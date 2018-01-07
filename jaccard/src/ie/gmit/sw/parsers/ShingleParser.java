package ie.gmit.sw.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A class responsible for parsing shingles from a document.
 */
public class ShingleParser implements Parseator<Set<Integer>> {
	/*
	 * The number of words per shingle.
	 */
	private int shingleSize;
	
	/**
	 * Fully parameterised constructor to create an instance of the
	 * {@link ie.gmit.sw.parsers.ShingleParser} class.
	 * @param shingleSize is the number of words per shingle.
	 */
	public ShingleParser(int shingleSize) {
		this.shingleSize = shingleSize;
	}
	
	/**
	 * Parse a set of shingles from a document.
	 * @param stream of the document.
	 * @return a set of shingles.
	 * @throws IOException
	 */
	public Set<Integer> parse(InputStream stream) throws IOException {
		// The set of shingles.
		Set<Integer> shingles = new LinkedHashSet<Integer>();
		
		Deque<String> buffer = parseWords(stream);
		
		StringBuilder sb = new StringBuilder();
		int counter  = 0;
		
		/*
		 * Loop through the words and group them into shingles and then
		 * add the shingles to a set.
		 */
		while (buffer.peek() != null) {
			if (counter == shingleSize - 1) {
				shingles.add(sb.toString().toUpperCase().hashCode());
				counter = 0;
				sb = new StringBuilder();
			} else {
				sb.append(buffer.poll());
				counter++;
			}
		}
		
		return shingles;
	}
	
	/*
	 * Convert an input stream to a collection of words.
	 */
	private Deque<String> parseWords(InputStream stream) throws IOException {
		// A collection of individual words parsed from the document.
		Deque<String> buffer = new LinkedList<String>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		
		while ((line = br.readLine()) != null) {
			/*
			 * Break each line into an array of words. We could also use a
			 * regex to remove all characters except A-z, however, this
			 * might eliminate the semantics of the document.
			 */
			String[] words = line.split(" ");
			
			// Add each word in the document to the collection.
			for(int i = 0; i < words.length; i++) {
				buffer.add(words[i]);
			}
		}
		
		return buffer;
	}
}
