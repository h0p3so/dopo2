/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * A reusable GUI component similar in purpose to a {@link BiPanel}, but designed
 * for screens where the informational content and the back button must be placed
 * inside a *single unified content block* rather than in two separate stacked
 * sections.
 *
 * The component provides:
 *
 *  - A title section – displayed at the top of the panel using predefined
 *    presentation styles.
 *
 *  - Information panel – implemented by subclasses to supply the custom
 *    content (labels, forms, controls, etc.).
 *
 *  - Back button – a standardized “go back” button placed at the bottom of
 *    the same content block, rather than in a dedicated footer panel.
 *
 * This class centralizes the common initialization steps, spacing rules,
 * and styling conventions used by several single-section views in the
 * presentation layer, ensuring visual consistency and reducing duplication.
 *
 * Subclasses are responsible for:
 *  - Building the internal information panel.
 *  - Defining the behavior of the “go back” button.
 *
 * @author juad - 2025
 */
package presentation.recycle;

import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentation.constants.Fonts;
import presentation.constants.Styles;

public abstract class MonoPanel extends GifPanel
{
	private JPanel mainPanel;
	
	protected JButton backButton;
	protected JPanel singlePanel;

	public MonoPanel (final String gifPanel, final String title) {
		super(gifPanel);
		this.setLayout(new GridBagLayout());
		this.setUp(title);
		this.add(this.mainPanel);
	}
	
	private void setUp (final String titlemsg) {
		this.mainPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		
		final JLabel title = new JLabel(titlemsg);
		title.setFont(Fonts.BIG);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.setUpInformationPanel();
		this.backButton = Generics.createGoldenButton(
			Styles.TEXT_GO_BACK_BUTTON,
			Fonts.SMALL,
			Styles.BORDER_THICKNESS_NONE
		);
		this.backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setUpGoBackButtonAction();
		
		this.mainPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.mainPanel.add(title);
		this.mainPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.mainPanel.add(this.singlePanel);
		this.mainPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.mainPanel.add(this.backButton);
		this.mainPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
	}

	protected abstract void setUpInformationPanel ();
	protected abstract void setUpGoBackButtonAction ();
}
