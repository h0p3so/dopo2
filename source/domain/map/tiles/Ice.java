package domain.map.tiles;

import domain.LevelContextualizer;
import presentation.constants.Paths;

public class Ice extends Tile {
	private static final String FRONT_SPRITE_PATH = "assets/tiles/ice/front.png";
	private static final String BROKEN_SPRITE_PATH = "assets/tiles/ice/broken.png";

	public Ice() {
		super(FRONT_SPRITE_PATH, BROKEN_SPRITE_PATH, TileId.BLOCK_ICE);
	}
	
	@Override public boolean isWalkable () {
		return false;
	}
	
	@Override public boolean isBreakable() {
		return true;
	}
	
	@Override public boolean isCoverable () {
		return false;
	}

	@Override public boolean isCollectable () {
		return false;
	}

	@Override
	public void interact(LevelContextualizer lc) {}
	
	@Override public boolean playerDependent () {
		return false;
	}
}