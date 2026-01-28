/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * A reusable GUI component consisting of two vertically stacked sections:
 *
 *  - Information panel – implemented by subclasses to display custom
 *    content (labels, forms, icons, etc.)
 *
 *  - Back panel – a standardized bottom section containing a
 *    pre-styled “go back” button.
 *
 * This class centralizes the common layout and styling shared across
 * multiple views in the presentation layer, ensuring uniform appearance
 * and reducing duplicated code.
 *
 * @author juad - 2025
 */
package presentation.recycle;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import presentation.constants.Fonts;
import presentation.constants.Styles;

public abstract class BiPanel extends GifPanel
{	
	private JPanel mainPanel;
	private JPanel backPanel;

	protected JPanel infoPanel;
	protected JButton backButton;
	
	public BiPanel (String gifPath) {
		super(gifPath);
		this.setLayout(new GridBagLayout());
		this.setUpMainPanel();
		this.add(this.mainPanel);
	}
	
	private void setUpMainPanel () {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel , BoxLayout.Y_AXIS));
		this.mainPanel.setOpaque(false);

		this.setUpInformationPanel();
		this.setUpBackPanel();
		
		this.mainPanel.add(this.infoPanel);
		this.mainPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.mainPanel.add(this.backPanel);
	}
	
	private void setUpBackPanel () {
		this.backPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
		this.backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.backButton = Generics.createGoldenButton(Styles.TEXT_GO_BACK_BUTTON, Fonts.SMALL, Styles.BORDER_THICKNESS_NONE);
		this.setUpGoBackButtonAction();
		this.backPanel.add(this.backButton);
	}
	
	protected abstract void setUpInformationPanel ();
	protected abstract void setUpGoBackButtonAction ();
}
