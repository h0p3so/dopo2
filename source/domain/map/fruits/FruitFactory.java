package domain.map.fruits;

import java.awt.Image;

import domain.map.tiles.TileId;
import exceptions.BLogger;
import exceptions.SharedException;

public class FruitFactory {
	public static Fruit get (final char id) {
		switch (id) {
			case TileId.FRUIT_BANANA: return new Banana();
			case TileId.FRUIT_GRAPES: return new Grapes();
			case TileId.FRUIT_PINAPPLE: return new Pinapple();
			case TileId.FRUIT_CHERRY: return new Cherry();
			case TileId.FRUIT_CACTUS: return new Cactus();
		}

		BLogger.logError(BLogger.SEVERE, new SharedException(SharedException.invalidTileId(id)));
		return null;
	}
	
	public static Image getFruitImage (char id) {
		switch (id) {
			case TileId.FRUIT_BANANA: return new Banana().getFrontSideSprite();
			case TileId.FRUIT_GRAPES: return new Grapes().getFrontSideSprite();
			case TileId.FRUIT_PINAPPLE: return new Pinapple().getFrontSideSprite();
			case TileId.FRUIT_CHERRY: return new Cherry().getFrontSideSprite();
			case TileId.FRUIT_CACTUS: return new Cactus().getFrontSideSprite();

		}
		BLogger.logError(BLogger.SEVERE, new SharedException(SharedException.invalidTileId(id)));
		return null;
	}
}
