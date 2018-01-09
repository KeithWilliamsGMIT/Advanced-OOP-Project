package ie.gmit.sw.comparators;

import java.util.List;
import java.util.Map;

import ie.gmit.sw.documents.Documentable;

/**
 * This interface should be implemented by any object that plays the role
 * of a comparator for documents. Any class that inherits this interface is
 * responsible for calculating the similarity of two or more documents.
 *
 * {@author Keith Williams}
 */
public interface DocumentComparator {

	/**
	 * Compare one document to a collection of documents.
	 * @param original document to compare.
	 * @param documents to compare the original document against.
	 * @return a map where the key is the document id and the value is the similarity measurement.
	 */
	public Map<Integer, Float> compare(Documentable original, List<Documentable> documents);

	/**
	 * Compare and calculate the similarity of two documents.
	 * @param original document to compare.
	 * @param document to compare the original document against.
	 * @return the similarity measurement.
	 */
	public float compare(Documentable original, Documentable document);

}