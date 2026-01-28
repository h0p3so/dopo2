package domain.map.fruits;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

import domain.GoingDirection;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.map.chars.Position;
import domain.map.tiles.Tile;

public abstract class Fruit extends Tile {
	private int pointsgiven;
	protected int rpf;
	protected GoingDirection lastDirection;
	protected Position pos;
	protected Position wishedPos;
	
	private Image deadlyversion;
	private Image normal, deadly_;
		
	public Fruit (final String frontPath, final String coveredPath, final int pointsByEatingMe, char childId) {
		super(frontPath, coveredPath, childId);
		this.pointsgiven = pointsByEatingMe;
		this.rpf = 0;
		this.pos = new Position(0, 0);
		this.lastDirection = GoingDirection.FRONT;
	}

	public Fruit (final String frontPath, final String coveredPath, final String deadlyPath, final int pointsByEatingMe, char childId) {
		super(frontPath, coveredPath, childId);
		this.deadlyversion = new ImageIcon(deadlyPath).getImage();

		this.deadly_ = new ImageIcon(deadlyPath).getImage();
		this.normal = new ImageIcon(frontPath).getImage();

		this.pointsgiven = pointsByEatingMe;
		this.rpf = 0;
		this.pos = new Position(0, 0);
		this.lastDirection = GoingDirection.FRONT;
	}
	
	protected Position simulateMotion (final MotionDirection towards) {
		final GoingDirection trying = MotionDirection.turnIntoGoingDirection(towards);
		if (trying != this.lastDirection) {
			this.lastDirection = trying;
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
		return simulated;
	}
	
	public abstract void interact (final LevelContextualizer lc);

	@Override
	public boolean isBreakable () {
		return false;
	}

	@Override
	public boolean isCoverable () {
		return true;
	}
	
	public int getPointsGiven () {
		return this.pointsgiven;
	}

	@Override public boolean isCollectable () {
		return true;
	}

	@Override public boolean playerDependent () {
		return false;
	}
		
	public void toggleSprite () {
		Image temp = this.frontSprite;
		this.frontSprite = this.alternativeSprite;
		this.alternativeSprite = temp;
	}
	
	public void setPosition (final int row, final int col) {
		this.pos.setX(col);
		this.pos.setY(row);
	}
	
	public void setDeadlyVersion () {
		if (!this.deadly) {
			this.swapBetweenFrontAndAlternative(this.deadly_);
		}
		else {
			this.swapBetweenFrontAndAlternative(this.normal);
		}
	}
	
	public abstract boolean moveable ();
	
	public Position getPosition () {
		return this.pos;
	}
}
