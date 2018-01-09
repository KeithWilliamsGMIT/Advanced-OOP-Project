package ie.gmit.sw.databases;

import java.util.List;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import ie.gmit.sw.documents.Documentable;

/**
 * This class is responsible for saving and retrieving all {@link ie.gmit.sw.documents.Documentable}
 * objects to and from the database.
 * 
 * {@author Keith Williams}
 */
public class DocumentRepository implements Repository<Documentable> {
	
	/**
	 * Save the given document to the database.
	 * @param document to save.
	 */
	public void save(Documentable document) {	
		// Store the new document and commit changes.
		DatabaseManager.getInstance().getDb().store(document);
		DatabaseManager.getInstance().getDb().commit();
	}
	
	/**
	 * Retrieve all documents from the database.
	 * @return a list of documents.
	 */
	public List<Documentable> retrieveAll() {
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
		ObjectSet<Documentable> result = DatabaseManager.getInstance().getDb().query(new Predicate<Documentable>() {
			private static final long serialVersionUID = 777L;
			
			public boolean match(Documentable d) {
				return true;
			}
		});
		
		return result;
	}
}