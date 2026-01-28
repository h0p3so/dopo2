package presentation.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import domain.GameMode;
import presentation.Intermediary;
import presentation.constants.Fonts;

public class HUDView extends JPanel {
    private Intermediary inter;

    public HUDView(final Intermediary inter) {
        this.inter = inter;
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(java.awt.Color.WHITE);
        g2d.setFont(Fonts.HDU);
        
        final String p1poitns = String.format("1P %05d", this.inter.getController().getCharacter1().getScore());
        g2d.drawString(p1poitns, 25, 25);
        
        if (GameMode.isTwoPlayerMode(inter.getController().getMode())) {
        	final String p2poitns = String.format("2P %05d", this.inter.getController().getCharacter2().getScore());
        	g2d.drawString(p2poitns, 25, 50);
        }

        final int sexs = this.inter.getController().getTimeLeft();
        final String time = String.format("%02d:%02d", sexs / 60, sexs % 60);
        g2d.drawString(time, getSize().width - 100, 25);
    }
}