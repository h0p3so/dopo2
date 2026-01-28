/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |			
 *      `.| hjw
 *        `
 *        
 * This view allows the player to select a character/flavour
 * before starting a level. It displays buttons corresponding
 * to each available character and highlights them with colors.
 * 
 * Note: There are multiple modes in the game, and this view
 * may not render in some modes (see game rules for details).
 *        
 * This view extends {@link BiPanel} and reuses its standardized layout
 * for information and back sections. Character buttons trigger
 * navigation to the level selection view.
 *
 * TODO: Update to dynamically retrieve available characters instead of using fixed arrays.
 *
 * @author juad - 2025
 */
package presentation.selecting;

import presentation.constants.Titles;
import presentation.recycle.BiPanel;
import presentation.recycle.Generics;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.GameMode;
import domain.map.chars.CharType;
import presentation.ButtonInfo;
import presentation.Intermediary;
import presentation.ViewsId;
import presentation.constants.Colors;
import presentation.constants.Fonts;
import presentation.constants.Paths;
import presentation.constants.Styles;

public class SingleIceCreamView extends BiPanel
{
	private static SingleIceCreamView INSTANCE;
	private static Intermediary INTER;
	
	private static final String TITLE = Titles.SELECT_CHARACTER;
	private static final String GIF = Paths.GIF_GENERAL;
	
	public SingleIceCreamView () {
		super(GIF);
	}
	
	/**
	 * Sets up the information panel with a title and the character selection buttons.
	 * Buttons are styled and have hover effects for font size and text abbreviation.
	 */
	@Override protected void setUpInformationPanel () {
		this.infoPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		
		final JLabel title = new JLabel("Pick your flavour!");
		title.setFont(Fonts.BIG);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		this.infoPanel.add(title);
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
		
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, Styles.PADDING_BIG, Styles.PADDING_BIG));
		panel.setOpaque(false);
		
		this.displayFlavourButtons(panel);
		this.infoPanel.add(panel);
		this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
	}

	/**
	 * Sets the action of the “go back” button to return to the mode selection view.
	 */
	@Override protected void setUpGoBackButtonAction () {
		this.backButton.addActionListener(e -> {
			INTER.showThisView(ViewsId.SELECT_MODE);
		});
	}
	
	/**
	 * Creates and displays the character/flavour buttons.
	 * Each button navigates to the level selection view when clicked.
	 * Buttons are colored according to the character and sized consistently.
	 *
	 * @param panel the panel where the buttons will be added
	 */
	private void displayFlavourButtons (final JPanel panel) {
		final Color [] colors = {
			Colors.CHARACTER_CHOCOLATE,
			Colors.CHARACTER_VANILLA,
			Colors.CHARACTER_STRAWBERRY,
		};
		
		final String [] shortnames = {"C", "V", "S"};
		final ButtonInfo [] info = {
			new ButtonInfo("Chocolate", ViewsId.SELECT_LEVEL, 0),
			new ButtonInfo("Vanilla", ViewsId.SELECT_LEVEL, 1),
			new ButtonInfo("Strawberry", ViewsId.SELECT_LEVEL, 2),
		};
		
		final CharType [] types = {
			CharType.CHOCOLATE,
			CharType.VANILLA,
			CharType.STRAWBERRY
		};
		
		for (int i = 0; i < info.length; i++) {
			final ButtonInfo context = info[i];
			final JButton button = Generics.createGoldenButton(context.getName(), Fonts.SMALL, Styles.BORDER_THICKNESS_MEDIUM);
			Generics.styleIncreaseFontSizeOnHover(button, Fonts.SMALL, Fonts.MEDIUM);
			Generics.styleChangeTextOnHover(button, context.getName(), shortnames[context.getPosition()]);
			
			button.addActionListener(e -> {
                INTER.getController().setGameMode(GameMode.ONE_ICE_CREAM);
				INTER.getController().setCharTypeOne(types[context.getPosition()]);
				INTER.showThisView(context.getViewId());
			});
			
			/* TODO: move magic numbers into a constants file for button sizing
			 * @link SelectLevelView
			 * @link SingleIceCreamView
			 */
			button.setPreferredSize(new Dimension(120, 90));
			button.setBackground(colors[context.getPosition()]);
			
			panel.add(button);
		}
	}

	/**
	 * Returns the singleton instance of this view.
	 * Also sets the window title for the active view.
	 *
	 * @param inter the intermediary used to communicate with the main frame
	 * @return the unique instance of SingleIceCreamView
	 */
	public static SingleIceCreamView getInstance (final Intermediary inter) {
		if (INSTANCE == null) {
			INTER = inter;
			INSTANCE = new SingleIceCreamView();
		}
		INTER.setViewTitle(TITLE);
		return INSTANCE;
	}	
}
