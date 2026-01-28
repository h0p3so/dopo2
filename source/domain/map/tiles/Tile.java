package domain.map.tiles;

import domain.LevelContextualizer;
import presentation.DrawableAdapter;

public abstract class Tile extends DrawableAdapter {
	private char id;
	protected boolean beingFrozen;

	protected boolean deadly;
	
	protected Tile (final String frontSpritePath, final char id) {
		super(frontSpritePath);
		this.id = id;
		this.beingFrozen = false;
		this.deadly = false;
	}

	protected Tile (final String frontSpritePath, final String alternativeSpritePath, final char id) {
		super(frontSpritePath, alternativeSpritePath);
		this.id = id;
		this.deadly = false;
	}	
	
	public char getId () {
		return this.id;
	}
	
	public void toggleFrozen () {
		this.beingFrozen = !this.beingFrozen;
	}

	public boolean isWalkable () {
		return !this.beingFrozen;
	}
	
	public boolean isFrozen () {
		return this.beingFrozen;
	}
	
	public boolean isDeadly () {
		return this.deadly;
	}
	
	public abstract boolean playerDependent ();

	public abstract boolean isBreakable ();
	public abstract boolean isCoverable ();
	public abstract boolean isCollectable ();
	public abstract void interact (final LevelContextualizer lc);
}
