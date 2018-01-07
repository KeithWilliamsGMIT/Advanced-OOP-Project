package ie.gmit.sw.requests;

import java.io.InputStream;
import java.util.Set;

import ie.gmit.sw.comparators.DocumentComparator;
import ie.gmit.sw.parsers.Parseator;

/**
 * This interface should be implemented by any object that plays the role
 * of a request. In this application, each request should contain a task
 * number, which will be used to identify if the request is processed when
 * polling the result, a document which should be processed, and a
 * {@link ie.gmit.sw.Parseator} and {@link ie.gmit.sw.comparators.DocumentComparator}
 * that will be used to process the document. 
 */
public interface Requestable {
	/**
	 * Get the task number associated with this request.
	 * @return the task number.
	 */
	public String getTaskNumber();
	
	/**
	 * Get the document associated with this request.
	 * @return the document to process.
	 */
	public InputStream getDocument();
	
	/**
	 * Get the parser associated with this request.
	 * @return the parser.
	 */
	public Parseator<Set<Integer>> getParser();
	
	/**
	 * Get the comparator associated with this request.
	 * @return the comparator.
	 */
	public DocumentComparator getComparator();
}