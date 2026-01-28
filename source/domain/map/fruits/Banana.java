package domain.map.fruits;

import domain.LevelContextualizer;
import domain.map.tiles.TileId;
import presentation.constants.Paths;

public class Banana extends Fruit {
	private static final String SPRITE_FRONT_PATH = "assets/fruits/banana/normal.png";
	private static final String SPRITE_ALTR_PATH = "assets/fruits/banana/frozen.png";

	public Banana() {
		super(SPRITE_FRONT_PATH, SPRITE_ALTR_PATH, 100, TileId.FRUIT_BANANA);
	}

	@Override
	public void interact(final LevelContextualizer lc) {}

	@Override public boolean moveable () {
		return false;
	}
}
