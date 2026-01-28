package presentation.render;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.GameMode;
import presentation.Intermediary;
import presentation.ViewsId;
import presentation.constants.Colors;
import presentation.constants.Fonts;
import presentation.constants.GameEndsUp;
import presentation.constants.Styles;
import presentation.recycle.Generics;

public class WinLostView { 
    private JPanel panel;
    private Intermediary inter;
    private JLabel socreP1;
    private JLabel socreP2;
    private JLabel message;
    
    private JButton restartButton;
    private JButton continueButton;
    
    public WinLostView (final Intermediary in) {
        this.inter = in;

        this.panel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
        this.panel.setLayout(new java.awt.BorderLayout()); 

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        this.message = new JLabel("VICTORY!");
        this.message.setFont(Fonts.HDU.deriveFont(java.awt.Font.BOLD, 48f)); 
        this.message.setForeground(Colors.PANEL_BORDERS);
        this.message.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.socreP1 = new JLabel("");
        this.socreP1.setFont(Fonts.SMALL.deriveFont(java.awt.Font.PLAIN, 18f));
        this.socreP1.setForeground(Colors.PANEL_BORDERS);
        this.socreP1.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.socreP2 = new JLabel("");
        this.socreP2.setFont(Fonts.SMALL.deriveFont(java.awt.Font.PLAIN, 18f));
        this.socreP2.setForeground(Colors.PANEL_BORDERS);
        this.socreP2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(this.message);
        contentPanel.add(this.socreP1);
        
        this.socreP2.setVisible(false);
        contentPanel.add(this.socreP2);
        
        this.panel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = Generics.createGoldenPanel(0);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Font buttonFont = Fonts.SMALL;

        this.continueButton = Generics.createGoldenButton(
            "Continue", 
            buttonFont, 
            Styles.BORDER_THICKNESS_NONE
        );
        Generics.styleIncreaseFontSizeOnHover(this.continueButton, buttonFont, Fonts.MEDIUM);
        this.continueButton.addActionListener(e -> {
        	inter.getController().goNextLevel();
        });
        
        this.restartButton = Generics.createGoldenButton(
            "Restart", 
            buttonFont, 
            Styles.BORDER_THICKNESS_NONE
        );
        Generics.styleIncreaseFontSizeOnHover(this.restartButton, buttonFont, Fonts.MEDIUM);
        this.restartButton.addActionListener(e -> {
            inter.getController().restartLevel();
        });
        
        JButton homeButton = Generics.createGoldenButton(
            "Go Back Home", 
            buttonFont, 
            Styles.BORDER_THICKNESS_NONE
        );
        
        homeButton.addActionListener(e -> {
            in.showThisView(ViewsId.HOME_VIEW);
        });
        Generics.styleIncreaseFontSizeOnHover(homeButton, buttonFont, Fonts.MEDIUM);
        
        buttonPanel.add(this.continueButton);
        buttonPanel.add(this.restartButton);
        buttonPanel.add(homeButton);
        
        this.panel.add(buttonPanel, BorderLayout.SOUTH);
        this.renderFinalInfo(GameEndsUp.WINNING); 
    }
    
    /**
     * Updates the message, score display, and button visibility based on the game result.
     * @param in The result of the game (WINNING or LOSING).
     */
    public void renderFinalInfo (final GameEndsUp in) {   
        this.message.setText(in == GameEndsUp.LOSING ? "GAME OVER" : "VICTORY, JAY!");
        
        String scoreText = "Player 1 score: " + this.inter.getController().getCh1Score();
        this.socreP1.setText(scoreText);             

        if (this.inter.getController().getMode() == GameMode.TWO_ICE_CREAM) {
            scoreText = "Player 2 score: " + this.inter.getController().getCh2Score();
            this.socreP2.setText(scoreText);             
            this.socreP2.setVisible(true);
        }
        else {
            this.socreP2.setVisible(false);
        }
        
        if (in == GameEndsUp.LOSING) {
            this.restartButton.setVisible(true);
            this.continueButton.setVisible(false);
        } else {
            this.restartButton.setVisible(false);
            this.continueButton.setVisible(true);
        }
    }
    
    public JPanel getPanel() {
        return this.panel;
    }
}