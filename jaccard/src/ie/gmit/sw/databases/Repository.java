package ie.gmit.sw.databases;

import java.util.List;

/**
 * This interface should be implemented by any object that plays the role
 * of a repository. A repository is responsible for interacting with the
 * database. Specifically, it is responsible for persisting entities to
 * a database and querying those entities.
 * @param <T> refers to the entity type handled by the repository.
 */
public interface Repository<T> {
	/**
	 * Save the given entity to a database.
	 * @param entity to save.
	 */
	public void save(T entity);
	
	/**
	 * Retrieve all entities of the given type from the database.
	 * @return all entities.
	 */
	public List<T> retrieveAll();
}
