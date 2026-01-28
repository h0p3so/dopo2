package presentation.render;


import java.awt.Graphics;

import javax.swing.JPanel;

import domain.map.BluePrint;
import presentation.Drawable;
import presentation.Intermediary;
import presentation.constants.Colors;

public class StaticTilesView extends JPanel {
    public static final int TILE_DIMENSIONS = 35;
    
    private Intermediary inter;
    
    public StaticTilesView (final Intermediary inter) {
        this.inter = inter;
    }

    public void updateMap () {
        this.repaint();
    }
    
    @Override protected void paintComponent (final Graphics g) {
        super.paintComponent(g);
        this.setBackground(Colors.SNOW);
        
        for (int row = 0; row < BluePrint.NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < BluePrint.NUMBER_OF_COLS; col++) {
                g.drawImage(
                    this.inter.getController().getTileAt(row, col).getFrontSideSprite(),
                    col * TILE_DIMENSIONS,
                    row * TILE_DIMENSIONS,
                    TILE_DIMENSIONS,
                    TILE_DIMENSIONS,
                    this
                );
            }
        }
    }    
}