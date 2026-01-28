package domain.map.chars;

import java.awt.Image;
import javax.swing.ImageIcon;

import domain.GameMode;
import domain.GoingDirection;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.MotionEndsUp;
import presentation.DrawableAdapter;

public abstract class Character extends DrawableAdapter {
	protected Position pos;
	protected Position wishedPos;
	protected GoingDirection lastDirection;
	private int score;
	protected boolean dead;
	
	private Image [] renderings;
	private Image sprite;
	
	protected Character (final String frontSpritePath, final String backSpritePath, final String leftSpritePath, final String rightSpritePath) {
		super(frontSpritePath, backSpritePath, leftSpritePath, rightSpritePath);
		this.pos = new Position(0, 0);
		
		this.renderings = new Image [] {
			this.frontSprite,
			this.backSprite,
			this.leftSprite,
			this.rightSprite
		};
		this.score = 0;
		this.lastDirection = GoingDirection.FRONT;
		this.dead = false;
		this.setSprite();
	}	
	
	public Position getPosition () {
		return this.pos;
	}
	
	protected Position simulateMotion (final MotionDirection towards) {
		final GoingDirection trying = MotionDirection.turnIntoGoingDirection(towards);
		if (trying != this.lastDirection) {
			this.lastDirection = trying;
			this.setSprite();
			return this.pos;
		}
		
		final int currentx = this.pos.getX();
		final int currenty = this.pos.getY();
		Position simulated = new Position(currentx, currenty);

		switch (towards) {
			case UP: simulated.setY(currenty - 1); break;
			case DOWN: simulated.setY(currenty + 1); break;
			case LEFT: simulated.setX(currentx - 1); break;
			case RIGHT: simulated.setX(currentx + 1); break;
		}
		
		this.lastDirection = MotionDirection.turnIntoGoingDirection(towards);
		this.setSprite();
		return simulated;
	}
	
	private void setSprite () {
		this.sprite = this.renderings[this.lastDirection.getAsIndex()];
	}
	
	public Image getCurrentSprite () {
		return this.sprite;
	}

	public GoingDirection getGoingDirection () {
		return this.lastDirection;
	}

	public int getScore () {
		return this.score;
	}
	
	public void incScore (final int by) {
		this.score += by;
	}
	
	public void setDead () {
		this.dead = true;
		this.frontSprite = new ImageIcon("").getImage();
	}
	
	public boolean isDead () {
		return this.dead;
	}

	// this function is defined in order to know where the characters
	// is indicated to move even if it does not move by external factors
	public Position getWishedPositionToMove () {
		return this.wishedPos;
	}
	
	public abstract MotionEndsUp autonomousMode (final MotionDirection towards, final LevelContextualizer lc, final GameMode mode);
	public abstract MotionEndsUp playableMove (final MotionDirection towards, final LevelContextualizer lc);
	
	public abstract boolean killsIceCream ();
	public abstract boolean doesItMelt ();
}
