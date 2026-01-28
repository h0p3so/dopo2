package domain.map.chars.cream;

import domain.map.tiles.TileId;
import presentation.constants.Paths;

public class Strawberry extends IceCream {
	private static final String SPRITE_FRONT_PATH = "assets/characters/strawberry/front.png";
	private static final String SPRITE_BACK_PATH = "assets/characters/strawberry/back.png";
	private static final String SPRITE_LEFT_PATH = "assets/characters/strawberry/left.png"; 
	private static final String SPRITE_RIGHT_PATH = "assets/characters/strawberry/right.png";
	public static final String SPRITE_DEAD_PATH = "assets/characters/strawberry/dead.png";
	
	public Strawberry () {
		super(SPRITE_FRONT_PATH, SPRITE_BACK_PATH, SPRITE_LEFT_PATH, SPRITE_RIGHT_PATH, TileId.DEAD_STRAWBERRY);
	}
}
