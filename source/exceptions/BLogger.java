/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Centralized error logger used to record programmer-related faults
 * during execution.  
 *
 * Errors are written to a persistent log file, allowing developers
 * to diagnose unexpected conditions without exposing them to players.
 *
 * SEVERE errors immediately terminate the application to prevent
 * undefined states. All other levels are simply recorded and the
 * execution of the program continues.
 *
 * @author juad
 */
package exceptions;

import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BLogger {
	private static final Logger INSTANCE = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static final int SEVERE  = Level.SEVERE.intValue();
	public static final int WARNING = Level.WARNING.intValue();
	public static final int INFO    = Level.INFO.intValue();
	public static final int CONFIG  = Level.CONFIG.intValue();

	/**
	 * Initializes the logger by attaching a file handler and formatter.
	 * Must be called once at program startup.
	 *
	 * If initialization fails, the program will exit immediately since
	 * logging is considered essential for debugging.
	 */
	public static void initLogger() {
		try {
			final FileHandler file = new FileHandler("bad-ice-cream-programmer-faults.log", true);
			file.setFormatter(new SimpleFormatter());
			INSTANCE.addHandler(file);
			INSTANCE.setUseParentHandlers(false);
		} catch (final Exception e) {
			System.err.println("it was not possible to set up the logger; aborting now!");
			System.exit(-1);
		}
	}

	/**
	 * Converts an integer severity constant into its corresponding
	 * {@link Level} instance. This avoids exposing java.util.logging
	 * to the rest of the codebase.
	 *
	 * @param level integer severity constant
	 * @return matching {@link Level}
	 */
	private static Level getLevel(final int level) {
		if (level == SEVERE)  { return Level.SEVERE; }
		if (level == WARNING) { return Level.WARNING; }
		if (level == INFO)    { return Level.INFO; }
		return Level.CONFIG;
	}

	/**
	 * Records an exception using the selected severity level.
	 * SEVERE errors also terminate the application after logging.
	 *
	 * @param level severity of the error
	 * @param e exception to be recorded
	 */
	public static void logError(final int level, final Exception e) {
		INSTANCE.log(getLevel(level), e.getMessage(), e);

		if (level == SEVERE) {
			System.err.println("SEVERE ERROR JUST HAPPENED: ABORTING NOW! PLEASE READ THE LOG FILE");
			System.exit(-1);
		}
	}
}
