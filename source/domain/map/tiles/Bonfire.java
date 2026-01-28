package domain.map.tiles;

import java.awt.Image;

import domain.LevelContextualizer;

public class Bonfire extends Tile {
	public Bonfire () {
		super(
			"assets/tiles/bonfire/normal.png",
			"assets/tiles/bonfire/frozen.png",
			TileId.FLOOR_BONFIRE
		);
		this.deadly = true;
	}

	@Override
	public boolean isBreakable() {
		return false;
	}

	@Override
	public boolean isCoverable() {
		return true;
	}

	@Override
	public boolean isCollectable() {
		return false;
	}

	@Override
	public void interact (final LevelContextualizer lc) {	
		if (this.deadly) {
			return;
		}
		
		this.deadly = true;
		Image temp = this.frontSprite;
		this.frontSprite = this.alternativeSprite;
		this.alternativeSprite = temp;
	}
	
	@Override public void swapBetweenFrontAndAlternative () {
		if (this.deadly) {
			Image temp = this.frontSprite;
			this.frontSprite = this.alternativeSprite;
			this.alternativeSprite = temp;
			this.deadly = false;
		}	
		this.beingFrozen = false;
	}

	@Override public boolean playerDependent () {
		return false;
	}
}
