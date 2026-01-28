/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Utility class that centralizes the creation and styling of
 * visual components used throughout the presentation layer.
 * This class provides factory-style helper methods for creating
 * consistently styled panels, buttons, and interaction effects.
 *
 * All methods are static because the class is intended purely
 * as a reusable component container.
 *
 * @author juad - 2025
 */
package presentation.recycle;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import presentation.constants.Colors;

public class Generics {
	/**
	 * Creates a panel using the standard golden-style configuration of the game.
	 * The panel's background and border colors are determined by
	 * {@link Colors}, and the border thickness
	 * is issued by the caller.
	 *
	 * @param borderThickness the desired border width, typically values from {@link presentation.constants.Styles}
	 * @return a new {@link JPanel} configured with the project's standard appearance
	 */
	public static JPanel createGoldenPanel (final int borderThickness) {
		final JPanel panel = new JPanel();
		panel.setBackground(Colors.PANEL_BACKGROUND);
		panel.setBorder(BorderFactory.createLineBorder(Colors.PANEL_BORDERS, borderThickness));
		return panel;
	}

	/**
	 * Creates a button styled according to the project's visual conventions.
	 * The button receives a background color, border, font, and a hand cursor.
	 *
	 * @param message the text displayed on the button
	 * @param font the font applied to the button
	 * @param borderThickness the thickness of the border, commonly defined in {@link presentation.constants.Styles}
	 * @return a styled {@link JButton} instance
	 */
	public static JButton createGoldenButton (final String message, final Font font, final int borderThickness) {
		final JButton button = new JButton(message);
		button.setName(message);
		button.setFont(font);
		button.setBackground(Colors.PANEL_BACKGROUND);
		button.setBorder(BorderFactory.createLineBorder(Colors.PANEL_BORDERS, borderThickness));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setFocusPainted(false);
		return button;
	}

	/**
	 * Adds a hover effect to a component that increases its font size
	 * when the mouse enters and restores it when the mouse exits.
	 * This method works on any {@link Component}, not just buttons.
	 *
	 * @param comp the component that will receive the hover effect
	 * @param normal the default font of the component
	 * @param onHover the font displayed while the component is hovered
	 */
	public static void styleIncreaseFontSizeOnHover (final Component comp, final Font normal, final Font onHover) {
		comp.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered (final MouseEvent e) { comp.setFont(onHover); }
			@Override public void mouseExited  (final MouseEvent e) { comp.setFont(normal); }
		});
	}
	
	/**
	 * Adds a hover effect to a button that temporarily changes its text.
	 * When hovered, the button displays {@code onHover}; when the cursor
	 * leaves, it restores {@code normal}.
	 *
	 * @param button the button receiving the hover text effect
	 * @param normal the default text of the button
	 * @param onHover the text displayed when the button is hovered
	 */
	public static void styleChangeTextOnHover (final JButton button, final String normal, final String onHover) {
		button.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered (final MouseEvent e) { button.setText(onHover); }
			@Override public void mouseExited  (final MouseEvent e) { button.setText(normal); }
		});
	}
}