package domain.map.chars.cream;

import domain.map.tiles.TileId;
import presentation.constants.Paths;

public class Vanilla extends IceCream {
	private static final String SPRITE_FRONT_PATH = "assets/characters/vanilla/front.png";
	private static final String SPRITE_BACK_PATH = "assets/characters/vanilla/back.png";
	private static final String SPRITE_LEFT_PATH = "assets/characters/vanilla/left.png";
	private static final String SPRITE_RIGHT_PATH = "assets/characters/vanilla/right.png";
	public static final String SPRITE_DEAD_PATH = "assets/characters/vanilla/dead.png";
	
	public Vanilla () {
		super(SPRITE_FRONT_PATH, SPRITE_BACK_PATH, SPRITE_LEFT_PATH, SPRITE_RIGHT_PATH, TileId.DEAD_VANILLA);
	}
}
