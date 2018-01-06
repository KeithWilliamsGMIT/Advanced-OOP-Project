package ie.gmit.sw.databases;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;

import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

/**
 * This class is responsible for managing an instance of a DB4O database.
 * Note that this class is a singleton as only one instance of the database
 * should exist in the application. Note that the init() method must be
 * invoked in order to create the instance of the database.
 */
public class DatabaseManager {
	
	/*
	 * Own instance of the DatabaseManager class.
	 */
	private static DatabaseManager instance;
	
	/*
	 * Instance of the database.
	 */
	private ObjectContainer db;
	
	/*
	 * Private constructor to prevent more than one instance of this object
	 * from being created.
	 */
	private DatabaseManager() { }
	
	/**
	 * Get the single instance of the {@link ie.gmit.sw.databases.DatabaseManager} class.
	 * @return the instance of the class.
	 */
	public static DatabaseManager getInstance() {
		// Note that this is known as a 'lazy' singleton.
		if (instance == null) {
			instance = new DatabaseManager();
		}
		
		return instance;
	}
	
	/**
	 * Create and configure an instance of the database
	 * @param pasword to encrypt the storage file.
	 * @param filename is the name of the storage file.
	 */
	public void init(String password, String filename, int updateDepth) {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport());
		config.common().add(new TransparentPersistenceSupport());
		config.common().updateDepth(updateDepth);
		
		/*
		 * We can use the XTea lib for encryption. The basic Db4O container
		 * only uses a Caesar cypher, which is not very secure.
		 */
		config.file().storage(new XTeaEncryptionStorage(password, XTEA.ITERATIONS64));
		
		/*
		 * Open a local database. We could also use the following command for
		 * for a full client-server architecture:
		 * 
		 * 		Db4o.openServer(config, server, port)
		 */
		db = Db4oEmbedded.openFile(config, filename);
	}
	
	/**
	 * Get the instance of the database.
	 * @return get the database instance.
	 */
	public ObjectContainer getDb() {
		return db;
	}
}
