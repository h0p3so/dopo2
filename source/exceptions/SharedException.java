/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * This class implements the exceptions that are shared by both user-level
 * exceptions and programmer-level exceptions. A typical example of its use
 * appears in {@link MapBluePrint}, where a method must verify that
 * the map being loaded is correctly defined before proceeding.
 *
 * @author juad - 2025
 */
package exceptions;

public class SharedException extends Exception {
    /**
     * Constructs a new SharedException with a descriptive message.
     *
     * @param message explanation of the shared exception
     */
    public SharedException (final String message) {
        super(message);
    }

    /**
     * Generates an error message when the dimensions of a map being loaded
     * do not meet the expected structural requirements.
     *
     * @param dimension description of the invalid dimension
     * @return formatted error message
     */
    public static String invalidMapDimensions (final String dimension) {
        return String.format("the map being loaded has an invalid dimension (%s)", dimension);
    }
    
    public static String invalidTileId (final char id) {
        return String.format("the tile defined by '%c' id is not known by the interpreter", id);
    }
}