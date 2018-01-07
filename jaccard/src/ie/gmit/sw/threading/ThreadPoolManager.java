package ie.gmit.sw.threading;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ie.gmit.sw.requests.Requestable;

/**
 * This class is responsible for setting up a thread pool that will handle
 * the processing of requests that are stored in a queue. Once processed
 * these requests will be stored in a map so that they can be returned to
 * the client.
 * 
 * Note that this is class uses the singleton design pattern as there
 * should only ever be one {@link ie.gmit.sw.threading.ThreadPoolManager} instance.
 */
public class ThreadPoolManager {
	/*
	 * Own instance of the ThreadPoolManager class.
	 */
	private static ThreadPoolManager instance;

	/*
	 * The pool of worker threads.
	 */
	private ExecutorService pool;
	
	/*
	 * The out queue (map) stores all the requests that have been processed.
	 * The key is the task number while the value is another map. The key in
	 * this map is the id of the document that the original was compared
	 * against, while the value is the similarity measurement of the documents.
	 * This is stored as an instance variable so that it can be accessed from
	 * other classes, for example, the servlet that handles the polling.
	 */
	private Map<String, Map<Integer, Float>> map = new ConcurrentHashMap<String, Map<Integer, Float>>();

	/*
	 * Private constructor to prevent more than one instance of this object
	 * from being created.
	 */
	private ThreadPoolManager() { }
	
	/**
	 * Get the single instance of the {@link ie.gmit.sw.threading.ThreadPoolManager} class.
	 * @return the instance of the class.
	 */
	public static ThreadPoolManager getInstance() {
		// Note that this is known as a 'lazy' singleton.
		if (instance == null) {
			instance = new ThreadPoolManager();
		}
		
		return instance;
	}
	
	/**
	 * Setup the thread pool that will process requests.
	 * @param threadPoolSize the number of threads in the thread pool.
	 * @param queue of requests to be processed.
	 * @throws InterruptedException
	 */
	public void init(int threadPoolSize, BlockingQueue<Requestable> queue) throws InterruptedException {
		// Create a thread pool with X number of threads.
		pool = Executors.newFixedThreadPool(threadPoolSize);
		// Populate the thread pool with workers.
		for (int i = 0; i < threadPoolSize; i++) {
			Runnable worker = new Worker(queue, map);
			pool.execute(worker);
		}
	}
	
	/**
	 * Get a map of requests that were processed by the threads in the thread pool.
	 * @return map of processed requests.
	 */
	public Map<String, Map<Integer, Float>> getMap() {
		return map;
	}
}
