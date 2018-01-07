package ie.gmit.sw.databases;

import java.util.List;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import ie.gmit.sw.document.Document;

/**
 * This class is responsible for saving and retrieving all {@link ie.gmit.sw.document.Document}
 * objects to and from the database.
 */
public class DocumentRepository implements Repository<Document> {
	
	/**
	 * Save the given document to the database.
	 * @param document to save.
	 */
	public void save(Document document) {	
		// Store the new document and commit changes.
		DatabaseManager.getInstance().getDb().store(document);
		DatabaseManager.getInstance().getDb().commit();
	}
	
	/**
	 * Retrieve all documents from the database.
	 * @return a list of documents.
	 */
	public List<Document> retrieveAll() {
		/* 
		 * This method uses native queries to find a list of documents in the
		 * database. Native queries are the preferred mechanism for querying
		 * any database from an OOPL. Native queries work by constructing a
		 * predicate that evaluates to either true or false. If true, the matched
		 * object is added to the ObjectSet. Note that the  predicate is merely
		 * an anonymous inner class. Unlike other query languages like SQL, OQL,
		 * HQL, etc... the application of agile techniques such as refactoring
		 * work seamlessly with native queries.
		 */
		ObjectSet<Document> result = DatabaseManager.getInstance().getDb().query(new Predicate<Document>() {
			private static final long serialVersionUID = 777L;
			
			public boolean match(Document d) {
				// Match all documents in the database.
		        return true;
		    }
		});
		
		return result;
	}
}
