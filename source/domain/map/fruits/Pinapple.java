package domain.map.fruits;

import domain.Control;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.map.chars.Position;
import domain.map.tiles.Tile;
import domain.map.tiles.TileId;

public class Pinapple extends Fruit {
	final private MotionDirection [] cycle = {
		MotionDirection.UP,
		MotionDirection.RIGHT,
		MotionDirection.DOWN,
		MotionDirection.LEFT,
	};
	
	private int cycleEpoch = 0;
	private int showed = 0;
	private final int interactEach = (int) 60;
		
	public Pinapple () {
		super(
			"assets/fruits/pinapple/normal.png",
			 "assets/fruits/pinapple/frozen.png",
			 200,
			 TileId.FRUIT_PINAPPLE
		);
	}
	
	@Override public void interact (final LevelContextualizer lc) {
		System.out.println(cycleEpoch);
		if (showed < 8 * 10) {
			showed++;
			return;
		}
		
		showed=0;
		
		for (int i = cycleEpoch % 4; i < cycle.length; i++) {
			Position pos = super.simulateMotion(cycle[i]);
			final Tile tile = lc.getTileAt(pos.getY(), pos.getX());

			if (tile.isWalkable()) {
				lc.updatePositionOfFruit(pos.getY(), pos.getX(), this);
				this.pos = pos;
				return;
			}
			this.cycleEpoch++;
		}
	}

    @Override public boolean moveable () {
    	return true;
    }

	@Override public boolean playerDependent () {
		return true;
	}
}
