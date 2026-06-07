/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * This view displays the animated background, a central “start” button,
 * and an overlay modal containing several navigation choices such as
 * play, scores, help, and credits.
 *
 * The modal panel is implemented as a custom glass pane that appears
 * above the main view, providing a simple modal-like effect without
 * additional window management.
 *
 * Access to navigation and interaction with the rest of the program
 * is delegated to an {@link Intermediary}, which communicates user
 * actions back to the main frame (see {@link BaDopoCreamGUI}).
 *
 * Instances of this class follow the Singleton pattern to maintain
 * consistency of UI state across transitions.
 *
 * @author juad - 2025
 */
package presentation;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import exceptions.UserException;
import presentation.constants.Fonts;
import presentation.constants.Paths;
import presentation.constants.Styles;
import presentation.constants.Titles;
import presentation.recycle.Generics;
import presentation.recycle.GifPanel;

public class HomeView extends GifPanel {
	private static HomeView INSTANCE;
	private static Intermediary INTER;

	private static final int MODAL_NUMBER_OF_BUTTONS = 4;

	private static final String GIF = Paths.GIF_HOME;
	private static final String TITLE = Titles.HOME;

	private JPanel glass;
	private JPanel modal;
	private JButton startbutton;

	private HomeView () {
		super(GIF);
		this.setLayout(null);
		this.initGlassPane();
		this.initStartButton();
	}

	/**
	 * Initializes the glass pane used to render the modal window.
	 * The pane is added directly to the main frame through the
	 * {@link Intermediary} reference.
	 */
	private void initGlassPane () {
		this.glass = new JPanel(null);
		this.glass.setBackground(new Color(0, 0, 0, 150));
		this.initModalPane();
		this.glass.add(this.modal);
		INTER.addAGlassPane(this.glass);
	}

	/**
	 * Builds and positions the main “start” button. When clicked,
	 * the button displays the modal window containing additional
	 * navigation options.
	 */
	private void initStartButton () {
		this.startbutton = Generics.createGoldenButton(
			"CLICK TO LICK",
			Fonts.BIG,
			Styles.BORDER_THICKNESS_BIG
		);

		this.startbutton.setBounds(
			(BaDopoCreamGUI.WINDOW_WIDTH - 200) / 2,
			(BaDopoCreamGUI.WINDOW_HEIGHT - 60) / 2
				+ (int) ((BaDopoCreamGUI.WINDOW_HEIGHT - 200) * 0.4),
			200,
			60
		);

		this.startbutton.addActionListener(e -> this.glass.setVisible(true));
		this.add(this.startbutton);
	}

	/**
	 * Initializes the modal panel that appears inside the glass pane.
	 * The panel is styled and centered according to the window size.
	 */
	private void initModalPane () {
		final int modal_win_height = (int) (BaDopoCreamGUI.WINDOW_HEIGHT / 3);
		final int modal_win_width  = (int) (BaDopoCreamGUI.WINDOW_WIDTH  / 2);

		this.modal = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_BIG);
		this.modal.setLayout(new GridLayout(MODAL_NUMBER_OF_BUTTONS, 1));
		this.modal.setBounds(
			(BaDopoCreamGUI.WINDOW_WIDTH - modal_win_width) / 2,
			modal_win_height,
			modal_win_width,
			modal_win_height
		);

		this.initModalPanelButtons();
	}

	/**
	 * Creates and assigns the modal buttons based on an internal
	 * {@link ButtonInfo} configuration. Each button either triggers
	 * navigation to a new view (via {@link Intermediary})
	 * or raises a {@link UserException} if the action is not yet
	 * implemented.
	 */
	private void initModalPanelButtons () {
		final ButtonInfo [] info = {
			new ButtonInfo("PLAY",    ViewsId.SELECT_MODE, 0),
			new ButtonInfo("LOAD",    null, 1),
			new ButtonInfo("HELP",    null, 2),
			new ButtonInfo("CREDITS", null, 3),
		};

		for (int i = 0; i < MODAL_NUMBER_OF_BUTTONS; i++) {
			final ButtonInfo context = info[i];
			final JButton button = Generics.createGoldenButton(
				context.getName(),
				Fonts.SMALL,
				Styles.BORDER_THICKNESS_NONE
			);

			Generics.styleIncreaseFontSizeOnHover(button, Fonts.SMALL, Fonts.MEDIUM);

			button.addActionListener(e -> {
				try {
					if (context.getName() == "LOAD") {
						INTER.getController().loadStateFromFile(
							INTER.getController().pickFile()
						);
						this.glass.setVisible(false);
						INTER.showThisView(ViewsId.LEVEL);
						return;
					}
					else if (context.getViewId() == null) {
						throw new UserException(UserException.UNIMPLEMENTED_ACTION);
					}
					

					this.glass.setVisible(false);
					INTER.showThisView(context.getViewId());
				}
				catch (final UserException u) {
					INTER.indicateUserException(u.getMessage());
				}
			});

			this.modal.add(button);
		}
	}

	/**
	 * Provides access to the Singleton instance of this view.
	 * A reference to an {@link Intermediary} must be supplied to
	 * allow the view to communicate with the main frame.
	 *
	 * @param inter intermediary object used for view transitions
	 * @return the unique instance of this view
	 */
	public static HomeView getInstance (final Intermediary inter) {
		if (INSTANCE == null) {
			INTER = inter;
			INSTANCE = new HomeView();
		}
		INTER.setViewTitle(TITLE);
		return INSTANCE;
	}
}
