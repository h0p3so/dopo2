package domain.map.chars.cream;

import domain.LevelContextualizer;
import domain.map.tiles.Tile;

public class DeadOne extends Tile {
	public DeadOne(String frontSpritePath, char id) {
		super(frontSpritePath, id);
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
	public void interact(LevelContextualizer lc) {}

	@Override public boolean playerDependent () {
		return false;
	}
}
