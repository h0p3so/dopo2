package domain.map.chars.enemies;

import domain.GameMode;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.MotionEndsUp;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.tiles.Tile;
import exceptions.SharedException;
import presentation.constants.Paths;

public class Troll extends Enemy {
	private static final String SPRITE_FRONT_PATH = "assets/characters/troll/front.png";
	private static final String SPRITE_BACK_PATH = "assets/characters/troll/back.png";
	private static final String SPRITE_LEFT_PATH = "assets/characters/troll/left.png";
	private static final String SPRITE_RIGHT_PATH = "assets/characters/troll/right.png";
	
	final private MotionDirection [] cycle = {
		MotionDirection.UP,
		MotionDirection.RIGHT,
		MotionDirection.DOWN,
		MotionDirection.LEFT,
	};
	
	private int cycleEpoch = 0;

	public Troll () {
		super(SPRITE_FRONT_PATH, SPRITE_BACK_PATH, SPRITE_LEFT_PATH, SPRITE_RIGHT_PATH);
	}

	@Override
	public MotionEndsUp autonomousMode (MotionDirection towards, LevelContextualizer lc, final GameMode mode) {
		for (int i = cycleEpoch % 4; i < cycle.length; i++) {
			Position pos = super.simulateMotion(cycle[i]);
			final Tile tile = lc.getTileAt(pos.getY(), pos.getX());

			final Character possiblech = lc.getChatAt(pos.getY(), pos.getX());
			if (possiblech != null && possiblech.doesItMelt()) {
				this.wishedPos = pos;
				return MotionEndsUp.PLAYER_DIYING;
			}
			
			if (tile.isWalkable()) {
				lc.updatePositionOfChar(pos.getY(), pos.getX(), this);
				this.pos = pos;
				return MotionEndsUp.JUST_MOVING;
			}
			
			this.cycleEpoch++;
		}	
		return MotionEndsUp.GOT_BLOCKED_FUCKK;
	}

	@Override public MotionEndsUp playableMove (final MotionDirection towards, final LevelContextualizer lc) {
		return this.sharedPlayableMotion(towards, lc);
	}

	@Override
	public boolean killsIceCream() {
		return true;
	}

	@Override public boolean doesItMelt () {
		return false;
	}
}