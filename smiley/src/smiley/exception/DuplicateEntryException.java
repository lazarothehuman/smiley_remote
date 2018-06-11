package smiley.exception;

public class DuplicateEntryException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2188224994440925417L;

	public DuplicateEntryException(String message) {
		super(message);
	}
}