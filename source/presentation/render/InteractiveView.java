package presentation.render;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;

import javax.swing.JPanel;

import domain.GameMode;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.fruits.Fruit;
import domain.map.tiles.Tile;
import presentation.Intermediary;

public class InteractiveView extends JPanel {
    private Intermediary inter;
    
    public InteractiveView (final Intermediary inter) {
        this.inter = inter;
        this.setOpaque(false);
        this.initCharOne();
        
        if (GameMode.isTwoPlayerMode(inter.getController().getMode())) {
        	this.initCharTwo();
        }
    }
    
    public void initCharOne () {
       	final int x = this.inter.getController().getBluePrint().getPlayer1Position().getX();
       	final int y = this.inter.getController().getBluePrint().getPlayer1Position().getY();

       	this.inter.getController().getCharacter1().getPosition().setX(x);
       	this.inter.getController().getCharacter1().getPosition().setY(y);
    }

    public void initCharTwo () {
        if (GameMode.isTwoPlayerMode(inter.getController().getMode())) {
        	final int x = this.inter.getController().getBluePrint().getPlayer2Position().getX();
        	final int y = this.inter.getController().getBluePrint().getPlayer2Position().getY();
        	this.inter.getController().getCharacter2().getPosition().setX(x);
        	this.inter.getController().getCharacter2().getPosition().setY(y);
        }
    }
    
    private void drawEnemoes (final Graphics g) {
        ArrayList<Character> enemies = this.inter.getController().getEnemies();
        
        for (int i = 0; i < enemies.size(); i++) {
            this.renderCharacter(g, enemies.get(i));
        }
    }
    
    public void drawTiles (final Graphics g) {
    	Tile[][] tiles = this.inter.getController().getTiles();
    	for (Tile[] ts: tiles) {
    		for (Tile t: ts) {
    			t.interact(this.inter.getController().getContextzr());
    		}
    	}
    }

    public void drawDependentTiles (final Graphics g) {
    	Tile[][] tiles = this.inter.getController().getTiles();
    	for (Tile[] ts: tiles) {
    		for (Tile t: ts) {
    			if (t.playerDependent()) {
    				t.interact(this.inter.getController().getContextzr());
    			}
    		}
    	}
    }
    
    public void updateMap () {
        this.repaint();
    }
    
    private void renderCharacter (final Graphics g, final Character ch) {
        final int charPixelX = ch.getPosition().getX() * StaticTilesView.TILE_DIMENSIONS;
        final int charPixelY = ch.getPosition().getY() * StaticTilesView.TILE_DIMENSIONS;

        g.drawImage(
            ch.getCurrentSprite(),
            charPixelX,
            charPixelY,
            StaticTilesView.TILE_DIMENSIONS,
            StaticTilesView.TILE_DIMENSIONS,
            this
        );

        if (ch == domain.Control.ch1) {
        	this.drawDependentTiles(g);
        }
    }

    @Override protected void paintComponent (final Graphics g) {
        super.paintComponent(g);
        
        if (!this.inter.getController().getCharacter1().isDead()) {
        	this.renderCharacter(g, this.inter.getController().getCharacter1());
        }
        if (GameMode.isTwoPlayerMode(inter.getController().getMode()) && !this.inter.getController().getCharacter2().isDead()) { 
        	this.renderCharacter(g, this.inter.getController().getCharacter2());
        }

        this.drawEnemoes(g);
    }
}
