package ie.gmit.sw.comparators;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import ie.gmit.sw.documents.Documentable;

/**
 * This class is responsible for calculating the Jaccard index, meaning the
 * similarity, between two or more documents. This measurement will be a
 * number between 1 and 0, where a value of 1 indicates that the documents
 * are identical. This comparer is best used with larger documents as it
 * uses the MinHash algorithm to substantially reduce the computational
 * complexity by reducing the total number of comparisons required. However,
 * the similarity measurement will be an approximation and may not be 100%
 * accurate. For smaller documents see {@link ie.gmit.sw.comparators.SimpleJaccardComparer}.
 */
public class MinHashJaccardComparer extends DocumentComparer {
	/*
	 * Set of random hash functions.
	 */
	private Set<Integer> minhashes;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.comparators.MinHashJaccardComparer} class.
	 * @param numberOfHashes refers to the number of min hashes in the set.
	 */
	public MinHashJaccardComparer(int numberOfHashes) {
		minhashes = new TreeSet<Integer>();
		Random rnd = new Random();

		for (int i = 0; i < numberOfHashes; i++) {
			minhashes.add(rnd.nextInt());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public float compare(Documentable original, Documentable document) {
		// Calculate min hash values before comparing documents.
		Set<Integer> a = calculateMinHash(original);
		Set<Integer> b = calculateMinHash(document);
		
		// A set to store the intersection O(n).
		Set<Integer> n = new TreeSet<Integer>(b);
		n.retainAll(a); // O(n log n)
		
		// Calculate the Jaccard index.
		return n.size() / minhashes.size();
	}

	/*
	 * Calculate a set of min hash values for the shingles in the given
	 * document.
	 */
	private Set<Integer> calculateMinHash(Documentable document) {
		Set<Integer> shingles = new TreeSet<Integer>();
		
		for (Integer hash : minhashes) {
			int min = Integer.MAX_VALUE;
			
			for (int shingle : document.getShingles()) {
				// Bitwise XOR each shingle with the hash.
				int minhash = shingle ^ hash;
				
				if (minhash < min) {
					min = minhash;
				}
			}
			
			// Only store the shingle with the minimum hash for each hash function.
			shingles.add(min);
		}
		
		return shingles;
	}
}
