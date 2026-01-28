package domain.map.tiles;

import domain.LevelContextualizer;
import presentation.constants.Paths;

public class Snow extends Tile {
	private static final String FRONT_SPRITE_PATH = "";
	private static final String SPRITE_COVER_PATH = "assets/tile/ice/normal.png";

	public Snow () {
		super(FRONT_SPRITE_PATH, SPRITE_COVER_PATH, TileId.EMPTY);
	}

	@Override public boolean isBreakable() {
		return false;
	}
	
	@Override public boolean isCoverable () {
		return true;
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
