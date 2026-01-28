package domain.map.tiles;

import domain.LevelContextualizer;
import presentation.constants.Paths;

public class SmallSnowHeap extends Tile {
	private static final String SPRITE_FRONT_PATH = "assets/tiles/floor/small-snow-heap-front.png";
	private static final String SPRITE_COVER_PATH = "assets/tile/ice/normal.png";
	
	public SmallSnowHeap () {
		super(SPRITE_FRONT_PATH, SPRITE_COVER_PATH, TileId.FLOOR_SMALL_SNOW_HEAP);
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
