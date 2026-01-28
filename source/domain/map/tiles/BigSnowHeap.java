package domain.map.tiles;

import domain.LevelContextualizer;
import presentation.constants.Paths;

public class BigSnowHeap extends Tile {
	private static final String SPRITE_FRONT_PATH = "assets/tiles/floor/big-snow-heap-front.png";
	private static final String SPRITE_COVER_PATH = "assets/tiles/ice/front.png";

	public BigSnowHeap () {
		super(SPRITE_FRONT_PATH, SPRITE_COVER_PATH, TileId.FLOOR_BIG_SNOW_HEAP);
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
