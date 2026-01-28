package presentation.render;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exceptions.UserException;
import presentation.Intermediary;
import presentation.ViewsId;
import presentation.constants.Colors;
import presentation.constants.Fonts;
import presentation.constants.Styles;
import presentation.recycle.Generics;

public class PauseView { 
    private JPanel panel;
    private Intermediary inter;
    private JLabel titleMessage;
    
    private JButton resumeButton;
    private JButton saveGameButton;

    /**
     * Initializes the Pause View.
     * @param in The Intermediary for communication with the controller and main view.
     */
    public PauseView (final Intermediary in, RenderComunication rc) {
        this.inter = in;

        this.panel = Generics.createGoldenPanel(Styles.BORDER_THICKNESS_MEDIUM);
        this.panel.setLayout(new java.awt.BorderLayout()); 

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        this.titleMessage = new JLabel("GAME PAUSED");
        this.titleMessage.setFont(Fonts.HDU.deriveFont(java.awt.Font.BOLD, 48f)); 
        this.titleMessage.setForeground(Colors.PANEL_BORDERS);
        this.titleMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(this.titleMessage);
        
        this.panel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = Generics.createGoldenPanel(0);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Font buttonFont = Fonts.SMALL;

        this.resumeButton = Generics.createGoldenButton(
            "Resume", 
            buttonFont, 
            Styles.BORDER_THICKNESS_NONE
        );
        Generics.styleIncreaseFontSizeOnHover(this.resumeButton, buttonFont, Fonts.MEDIUM);
        this.resumeButton.addActionListener(e -> {
        	rc.resumeGame();
        });
        
        this.saveGameButton = Generics.createGoldenButton(
            "Save Game", 
            buttonFont, 
            Styles.BORDER_THICKNESS_NONE
        );
        Generics.styleIncreaseFontSizeOnHover(this.saveGameButton, buttonFont, Fonts.MEDIUM);
        this.saveGameButton.addActionListener(e -> {
        	try {
        		inter.getController().storeCurrentStateOfGame(inter.getController().pickFile());
        	} catch (final UserException ex) {
        		inter.indicateUserException(ex.getMessage());
        	}
        });
        
        buttonPanel.add(this.resumeButton);
        buttonPanel.add(this.saveGameButton);
        this.panel.add(buttonPanel, BorderLayout.SOUTH);
    }    
    
    public JPanel getPanel () {
    	return this.panel;
    }
}