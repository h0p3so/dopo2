package domain.map.chars.enemies;

import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.MotionEndsUp;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.tiles.Tile;

public abstract class Enemy extends Character {
	protected Enemy(String frontSpritePath, String backSpritePath, String leftSpritePath, String rightSpritePath) {
		super(frontSpritePath, backSpritePath, leftSpritePath, rightSpritePath);
	}	
	
    protected MotionEndsUp sharedPlayableMotion (MotionDirection towards, LevelContextualizer lc) {
		if (this.dead) {
			return MotionEndsUp.GOT_BLOCKED_FUCKK;
		}

		final Position finalxy = super.simulateMotion(towards);	
		final Tile tile = lc.getTileAt(finalxy.getY(), finalxy.getX());

		final Character possiblech = lc.getChatAt(finalxy.getY(), finalxy.getX());
		
		if (possiblech != null && possiblech.doesItMelt()) {
			return MotionEndsUp.PLAYER_KILLING_PLAYER;
		}
		
		if (tile.isWalkable()) {
			lc.updatePositionOfChar(finalxy.getY(), finalxy.getX(), this);
			this.pos = finalxy;
			return MotionEndsUp.JUST_MOVING;
		}

		return MotionEndsUp.GOT_BLOCKED_FUCKK;
    }
}
