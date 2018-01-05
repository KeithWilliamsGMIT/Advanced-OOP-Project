package ie.gmit.sw;

import java.util.Set;

/**
 * A representation of a processed document that can be persisted
 * in a database.
 */
public class Document {
	private int id;
	private Set<Integer> shingles;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.Document} class.
	 * @param id is used to uniquely identify the document.
	 * @param shingles is a set of hashed values. 
	 */
	public Document(int id, Set<Integer> shingles) {
		this.id = id;
		this.shingles = shingles;
	}

	/**
	 * Get the id associated with this document.
	 * @return the document id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the set of shingles associated with this request.
	 * @return the set of shingles.
	 */
	public Set<Integer> getShingles() {
		return shingles;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "Document [id=" + id + ", shingles=" + shingles.size() + "]";
	}
}
