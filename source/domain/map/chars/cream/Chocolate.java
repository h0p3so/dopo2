package domain.map.chars.cream;
import domain.map.tiles.TileId;
import presentation.constants.Paths;

public class Chocolate extends IceCream {
	private static final String SPRITE_FRONT_PATH = "assets/characters/chocolate/front.png";
	private static final String SPRITE_BACK_PATH = "assets/characters/chocolate/back.png";
	private static final String SPRITE_LEFT_PATH = "assets/characters/chocolate/left.png";
	private static final String SPRITE_RIGHT_PATH = "assets/characters/chocolate/right.png";
	public static final String SPRITE_DEAD_PATH = "assets/characters/chocolate/dead.png";
	
	public Chocolate () {
		super(SPRITE_FRONT_PATH, SPRITE_BACK_PATH, SPRITE_LEFT_PATH, SPRITE_RIGHT_PATH, TileId.DEAD_CHOCOLATE);
	}
}
