/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * This class defines all color constants used throughout the project.
 * 
 * To keep a consistent style across panels, views, and game elements,
 * every UI-related color value must be referenced from here. This avoids
 * scattering arbitrary RGB values across the codebase.
 *
 * @author juad - 2025
 */
package presentation.constants;

import java.awt.Color;

public class Colors {
	public static final Color PANEL_BACKGROUND = new Color(248, 242, 226);
	public static final Color PANEL_BORDERS = new Color(166, 119, 17);

	public static final Color GAME_PINK = new Color(254, 199, 197);
	public static final Color GAME_BEIGE = PANEL_BACKGROUND;
	public static final Color GAME_BROWN = new Color(200, 164, 98);
	public static final Color GAME_LIGHT_BLUE = new Color(246, 254, 254);

	public static final Color CHARACTER_CHOCOLATE = GAME_BROWN;
	public static final Color CHARACTER_VANILLA = GAME_BEIGE;
	public static final Color CHARACTER_STRAWBERRY = GAME_PINK;
	public static final Color SNOW = GAME_LIGHT_BLUE;
	public static final Color CHARACTER_TROLL = new Color(143, 224, 116);
	public static final Color CHARACTER_MACETA = new Color(246, 209, 70);
	public static final Color CHARACTER_NARWHAL = new Color(203, 242, 254);

	public static final Color GAME_SCORE_FOREGROUND = new Color(247, 221, 85);
}
