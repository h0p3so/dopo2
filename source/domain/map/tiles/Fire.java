package domain.map.tiles;

import domain.LevelContextualizer;

public class Fire extends Tile {
	public Fire() {
		super(
			"assets/tiles/fire/normal.png",
			"assets/tiles/fire/normal.png",
			TileId.FLOOR_FIRE
		);
		this.deadly = false;
	}

	@Override
	public boolean isBreakable() {
		return false;
	}

	@Override
	public boolean isCoverable() {
		return false;
	}

	@Override
	public boolean isCollectable() {
		return false;
	}

	@Override
	public void interact(LevelContextualizer lc) {
	}

	@Override public boolean playerDependent () {
		return false;
	}
}
