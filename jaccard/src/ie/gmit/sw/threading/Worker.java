package ie.gmit.sw.threading;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import ie.gmit.sw.databases.DocumentRepository;
import ie.gmit.sw.databases.Repository;
import ie.gmit.sw.document.Document;
import ie.gmit.sw.requests.Requestable;

/**
 * Worker threads are responsible for processing requests. Once processed
 * they are moved from the in queue to a map. There are three steps in
 * processing a request.
 * 1. Extract the shingles
 * 2. Store in a database
 * 3. Compute the similarity
 */
public class Worker implements Runnable {
	
	/*
	 * The in queue that stores all the requests that need to be processed.
	 */
	private BlockingQueue<Requestable> queue;
	
	/*
	 * The out queue (map) stores all the requests that have been processed.
	 */
	private Map<String, Map<Integer, Float>> map;
	
	/*
	 * Define a repository to save the processed documents to.
	 */
	private Repository<Document> repository = new DocumentRepository();
	
	/*
	 * If true, the thread will keep checking for new requests in the in queue.
	 */
	private boolean keepRunning = true;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.threading.Worker} class.
	 * @param queue of requests to be processed.
	 * @param map of requests that are processed.
	 */
	public Worker(BlockingQueue<Requestable> queue, Map<String, Map<Integer, Float>> map) {
		this.queue = queue;
		this.map = map;
	}
	
	/**
	 * Everything within this method will be executed in a separate thread.
	 */
	public void run() {
		while (keepRunning) {
			try {
				// Blocking call to take the next request from the in queue.
				Requestable request = queue.take();
				
				// Extract the set of shingles from the document.
				Set<Integer> shingles = request.getParser().parse(request.getDocument());
				
				// Get all documents from the database.
				List<Document> documents = repository.retrieveAll();
				
				/*
				 * Get the number of documents in the database and use the total
				 * number of documents as the id. This means the first document
				 * will have an id of 0, the second will have an id of 1 and so on.
				 * Note that this method of incrementing the document relies on
				 * documents not being deleted from the database. If this functionality
				 * is added, then a different method of creating a new id must be
				 * used.
				 */
				int id = documents.size();
				
				// Create the new document object that should be stored.
				Document document = new Document(id, shingles);
				
				// Write the document to the database.
				repository.save(document);
				
				// Calculate the Jaccard index.
				Map<Integer, Float>result = request.getComparator().compare(document, documents);
				
				// Add the result to an out queue when processed.
				map.put(request.getTaskNumber(), result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}