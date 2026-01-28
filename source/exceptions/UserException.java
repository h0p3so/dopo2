/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * This class defines the exceptions that may be thrown when the user
 * attempts to perform an invalid, unavailable, or unimplemented action
 * during gameplay. These errors are not programmer faults, but rather
 * violations of what the user is allowed to do at a given point.
 *
 * For programmer-related issues, refer to {@link ProgrammerException}.
 *
 * @author juad - 2025
 */
package exceptions;

public class UserException extends SharedException {	
	/** Message used when the user triggers an unimplemented action. */
	public static final String UNIMPLEMENTED_ACTION = "This action is not available yet! :(";
	public static final String UNEXISTING_FILE = "File provided to store current game state does not exist";
	
	/**
	 * Creates a UserException with the given explanation.
	 *
	 * @param message description of the invalid user action
	 */
	public UserException (final String message) {
		super(message);
	}
}
