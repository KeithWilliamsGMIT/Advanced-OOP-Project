package ie.gmit.sw.documents;

import java.util.Set;

/**
 * This is an implementation of the {@link ie.gmit.sw.documents.Documentable}
 * interface.
 * 
 * {@author Keith Williams}
 */
public class Document implements Documentable {
	private int id;
	private Set<Integer> shingles;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.documents.Document} class.
	 * @param id is used to uniquely identify the document.
	 * @param shingles is a set of hashed values. 
	 */
	public Document(int id, Set<Integer> shingles) {
		this.id = id;
		this.shingles = shingles;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
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
