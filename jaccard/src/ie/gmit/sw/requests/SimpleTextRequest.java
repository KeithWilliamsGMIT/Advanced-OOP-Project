package ie.gmit.sw.requests;

import java.io.InputStream;

import ie.gmit.sw.comparators.DocumentComparator;
import ie.gmit.sw.comparators.SimpleJaccardComparer;
import ie.gmit.sw.parsers.ShingleParseator;
import ie.gmit.sw.parsers.ShingleParser;

/**
 * This class is a concrete implementation of the {@link ie.gmit.sw.requests.Request}
 * abstract class. This type of request is best suitable for smaller text documents.
 *
 * {@author Keith Williams}
 */
public class SimpleTextRequest extends Request {
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.requests.SimpleTextRequest} class.
	 * @param taskNumber associated with this request.
	 * @param document associated with this request.
	 * @param shingleSize is the number of words per shingle.
	 */
	public SimpleTextRequest(String taskNumber, InputStream document, int shingleSize) {
		super(taskNumber, document);
		
		// Create and set the parser.
		ShingleParseator parser = new ShingleParser(shingleSize);
		super.setParser(parser);
		
		// Create and set the comparer.
		DocumentComparator comparer = new SimpleJaccardComparer();
		super.setComparer(comparer);
	}
}