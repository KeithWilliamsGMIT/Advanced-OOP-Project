package ie.gmit.sw.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * This interface should be implemented by any object that plays the role
 * of a parser. Any class that inherits this interface is responsible for
 * parsing shingles from an input.
 * 
 * {@author Keith Williams}
 */
public interface ShingleParseator {
	/**
	 * Parse a set of hashed shingles from an input.
	 * @param stream of the document to parse.
	 * @return the set of shingles that were parsed.
	 * @throws IOException
	 */
	public Set<Integer> parse(InputStream stream) throws IOException;

}