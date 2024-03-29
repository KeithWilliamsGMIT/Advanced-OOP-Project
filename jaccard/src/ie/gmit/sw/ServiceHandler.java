package ie.gmit.sw;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.*;
import javax.servlet.http.*;

import ie.gmit.sw.databases.DatabaseManager;
import ie.gmit.sw.requests.OptimisedTextRequest;
import ie.gmit.sw.requests.Requestable;
import ie.gmit.sw.threading.ThreadPoolManager;

import javax.servlet.annotation.*;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB. The file size in bytes after which the file will be temporarily stored on disk. The default size is 0 bytes.
                 maxFileSize=1024*1024*10,      // 10MB. The maximum size allowed for uploaded files, in bytes
                 maxRequestSize=1024*1024*50)   // 50MB. he maximum size allowed for a multipart/form-data request, in bytes.
public class ServiceHandler extends HttpServlet {
	/* 
	 * Declare any shared objects here.
	 */
	private int shingleSize;
	private int minHashNumber;
	private static long jobNumber = 0;
	
	/*
	 * The in queue that stores all the requests that need to be processed.
	 */
	private BlockingQueue<Requestable> queue = new LinkedBlockingQueue<Requestable>();
	
	/* This method is only called once, when the servlet is first started (like a constructor). 
	 * It's the Template Pattern in action! Any application-wide variables should be initialised 
	 * here. Note that if you set the xml element <load-on-startup>1</load-on-startup>, this
	 * method will be automatically fired by Tomcat when the web server itself is started.
	 */
	public void init() throws ServletException {
		ServletContext ctx = getServletContext(); //The servlet context is the application itself.
		
		// Reads the value from the <context-param> in web.xml. Any application scope variables 
		// defined in the web.xml can be read in as follows:
		shingleSize = Integer.parseInt(ctx.getInitParameter("SHINGLE_SIZE"));
		minHashNumber = Integer.parseInt(ctx.getInitParameter("MIN_HASH_NUMBER"));
		int threadPoolSize = Integer.parseInt(ctx.getInitParameter("THREAD_POOL_SIZE"));
		
		String storageEncryptionPassword = ctx.getInitParameter("DB_STORAGE_ENCRYPTION_PASSWORD");
		String storageFilename = ctx.getInitParameter("DB_STORAGE_FILENAME");
		int updateDepth = Integer.parseInt(ctx.getInitParameter("DB_UPDATE_DEPTH"));
		
		// Initialise the database.
		DatabaseManager.getInstance().init(storageEncryptionPassword, storageFilename, updateDepth);
		
		try {
			// Start the thread pool.
			ThreadPoolManager poolManager = ThreadPoolManager.getInstance();
			poolManager.init(threadPoolSize, queue);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/* 
	 * The doGet() method handles a HTTP GET request. Please note the following very carefully:
	 *   1) The doGet() method is executed in a separate thread. If you instantiate any objects
	 *      inside this method and don't pass them around (ie. encapsulate them), they will be
	 *      thread safe.
	 *   2) Any instance variables or class fields will be shared by threads and must be handled
	 *      carefully.
	 *   3) It is standard practice for doGet() to forward the method invocation to doPost() or
	 *      vice-versa.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Step 1) Write out the MIME type
		resp.setContentType("text/html");
		
		// Step 2) Get a handle on the PrintWriter to write out HTML
		PrintWriter out = resp.getWriter();
		
		// Step 3) Get any submitted form data. These variables are local to this method and thread safe...
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		Part part = req.getPart("txtDocument");
		
		// Step 4) Process the input and write out the response. 
		// The following string should be extracted as a context from web.xml 
		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
		out.print("</head>");
		out.print("<body>");
		
		// We could use the following to track asynchronous tasks. Comment it out otherwise...
		if (taskNumber == null){
			taskNumber = new String("T" + jobNumber);
			jobNumber++;
			
			// Create a new request and add the job to the in-queue.
			// Note that the add() method is not a blocking call.
			Requestable request = new OptimisedTextRequest(taskNumber, part.getInputStream(), shingleSize, minHashNumber);
			queue.add(request);
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/poll");
			dispatcher.forward(req,resp);
		}
		
		// Output some headings at the top of the generated page
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		
		// We can also dynamically write out a form using hidden form fields. The form itself is not
		// visible in the browser, but the JavaScript below can see it.
		out.print("<form name=\"frmRequestDetails\" action=\"poll\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	
		
		// JavaScript to periodically poll the server for updates (this is ideal for an asynchronous operation)
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);"); //Refresh every 10 seconds
		out.print("</script>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}