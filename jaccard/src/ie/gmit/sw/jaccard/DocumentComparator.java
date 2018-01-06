package ie.gmit.sw.jaccard;

import java.util.List;
import java.util.Map;

/**
 * This interface should be implemented by any object that plays the role
 * of a comparator for documents. Any class that inherits this interface is
 * responsible for calculating the similarity of two or more documents.
 */
public interface DocumentComparator {

	/**
	 * Compare one document to a collection of documents.
	 * @param original document to compare.
	 * @param documents to compare the original document against.
	 * @return a map where the key is the document id and the value is the similarity measurement.
	 */
	public Map<Integer, Float> compare(Document original, List<Document> documents);

	/**
	 * Compare and calculate the similarity of two documents.
	 * @param original document to compare.
	 * @param document to compare the original document against.
	 * @return the similarity measurement.
	 */
	public float compare(Document original, Document document);

}