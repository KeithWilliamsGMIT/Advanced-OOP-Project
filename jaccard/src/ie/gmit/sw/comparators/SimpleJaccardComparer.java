package ie.gmit.sw.comparators;

import java.util.Set;
import java.util.TreeSet;

import ie.gmit.sw.documents.Documentable;

/**
 * This class is responsible for calculating the Jaccard index, meaning the
 * similarity, between two or more documents. This measurement will be a
 * number between 1 and 0, where a value of 1 indicates that the documents
 * are identical. This comparer is best used for smaller documents as it
 * requires every shingle in one document to be compared against every shingle
 * in another document. This results in an accurate measure of similarity
 * but at a high computational complexity. For larger documents see
 * {@link ie.gmit.sw.comparators.MinHashJaccardComparer}.
 * 
 * {@author Keith Williams}
 */
public class SimpleJaccardComparer extends DocumentComparer {
	
	/**
	 * {@inheritDoc}
	 */
	public float compare(Documentable original, Documentable document) {
		// A set to store the intersection O(n).
		Set<Integer> n = new TreeSet<Integer>(document.getShingles());
		n.retainAll(original.getShingles()); // O(n log n)
		
		// Calculate the Jaccard index.
		float unionSize = original.getShingles().size() + document.getShingles().size() - n.size();
		
		return 1.0f * n.size() / unionSize;
	}
}
