/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *        
 * This interface acts as the communication layer between the
 * main window {@link BaDopoCreamGUI} and its child views
 * (any class whose name ends with “View”).
 * 
 * 	It standardizes how views interact with the application,
 * 	including navigation, glass-pane handling, messaging,
 *	and controller access.
 *
 * @author juad - 2025
 */
package presentation;

import javax.swing.JPanel;

import domain.Control;
import domain.map.chars.CharType;

public interface Intermediary {
	/**
	 * Views like {@link HomeView} needs to add a glass window in order
	 * to have a modal window, so we need to comunicate such action to
	 * the main frame
	 * 
	 * @param glassPane glass pane to be added
	 */
	void addAGlassPane (final JPanel glassPane);
	
	/**
	 * Tells the main compoenent to set the indicated view (this method is
	 * called within the action listeners of the buttons of the views)
	 * 
	 * @param ViewId id of the view to be shown (@see ViewsId)
	 */
	void showThisView (final String viewId);
	
	/**
	 * Tells the program an user exception occured so the program has to
	 * let the user know what went wrong
	 * 
	 * @param message descriptive message of what happened (@see exceptions.UserException)
	 */
	void indicateUserException (final String message);
	
	/**
	 * Since each view has its own title, the program will update the window title
	 * for the view's action (@see presentation.constants.Titles)
	 * 
	 * @param title title
	 */
	void setViewTitle (final String title);
	
	/**
	 * Gets the controller in order to make requests from the view part to the logic part
	 * 
	 * @return controller
	 */
	Control getController ();
}
