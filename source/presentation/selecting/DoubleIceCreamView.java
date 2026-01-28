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

public class DoubleIceCreamView extends BiPanel
{
    private static DoubleIceCreamView INSTANCE;
    private static Intermediary INTER;
    
    private static final String TITLE = Titles.SELECT_CHARACTER;
    private static final String GIF = Paths.GIF_GENERAL;
    
    private JButton[] flavorButtons;
    
    private boolean p1ready = false;
    private boolean p2ready = false;
    
    public DoubleIceCreamView () {
        super(GIF);
    }
    
    /**
     * Sets up the information panel with a title and the character selection buttons.
     * Buttons are styled and have hover effects for font size and text abbreviation.
     */
    @Override protected void setUpInformationPanel () {
        this.infoPanel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
        this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
        
        final JLabel title = new JLabel("Pick your flavours...");
        title.setFont(Fonts.BIG);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
        this.infoPanel.add(title);
        this.infoPanel.add(Box.createVerticalStrut(Styles.VERTICAL_GAP_MEDIUM));
        
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, Styles.PADDING_TINY, Styles.PADDING_TINY));
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
            new ButtonInfo("1. Chocolate", ViewsId.SELECT_LEVEL, 0),
            new ButtonInfo("1. Vanilla", ViewsId.SELECT_LEVEL, 1),
            new ButtonInfo("1. Strawberry", ViewsId.SELECT_LEVEL, 2),

            new ButtonInfo("2. Chocolate", ViewsId.SELECT_LEVEL, 3),
            new ButtonInfo("2. Vanilla", ViewsId.SELECT_LEVEL, 4),
            new ButtonInfo("2. Strawberry", ViewsId.SELECT_LEVEL, 5),
        };
        
        final CharType [] types = {
            CharType.CHOCOLATE,
            CharType.VANILLA,
            CharType.STRAWBERRY
        };
        
        this.flavorButtons = new JButton[info.length]; 
        
        for (int i = 0; i < info.length; i++) {
            final ButtonInfo context = info[i];
            this.flavorButtons[i] = Generics.createGoldenButton(context.getName(), Fonts.SMALL, Styles.BORDER_THICKNESS_MEDIUM);
            Generics.styleIncreaseFontSizeOnHover(this.flavorButtons[i], Fonts.SMALL, Fonts.MEDIUM);
            Generics.styleChangeTextOnHover(this.flavorButtons[i], context.getName(), shortnames[context.getPosition() % 3]);
            this.flavorButtons[i].setPreferredSize(new Dimension(90, 60));
            this.flavorButtons[i].setBackground(colors[context.getPosition() % 3]);
            panel.add(this.flavorButtons[i]);
        }
        
        for (int i = 0; i < info.length; i++) {
            final ButtonInfo context = info[i];
            final JButton currentButton = this.flavorButtons[i];
            
            if (i < 3) {
                currentButton.addActionListener(e -> {
                    INTER.getController().setCharTypeOne(types[context.getPosition() % 3]);
                    for (int j = 0; j < 3; j++) {
                        this.flavorButtons[j].setEnabled(false);
                    }
                    this.flavorButtons[context.getPosition() % 3 + 3].setEnabled(false);
                    
                    p1ready = true;
                    
                    if (p1ready && p2ready) {
                        INTER.getController().setGameMode(GameMode.TWO_ICE_CREAM);
                        INTER.showThisView(context.getViewId());
                    }
                });
            }
            else {
                currentButton.addActionListener(e -> {
                    INTER.getController().setCharTypeTwo(types[context.getPosition() % 3]);
                    p2ready = true;
                    
                    if (!p1ready) {
                        for (int j = 3; j < 6; j++) {
                            this.flavorButtons[j].setEnabled(false);
                        }
                        this.flavorButtons[context.getPosition() % 3].setEnabled(false);
                    }
                    else { 
                        INTER.getController().setGameMode(GameMode.TWO_ICE_CREAM);
                        INTER.showThisView(context.getViewId());
                    }
                });
            }    
        }
    }

    /**
     * Resets the view's state: re-enables all buttons and resets the player readiness flags.
     */
    private void resetView () {
        this.p1ready = false;
        this.p2ready = false;
        
        // Re-enable all flavor buttons if they exist
        if (this.flavorButtons != null) {
            for (JButton button : this.flavorButtons) {
                if (button != null) {
                    button.setEnabled(true);
                }
            }
        }
    }

    /**
     * Returns the singleton instance of this view.
     * Also sets the window title for the active view and resets the view state.
     *
     * @param inter the intermediary used to communicate with the main frame
     * @return the unique instance of DoubleIceCreamView
     */
    public static DoubleIceCreamView getInstance (final Intermediary inter) {
        if (INSTANCE == null) {
            INTER = inter;
            INSTANCE = new DoubleIceCreamView();
        }
        INSTANCE.resetView();
        
        INTER.setViewTitle(TITLE);
        return INSTANCE;
    }    
}