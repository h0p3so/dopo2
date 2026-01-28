package domain.map.chars.cream;

import domain.GameMode;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.MotionEndsUp;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.tiles.Tile;
import domain.map.tiles.TileId;

public abstract class IceCream extends Character {
	private char deadId;
	
	public IceCream (final String frontPath, final String backPath, final String leftPath, final String rightPath, char did) {
		super(frontPath, backPath, leftPath, rightPath);
		this.deadId = did;
	}
	
	@Override
	public MotionEndsUp autonomousMode(final MotionDirection towards, final LevelContextualizer lc, final GameMode mode) {
	    if (mode != GameMode.SIMULATION_HUNGRY) {
	        return MotionEndsUp.JUST_MOVING;
	    }

	    Position fruitPos = lc.getNearestFruitPosition(this.pos);

	    if (fruitPos == null) {
	        return MotionEndsUp.JUST_MOVING; 
	    }

	    int dx = fruitPos.getX() - this.pos.getX();
	    int dy = fruitPos.getY() - this.pos.getY();

	    MotionDirection primaryDir;
	    MotionDirection secondaryDir;

	    if (Math.abs(dx) > Math.abs(dy)) {
	        primaryDir = dx > 0 ? MotionDirection.RIGHT : MotionDirection.LEFT;
	        secondaryDir = dy > 0 ? MotionDirection.DOWN : (dy < 0 ? MotionDirection.UP : null);
	    } else {
	        primaryDir = dy > 0 ? MotionDirection.DOWN : (dy < 0 ? MotionDirection.UP : null);
	        secondaryDir = dx > 0 ? MotionDirection.RIGHT : (dx < 0 ? MotionDirection.LEFT : null);
	    }

	    
	    if (primaryDir != null) {
	        Position primaryTargetPos = super.simulateMotion(primaryDir);
	        Tile primaryTargetTile = lc.getTileAt(primaryTargetPos.getY(), primaryTargetPos.getX());

	        if (primaryTargetTile.getId() == TileId.BLOCK_ICE) { // Assuming 'I' is the ID for Ice Block
	        	lc.createRemoveIceWall(this);
	        } else {
	            return this.playableMove(primaryDir, lc);
	        }
	    }
	    
	    if (secondaryDir != null) {
	        Position secondaryTargetPos = super.simulateMotion(secondaryDir);
	        Tile secondaryTargetTile = lc.getTileAt(secondaryTargetPos.getY(), secondaryTargetPos.getX());

	        if (secondaryTargetTile.getId() != TileId.BLOCK_ICE) {
	            return this.playableMove(secondaryDir, lc);
	        }
	    }

	    return MotionEndsUp.GOT_BLOCKED_FUCKK;
	}

	/**
	 * Helper method to determine the single best direction (Horizontal or Vertical) 
	 * to move to get closer to the target position. 
	 * * NOTE: This method is now redundant for the autonomousMode logic above, 
	 * but kept here in case other parts of the system rely on it.
	 */
	private MotionDirection getDirectionTowards(final Position targetPos) {
	    int dx = targetPos.getX() - this.pos.getX();
	    int dy = targetPos.getY() - this.pos.getY();

	    if (Math.abs(dx) > Math.abs(dy)) {
	        if (dx > 0) {
	            return MotionDirection.RIGHT;
	        } else if (dx < 0) {
	            return MotionDirection.LEFT;
	        }
	    } 
	    
	    if (dy > 0) {
	        return MotionDirection.DOWN;
	    } else if (dy < 0) {
	        return MotionDirection.UP;
	    }
	    
	    return null;
	}

	@Override public MotionEndsUp playableMove (final MotionDirection towards, final LevelContextualizer lc) {
		if (this.dead) {
			return MotionEndsUp.GOT_BLOCKED_FUCKK;
		}

		final Position finalxy = super.simulateMotion(towards);	
		final Tile tile = lc.getTileAt(finalxy.getY(), finalxy.getX());

		final Character possiblech = lc.getChatAt(finalxy.getY(), finalxy.getX());
		if (possiblech != null && possiblech.killsIceCream()) {
			return MotionEndsUp.PLAYER_DIYING;
		}

		if (tile.isWalkable()) {
			lc.updatePositionOfChar(finalxy.getY(), finalxy.getX(), this);
			this.pos = finalxy;
			
			if (tile.isDeadly()) {
				return MotionEndsUp.PLAYER_DIYING;
			}
			
			if (tile.isCollectable()) {
				return MotionEndsUp.COLLECTING_FRUIT;
			}
			return MotionEndsUp.JUST_MOVING;
		}

		return MotionEndsUp.GOT_BLOCKED_FUCKK;
	}

	@Override
	public boolean killsIceCream() {
		return false;
	}

	@Override public boolean doesItMelt () {
		return true;
	}	
	
	public char getDeadId () {
		return this.deadId;
	}
}