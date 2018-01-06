package ie.gmit.sw.requests;

import java.io.InputStream;

/**
 * This is the implementation of the requestable interface for requests
 * that will compute the Jaccard index of a document.
 */
public class JaccardRequest implements Requestable {
	private String taskNumber;
	private InputStream document;
	private int shingleSize;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.requests.JaccardRequest} class.
	 * @param taskNumber associated with this request.
	 * @param document associated with this request.
	 * @param shingleSize is the number of words per shingle.
	 */
	public JaccardRequest(String taskNumber, InputStream document, int shingleSize) {
		this.taskNumber = taskNumber;
		this.document = document;
		this.shingleSize = shingleSize;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getTaskNumber() {
		return taskNumber;
	}
	
	/**
	 * Get the document associated with this request.
	 * @return the document to process.
	 */
	public InputStream getDocument() {
		return document;
	}
	
	/**
	 * Get the shingle size associated with this request.
	 * @return the number of words per shingle.
	 */
	public int getShingleSize() {
		return shingleSize;
	}
}