package domain.map.fruits;

import domain.LevelContextualizer;
import domain.map.tiles.TileId;
import presentation.constants.Paths;

public class Grapes extends Fruit {
	private static final String SPRITE_FRONT_PATH = "assets/fruits/grapes/normal.png";
	private static final String SPRITE_FROZEN_PATH = "assets/fruits/grapes/frozen.png";
	
	public Grapes () {
		super(SPRITE_FRONT_PATH, SPRITE_FROZEN_PATH, 50, TileId.FRUIT_GRAPES);
	}
	
	@Override public void interact (final LevelContextualizer lc) {}

    @Override public boolean moveable () {
    	return false;
    }
}
