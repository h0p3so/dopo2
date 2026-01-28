/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |			
 *    `.  |			
 *      `.| hjw
 *        `
 *
 *
 * This panel presents the available game modes so the
 * user can select their preferred mode before playing.
 * 
 * The view displays buttons for each mode, some of which
 * may not yet be implemented. Selecting a mode navigates
 * to the corresponding view via the {@link Intermediary}.
 *
 * @author juad - 2025
 */
package presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import exceptions.UserException;
import presentation.constants.Fonts;
import presentation.constants.Paths;
import presentation.constants.Styles;
import presentation.constants.Titles;
import presentation.recycle.BiPanel;
import presentation.recycle.Generics;

public class SelectModeView extends BiPanel {
	private static SelectModeView INSTANCE = null;
	private static Intermediary INTER;

	private static final String WINDOW_TITLE = Titles.PICK_MODE;
	private static final String GIF = Paths.GIF_GENERAL;
	
	private SelectModeView () {
		super(GIF);
	}

	/**
	 * Sets up the information panel, including a title label and
	 * buttons for each selectable game mode.
	 */
	@Override protected void setUpInformationPanel () {
		this.infoPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		
		final JLabel title = new JLabel("PICK A MODE");
		title.setFont(Fonts.BIG);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		final JPanel modes = new JPanel(new FlowLayout(FlowLayout.CENTER, Styles.PADDING_TINY, Styles.PADDING_TINY));
		modes.setOpaque(false);
		this.setUpButtons(modes);
		
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.infoPanel.add(title);
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.infoPanel.add(modes);
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
	}

	/**
	 * Sets up the "go back" button to navigate to the {@link HomeView}.
	 */
	@Override protected void setUpGoBackButtonAction () {
		this.backButton.addActionListener(e -> {
			INTER.showThisView(ViewsId.HOME_VIEW);
		});
	}
	
	/**
	 * Initializes the mode selection buttons with styling, size, hover effects,
	 * and action listeners. If a mode is unimplemented, an exception is shown
	 * via the {@link Intermediary}.
	 *
	 * @param panel the container panel for the buttons
	 */
	private void setUpButtons (final JPanel panel) {
		final ButtonInfo [] info = {
			new ButtonInfo("Solo", ViewsId.ONE_ICE_CREAM, 0),
			new ButtonInfo("W/ Fried", ViewsId.TWO_ICE_CREAM, 1),
			new ButtonInfo("Vs Fried", ViewsId.OPP_VS_ICE, 2),
			new ButtonInfo("Simulation", ViewsId.SIMULATION, 3)
		};
		
		final Dimension buttonsize = new Dimension(150, 60);
		for (int i = 0; i < info.length; i++) {
			final ButtonInfo context = info[i];
			final JButton button = Generics.createGoldenButton(context.getName(), Fonts.SMALL, Styles.BORDER_THICKNESS_TINY);
			Generics.styleIncreaseFontSizeOnHover(button, Fonts.SMALL, Fonts.MEDIUM);

			button.setPreferredSize(buttonsize);
			button.setMinimumSize(buttonsize);
			button.setMaximumSize(buttonsize);
			
			button.addActionListener(e -> {
				try {
					if (context.getViewId() == null) {
						throw new UserException(UserException.UNIMPLEMENTED_ACTION);
					}
					INTER.showThisView(context.getViewId());
				} catch (final UserException u) {
					INTER.indicateUserException(u.getMessage());
				}
			});
			panel.add(button);
		}
	}
	
	/**
	 * Returns the singleton instance of this view, initializing it if necessary.
	 * Also updates the window title via the {@link Intermediary}.
	 *
	 * @param inter the intermediary used to communicate with the main frame
	 * @return the unique instance of SelectModeView
	 */
	public static SelectModeView getInstance (final Intermediary inter) {
		if (INSTANCE == null) {
			INTER = inter;
			INSTANCE = new SelectModeView();
		}
		INTER.setViewTitle(WINDOW_TITLE);
		return INSTANCE;
	}	
}