package domain.map.tiles;

import domain.LevelContextualizer;
import presentation.constants.Paths;

public class BasicDelimiter extends Tile {
	private static final String FRONT_SPRITE_PATH = "assets/tiles/delimiters/basic-front.png";

	public BasicDelimiter () {
		super(FRONT_SPRITE_PATH, TileId.DELIMITER_BASIC);
	}
	
	@Override public boolean isWalkable () {
		return false;
	}

	@Override public boolean isBreakable() {
		return false;
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
