/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *        
 * This class defines the different programmer mistakes that can occur
 * during the execution of the program. These exceptions are meant to be
 * logged using the {@link BLogger} system so the issues can be tracked
 * and corrected during development.
 *
 * @author juad - 2025
 */
package exceptions;

public class ProgrammerException extends SharedException {
    /**
     * Creates a new ProgrammerException with a descriptive message.
     *
     * @param message explanation of the programmer mistake
     */
    public ProgrammerException (final String message) {
        super(message);
    }

    /**
     * Generates an error message when a view identifier does not match
     * any known or registered view.
     *
     * @param id the unrecognized view identifier
     * @return formatted error message
     */
    public static String unknownViewId (final String id) {
        return String.format("view id '%s' is not recognized", id);
    }

    /**
     * Generates an error message when the requested level number does
     * not correspond to an implemented level.
     *
     * @param levelNumber the attempted level number
     * @return formatted error message
     */
    public static String unimplementedLevel (final int levelNumber) {
        return String.format("attempting to play level %d, but it is not implemented", levelNumber);
    }
    
    public static String unreachable (final String from) {
    	return String.format("An unreachable point has been reached???? (%s)", from);
    }
    
    public static String malformedMap (final String line) {
    	return String.format("Malformed map. Check line: '%s'", line);
    }

    public static String unspecifiedP1Pos (final String path) {
    	return String.format("please specify a spawn position for player 1: %s", path);
    }
}
