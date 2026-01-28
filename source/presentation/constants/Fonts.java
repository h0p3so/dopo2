/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Fonts are loaded from the path specified in {@link Paths#GUI_FONT}.
 * If loading the TrueType font fails, the system safely falls back to
 * Arial Bold (20 pt), and the incident is logged using {@link exceptions.BLogger}.
 *
 * @author juad - 2025
 */
package presentation.constants;

import java.awt.Font;
import java.io.File;

import exceptions.BLogger;

public class Fonts {
	public static final Font TINY;
	public static final Font SMALL;
	public static final Font MEDIUM;
	public static final Font BIG;
	public static final Font HDU;
	
	static {
		/* TODO: create another base for the HDU font */
		Font base = new Font("Arial", Font.BOLD, 20);
		
		try {
			base = Font.createFont(Font.TRUETYPE_FONT, new File(Paths.GUI_FONT));
		} catch (final Exception e) {
			BLogger.logError(BLogger.WARNING, e);
		}
		
		TINY = base.deriveFont(10f);
		SMALL = base.deriveFont(15f);
		MEDIUM = SMALL.deriveFont(Font.BOLD).deriveFont(19.5f);
		BIG = base.deriveFont(25f);

		/* TODO: FIx this whenever we get there */
		HDU = BIG.deriveFont(30f);
	}
}
