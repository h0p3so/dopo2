/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * This class defines all the absolute or relative paths
 * to the project's graphical and typographical assets.
 *
 * Centralizing asset paths in a single class prevents inconsistencies,
 * duplication, and the classic “string scattered everywhere” problem.
 *
 * Asset categories include:
 *   - GIF animations (home screen, general ambience)
 *   - Custom TrueType fonts used by the GUI
 *   - Sprites for map tiles such as delimiters and blocks
 *   - etc
 *
 * Whenever assets are reorganized or renamed, this is the class
 * to update—no need to hunt through unrelated code.
 *
 * @author juad - 2025
 */
package presentation.constants;

public class Paths {
	public static final String GIF_HOME = "assets/gifs/home.gif";
	public static final String GIF_GENERAL = "assets/gifs/snow.gif";
	
	public static final String GUI_FONT = "assets/gui-font.ttf";
	
	/* TODO: fix this whenever we get there */
	public static final String HDU_FONT = GUI_FONT;
}
