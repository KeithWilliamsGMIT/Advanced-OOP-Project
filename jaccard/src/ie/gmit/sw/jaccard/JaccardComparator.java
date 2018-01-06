package ie.gmit.sw.jaccard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class is responsible for calculating the Jaccard index, meaning the
 * similarity, between two or more documents. This measurement will be a
 * number between 1 and 0, where a value of 1 indicates that the documents
 * are identical.
 */
public class JaccardComparator implements DocumentComparator {
	
	/**
	 * {@inheritDoc}
	 */
	public Map<Integer, Float> compare(Document original, List<Document> documents) {
		Map<Integer, Float> map = new HashMap<Integer, Float>();
		
		/*
		 * Compare each document and calculate the Jaccard index and add
		 * the results to the map that will be returned.
		 */
		for (Document document : documents) {
			// Delegate the work to the overloaded compare() method.
			float result = compare(original, document);
			map.put(document.getId(), result);
		}
		
		return map;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public float compare(Document original, Document document) {
		// A set to store the intersection O(n).
		Set<Integer> n = new TreeSet<Integer>(document.getShingles());
		n.retainAll(original.getShingles()); // O(n log n)
		
		float unionSize = original.getShingles().size() + document.getShingles().size() - n.size();
		
		return n.size() / unionSize;
	}
}
