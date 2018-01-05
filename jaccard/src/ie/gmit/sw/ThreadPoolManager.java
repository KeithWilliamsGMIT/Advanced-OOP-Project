package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for setting up a thread pool that will handle
 * the processing of requests that are stored in a queue. Once processed
 * these requests will be stored in a map so that they can be returned to
 * the client.
 * 
 * Note that this is class uses the singleton design pattern as there
 * should only ever be one {@link ie.gmit.sw.ThreadPoolManager} instance.
 */
public class ThreadPoolManager {
	/*
	 * Own instance of the {@link ie.gmit.sw.ThreadPoolManager} class.
	 */
	private static ThreadPoolManager instance;

	/*
	 * The pool of worker threads.
	 */
	private ExecutorService pool;

	/*
	 * Private constructor to prevent more than one instance of this object
	 * from being created.
	 */
	private ThreadPoolManager() { }
	
	/**
	 * Get the single instance of the {@link ie.gmit.sw.ThreadPoolManager} class.
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
			Runnable worker = new Worker(queue);
			pool.execute(worker);
		}
	}
}
