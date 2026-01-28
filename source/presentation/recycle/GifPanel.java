/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Base class for panels that require a GIF image as their background.
 * Any presentation class that needs a moving or static image behind
 * its components can extend this class instead of creating its own
 * image-loading or drawing logic.
 *
 * Subclasses typically add child components and layout logic on top
 * of the background rendered here.
 *
 * @author juad - 2025
 */
package presentation.recycle;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import presentation.BaDopoCreamGUI;

public class GifPanel extends JPanel {
	private Image background;
	
	/**
	 * Loads a GIF (or any image supported by {@link ImageIcon})
	 * and stores it as the panel's background.
	 *
	 * @param path the path to the image resource, typically from {@link presentation.constants.Paths}
	 */
	public GifPanel (final String path) {
		this.background = new ImageIcon(path).getImage();
	}
	
	@Override
	public void paintComponent (final Graphics g) {
		super.paintComponent(g);
		g.drawImage(
			this.background,
			0,
			0,
			BaDopoCreamGUI.WINDOW_WIDTH,
			BaDopoCreamGUI.WINDOW_HEIGHT,
			this
		);
	}
}
