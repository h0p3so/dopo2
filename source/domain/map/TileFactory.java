package domain.map;

import domain.map.chars.cream.Chocolate;
import domain.map.chars.cream.DeadOne;
import domain.map.chars.cream.Strawberry;
import domain.map.chars.cream.Vanilla;
import domain.map.tiles.BasicDelimiter;
import domain.map.tiles.BigSnowHeap;
import domain.map.tiles.Bonfire;
import domain.map.tiles.Fire;
import domain.map.tiles.Ice;
import domain.map.tiles.SmallSnowHeap;
import domain.map.tiles.Snow;
import domain.map.tiles.Tile;
import domain.map.tiles.TileId;
import exceptions.SharedException;

public class TileFactory {
	public static Tile get (final char id) throws SharedException {
		switch (id) {
			case TileId.DELIMITER_BASIC: return new BasicDelimiter();
			case TileId.BLOCK_ICE: return new Ice();
			case TileId.FLOOR_BIG_SNOW_HEAP: return new BigSnowHeap();
			case TileId.FLOOR_SMALL_SNOW_HEAP: return new SmallSnowHeap();
			case TileId.EMPTY: return new Snow();
			case TileId.FLOOR_BONFIRE: return new Bonfire();
			case TileId.FLOOR_FIRE: return new Fire();

			case TileId.DEAD_CHOCOLATE: return new DeadOne(Chocolate.SPRITE_DEAD_PATH, id);
			case TileId.DEAD_VANILLA: return new DeadOne(Vanilla.SPRITE_DEAD_PATH, id);
			case TileId.DEAD_STRAWBERRY: return new DeadOne(Strawberry.SPRITE_DEAD_PATH, id);
		}
		
		throw new SharedException(SharedException.invalidTileId(id));
	}
}
