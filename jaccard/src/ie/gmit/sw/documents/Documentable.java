package ie.gmit.sw.documents;

import java.util.Set;

/**
 * This interface should be implemented by any object that plays the role
 * of a processed document. In the case of this application, a document is
 * considered processed when it is assigned an id and a set of shingles
 * have been extracted.
 */
public interface Documentable {

	/**
	 * Get the id associated with this document.
	 * @return the document id.
	 */
	public int getId();

	/**
	 * Get the set of shingles associated with this request.
	 * @return the set of shingles.
	 */
	public Set<Integer> getShingles();

}