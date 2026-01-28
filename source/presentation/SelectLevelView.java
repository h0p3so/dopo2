/**
 *     _
 *    ,' `,.
 *    >-.(__)
 *   (_,-' |
 *     `.  |
 *       `.| hjw
 *         `
 * This view allows the player to select a level before starting
 * the game. It renders a grid of level buttons indicating
 * which levels are available and disabled ones for locked levels.
 *
 * The view extends {@link MonoPanel} and reuses its layout, displaying the
 * informational content (level buttons) and a “go back” button in a single
 * panel. Buttons dynamically load the selected level via the controller.
 *
 * TODO: Enhance navigation by returning to the previous view instead of always going back to home.
 *
 * @author juad - 2025
 */
package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.LevelAvailableness;
import presentation.constants.Fonts;
import presentation.constants.Paths;
import presentation.constants.Styles;
import presentation.constants.Titles;
import presentation.recycle.Generics;
import presentation.recycle.MonoPanel;
import presentation.render.LevelView;

public class SelectLevelView extends MonoPanel {
    private static SelectLevelView INSTANCE = null;
    private static Intermediary INTER;
    
    private static final String WINDOW_TITLE = Titles.SELECT_LEVEL_GAMING;
    private static final String GIF = Paths.GIF_GENERAL;
    
    private static final int LEVEL_ROWS = 8;
    private static final int LEVEL_COLS = 5;
    private static final int BUTTON_DIMENSION = 30;
    
    private JButton [] buttons;
    
    private SelectLevelView () {
        super(GIF, "PICK A LEVEL!");
    }

    /**
     * Initializes the information panel with a grid of level buttons.
     * Disabled buttons indicate unavailable levels, and clicking an
     * enabled button loads the corresponding level and navigates to
     * the {@link LevelView}.
     */
    @Override public void setUpInformationPanel () {
        this.buttons = new JButton[40];
        this.singlePanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_NONE);
        this.singlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, Styles.PADDING_TINY, Styles.PADDING_TINY));
        
        final JPanel panel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_NONE);
        panel.setLayout(new GridLayout(LEVEL_ROWS, LEVEL_COLS, Styles.PADDING_TINY, Styles.PADDING_TINY));
        
        if (INTER != null && INTER.getController() != null) {
            final boolean [] playable = INTER.getController().pleaseProvideListOfAvailableLevels();

            for (int i = 0; i < LevelAvailableness.MAX_LEVELS; i++) {
                final int levelnumber = i;
                this.buttons[i ]= Generics.createGoldenButton(
                    Integer.toString(levelnumber),
                    Fonts.SMALL,
                    Styles.BORDER_THICKNESS_TINY
                );
                
                Generics.styleIncreaseFontSizeOnHover(this.buttons[i], Fonts.SMALL, Fonts.MEDIUM);
                this.buttons[i].setPreferredSize(new Dimension(BUTTON_DIMENSION, BUTTON_DIMENSION));
                panel.add(this.buttons[i]);
                
                if (!playable[i]) {
                    this.buttons[i].setEnabled(false);
                    continue;
                }
                
                this.buttons[i].setEnabled(true);
                this.buttons[i].addActionListener(e -> {
                    INTER.getController().pleaseLoadPredefinedLevel(levelnumber);
                    INTER.showThisView(ViewsId.LEVEL);
                });
            }
        }
        this.singlePanel.add(panel);
    }

    /**
     * Sets the action for the “go back” button. Currently returns to the
     * {@link HomeView}, but this could be enhanced to return to the
     * previous view dynamically.
     */
    @Override protected void setUpGoBackButtonAction () {
        this.backButton.addActionListener(e -> {
            INTER.showThisView(ViewsId.HOME_VIEW);
        });
    }
    
    private void updateButtons () {
    	for (int i = 0; i < this.buttons.length; i++) {
    		boolean ok = INTER.getController().pleaseProvideListOfAvailableLevels()[i];
    		if (!ok) continue;
    		
    		final int j = i;
    		this.buttons[i].setEnabled(true);
    		this.buttons[i].addActionListener(e -> {
    			INTER.getController().pleaseLoadPredefinedLevel(j);
    			INTER.showThisView(ViewsId.LEVEL);
    		});
    	}
    }
    
    /**
     * Returns the singleton instance of this view, setting the window title
     * and initializing the intermediary reference.
     *
     * @param inter     intermediary used to communicate with the main frame
     * @return          the unique instance of SelectLevelView
     */
    public static SelectLevelView getInstance (final Intermediary inter) {
        if (INSTANCE == null) {
            INTER = inter;
            INSTANCE = new SelectLevelView();
        }
        INTER.setViewTitle(WINDOW_TITLE);
        INSTANCE.updateButtons();
        return INSTANCE;
    }
}