package ie.gmit.sw;

import java.io.*;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import ie.gmit.sw.threading.ThreadPoolManager;

public class ServicePollHandler extends HttpServlet {
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html"); 
		PrintWriter out = resp.getWriter(); 
		
		String title = req.getParameter("txtTitle");
		String taskNumber = req.getParameter("frmTaskNumber");
		int counter = 1;
		
		if (req.getParameter("counter") != null){
			counter = Integer.parseInt(req.getParameter("counter"));
			counter++;
		}

		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document Title: " + title + "</H3>");
		out.print("<b><font color=\"ff0000\">A total of " + counter + " polls have been made for this request.</font></b> ");
		
		Map<Integer, Float> result = ThreadPoolManager.getInstance().getMap().remove(taskNumber);
		
		if (result != null) {
			/*
			 * If the request is ready then return a table showing the
			 * id and measurement of similarity for each document that it
			 * was compared against. Also return a link to the starting
			 * page so that the user compare another document.
			 */
			out.print("<table>");
			out.print("<tr><th>Document ID</th><th>Similarity</th></tr>");
			
			for (Integer key : result.keySet()) {
				out.print("<tr>");
				out.print("<td>" + key + "</td>");
				out.print("<td>" + result.get(key) + "</td>");
				out.print("</tr>");
			}
			
			out.print("</table>");
			
			out.print("<a href=\"\\jaccard\">Compare more documents!</a>");
		} else {
			/*
			 * If the request is not ready then display a message to the
			 * user and return a script to refresh every 5 seconds.
			 */
			out.print("Your request has not yet been processed... Please Wait...");
			out.print("<form name=\"frmRequestDetails\">");
			out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
			out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
			out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
			out.print("</form>");
			
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);");
			out.print("</script>");
		}
		
		out.print("</body>");
		out.print("</html>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}