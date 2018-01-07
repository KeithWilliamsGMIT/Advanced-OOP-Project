package ie.gmit.sw.comparators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ie.gmit.sw.documents.Documentable;

/**
 * This abstract class should be extended by any object that is responsible
 * for comparing and calculating the similarity of two or more documents.
 */
public abstract class DocumentComparer implements DocumentComparator {

	/**
	 * {@inheritDoc}
	 */
	public Map<Integer, Float> compare(Documentable original, List<Documentable> documents) {
		Map<Integer, Float> map = new HashMap<Integer, Float>();
		
		/*
		 * Compare each document and calculate the Jaccard index and add
		 * the results to the map that will be returned.
		 */
		for (Documentable document : documents) {
			/*
			 * Delegate the work to the overloaded compare() method even
			 * though it is not implemented yet. This is known as the
			 * template pattern.
			 */
			float result = compare(original, document);
			map.put(document.getId(), result);
		}
		
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	public abstract float compare(Documentable original, Documentable document);

}