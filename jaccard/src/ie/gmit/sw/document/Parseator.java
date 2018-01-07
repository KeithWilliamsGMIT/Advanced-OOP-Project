package ie.gmit.sw.document;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface should be implemented by any object that plays the role
 * of a parser. Any class that inherits this interface is responsible for
 * parsing data from a document.
 * @param <T> the type of entity that is parsed.
 */
public interface Parseator<T> {
	/**
	 * Parse a document.
	 * @param stream of the document.
	 * @return the data that was parsed.
	 * @throws IOException
	 */
	public T parse(InputStream stream) throws IOException;

}