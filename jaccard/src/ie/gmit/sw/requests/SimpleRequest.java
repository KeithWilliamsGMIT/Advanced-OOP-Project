package ie.gmit.sw.requests;

import java.io.InputStream;
import java.util.Set;

import ie.gmit.sw.comparators.DocumentComparator;
import ie.gmit.sw.comparators.SimpleJaccardComparer;
import ie.gmit.sw.document.Parseator;
import ie.gmit.sw.document.ShingleParser;

/**
 * This class is a concrete implementation of the {@link ie.gmit.sw.requests.Request}
 * abstract class. This type of request is best suitable for smaller text documents.
 */
public class SimpleRequest extends Request {
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.requests.SimpleRequest} class.
	 * @param taskNumber associated with this request.
	 * @param document associated with this request.
	 * @param shingleSize is the number of words per shingle.
	 */
	public SimpleRequest(String taskNumber, InputStream document, int shingleSize) {
		super(taskNumber, document);
		
		// Create and set the parser.
		Parseator<Set<Integer>> parser = new ShingleParser(shingleSize);
		super.setParser(parser);
		
		// Create and set the comparer.
		DocumentComparator comparer = new SimpleJaccardComparer();
		super.setComparer(comparer);
	}
}