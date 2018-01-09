package ie.gmit.sw.requests;

import java.io.InputStream;

import ie.gmit.sw.comparators.DocumentComparator;
import ie.gmit.sw.parsers.ShingleParseator;

/**
 * This is an implementation of the {@link ie.gmit.sw.requests.Requestable}
 * interface. This abstract class implements all of the methods defined in
 * the interface. However, any class that extends this abstract class must
 * set the parser and comparer.
 * 
 * {@author Keith Williams}
 */
public abstract class Request implements Requestable {
	private String taskNumber;
	private InputStream document;
	private ShingleParseator parser;
	private DocumentComparator comparer;
	
	/**
	 * Constructor to create an instance of the {@link ie.gmit.sw.requests.Request}
	 * class.
	 * @param taskNumber associated with this request.
	 * @param document to process associated with this request.
	 */
	public Request(String taskNumber, InputStream document) {
		this.taskNumber = taskNumber;
		this.document = document;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getTaskNumber() {
		return taskNumber;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public InputStream getDocument() {
		return document;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ShingleParseator getParser() {
		return parser;
	}
	
	/**
	 * Set the parser for this request.
	 * @param parser to set.
	 */
	protected void setParser(ShingleParseator parser) {
		this.parser = parser;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public DocumentComparator getComparator() {
		return comparer;
	}
	
	/**
	 * Set the comparer for this request.
	 * @param comparer to set.
	 */
	protected void setComparer(DocumentComparator comparer) {
		this.comparer = comparer;
	}
}
