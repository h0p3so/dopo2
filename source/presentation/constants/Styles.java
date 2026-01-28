/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Defines UI-related numeric and textual constants used throughout the
 * presentation layer. Centralizing these values helps ensure layout
 * consistency and prevents "magic numbers" from being scattered across
 * the codebase.
 *
 * This includes:
 *  - Border thickness presets  
 *  - Vertical spacing values  
 *  - Padding presets  
 *  - Common UI strings (e.g., button labels)
 *  - etc
 *
 * These constants are intended to standardize the visual feel of the
 * application and simplify maintenance whenever the UI design evolves.
 *
 * @author juad - 2025
 */
package presentation.constants;

public class Styles {
	public static final int BORDER_THICKNESS_NONE   = 0;
	public static final int BORDER_THICKNESS_TINY   = 1;
	public static final int BORDER_THICKNESS_MEDIUM = 3;
	public static final int BORDER_THICKNESS_BIG    = 6;

	public static final int VERTICAL_GAP_TINY   = 4;
	public static final int VERTICAL_GAP_MEDIUM = 11;
	public static final int VERTICAL_GAP_BIG    = 15;

	public static final int PADDING_TINY = 4;
	public static final int PADDING_BIG  = 11;

	public static final String TEXT_GO_BACK_BUTTON = "BACK";
}
