package ie.gmit.sw.requests;

/**
 * This interface should be implemented by any object that plays the role
 * of a request. In this application, each request should contain a task
 * number, which will be used to identify if the request is processed when
 * polling the result.
 */
public interface Requestable {
	/**
	 * Get the task number associated with this request.
	 * @return the task number.
	 */
	public String getTaskNumber();
}