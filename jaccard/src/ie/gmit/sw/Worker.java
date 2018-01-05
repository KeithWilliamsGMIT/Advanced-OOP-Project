package ie.gmit.sw;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Worker threads are responsible for processing requests. Once processed
 * they are moved from the in queue to a map. There are four steps in
 * processing a request.
 * 1. Extract shingles
 * 2. Store in database
 * 3. Compute min hashes
 * 4. Compute jaccard index
 */
public class Worker implements Runnable {
	
	/*
	 * The in queue that stores all the requests that need to be processed.
	 */
	private BlockingQueue<Requestable> queue;
	
	/*
	 * If true, the thread will keep checking for new requests in the in queue.
	 */
	private boolean keepRunning = true;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.Worker} class.
	 * @param queue of requests to be processed.
	 */
	public Worker(BlockingQueue<Requestable> queue) {
		this.queue = queue;
	}
	
	/**
	 * Everything within this method will be executed in a separate thread.
	 */
	public void run() {
		while (keepRunning) {
			try {
				// Blocking call to take the next request from the in queue.
				Requestable request = queue.take();
				
				/*
				 * Process the request. Note that rather than having a sequence
				 * of if/else blocks here, it may be better to utilise the
				 * chain of responsibility design pattern.
				 */
				if (request instanceof JaccardRequest) {
					JaccardRequest jr = (JaccardRequest) request;
					
					// Extract the set of shingles from the document.
					ShingleParser sp = new ShingleParser();
					Set<Integer> shingles = sp.parse(jr.getShingleSize(), jr.getDocument());
					
					// Add the result to an out queue when processing is complete.
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}