/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * This helper is mainly used by panels that construct button menus or
 * multi-option selectors. Each instance represents a single button with:
 *
 *  - A user-facing name (text displayed on the button)
 *  - A destination view identifier (used for navigation)
 *  - A positional index (used for sorting or layout decisions)
 *
 * An example of its use can be found in {@link HomeView}, where multiple
 * navigation buttons are defined and arranged vertically.
 *
 * This class encapsulates the minimal information required to build such
 * interfaces in a clean and organized way.
 *
 * @author juad - 2025
 */
package presentation;

public class ButtonInfo {

	private String name;
	private String viewId;
	private int position;

	/**
	 * Creates a new button descriptor containing the visible name,
	 * the associated view identifier, and the button's relative
	 * position in the layout.
	 *
	 * @param name label to be shown on the button
	 * @param viewid identifier of the view the button should navigate to
	 * @param position position index for ordering within the panel
	 */
	public ButtonInfo(final String name, final String viewid, final int position) {
		this.name = name;
		this.viewId = viewid;
		this.position = position;
	}

	public String getName () {
		return this.name;
	}

	public String getViewId () {
		return this.viewId;
	}

	public int getPosition () {
		return this.position;
	}
}