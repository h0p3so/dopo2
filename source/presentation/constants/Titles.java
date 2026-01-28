/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 */

/**
 * Centralizes all window and view titles used across the presentation
 * layer. Keeping these titles in one place ensures consistency and
 * simplifies future adjustments to UI terminology.
 *
 * These titles are displayed at the top of various game screens,
 * including:
 *  - The home menu  
 *  - Game mode selection  
 *  - Character selection  
 *  - Level selection  
 *  - etc
 *
 * Storing them as constants avoids repeated string literals and reduces
 * the chance of typos or mismatches between views.
 *
 * @author juad - 2025
 */
package presentation.constants;

public class Titles {
	public static final String HOME = "Bad Dopo Cream - 2025-2";
	public static final String PICK_MODE = "Select the mode you want to play/simulate :)";
	public static final String SELECT_CHARACTER = "Pick the character(s) you want to play";
	public static final String SELECT_LEVEL_GAMING = "Choose a level to smash!";
	
	public static String playingLevel (final int level) {
		return String.format("Bad Dopo Cream -  Playing level %d", level);
	}
}
