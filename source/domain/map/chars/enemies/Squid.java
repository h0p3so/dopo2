package domain.map.chars.enemies;

import domain.Control;
import domain.GameMode;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.MotionEndsUp;
import domain.map.TileFactory;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.tiles.Tile;
import domain.map.tiles.TileId;
import exceptions.SharedException;

public class Squid extends Enemy {
	public Squid () {
		super(
			"assets/characters/squid/front.png",
			"assets/characters/squid/back.png",
			"assets/characters/squid/left.png",
			"assets/characters/squid/right.png"
		);
	}

	private Character decideWhoToFollow (final LevelContextualizer lc) {
        Character ch1 = Control.ch1;
        
        if (ch1 != null && ch1.doesItMelt() && !ch1.isDead()) {
            return ch1;
        }
        
        Character ch2 = Control.ch2;
        if (ch2 != null && ch2.doesItMelt() && !ch2.isDead()) {
            return ch2;
        }
        return null;
    }

    private MotionDirection determineBestDirection(final Position target) {
        int dx = target.getX() - this.pos.getX();
        int dy = target.getY() - this.pos.getY();

        MotionDirection primaryMove = null;

        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx != 0) {
                primaryMove = (dx > 0) ? MotionDirection.RIGHT : MotionDirection.LEFT;
            } else if (dy != 0) {
                primaryMove = (dy > 0) ? MotionDirection.DOWN : MotionDirection.UP;
            }
        } else {
            if (dy != 0) {
                primaryMove = (dy > 0) ? MotionDirection.DOWN : MotionDirection.UP;
            } else if (dx != 0) {
                primaryMove = (dx > 0) ? MotionDirection.RIGHT : MotionDirection.LEFT;
            }
        }
        
        return primaryMove; 
    }
    
    private MotionDirection determineSecondaryDirection(final Position target, final MotionDirection primary) {
        int dx = target.getX() - this.pos.getX();
        int dy = target.getY() - this.pos.getY();
        
        if (primary == MotionDirection.LEFT || primary == MotionDirection.RIGHT) {
            if (dy != 0) {
                return (dy > 0) ? MotionDirection.DOWN : MotionDirection.UP;
            }
        } 
        else if (primary == MotionDirection.UP || primary == MotionDirection.DOWN) {
            if (dx != 0) {
                return (dx > 0) ? MotionDirection.RIGHT : MotionDirection.LEFT;
            }
        }
        return null;
    }

    private MotionEndsUp tryMoveInDirection(MotionDirection direction, LevelContextualizer lc) {
        if (direction == null) return MotionEndsUp.GOT_BLOCKED_FUCKK;

        Position posSimulated = super.simulateMotion(direction);
        
        final Tile tile = lc.getTileAt(posSimulated.getY(), posSimulated.getX());
        final Character possiblech = lc.getChatAt(posSimulated.getY(), posSimulated.getX());

        if (possiblech != null && possiblech.doesItMelt()) {
            this.wishedPos = posSimulated;
            return MotionEndsUp.PLAYER_DIYING;
        }
        
        if (tile.isWalkable()) {
            lc.updatePositionOfChar(posSimulated.getY(), posSimulated.getX(), this);
            this.pos = posSimulated;
            return MotionEndsUp.JUST_MOVING;
        }
        
        return MotionEndsUp.GOT_BLOCKED_FUCKK;
    }

    /**
     * Attempts to break an Ice tile at the simulated position and then move onto the resulting tile.
     * @param direction The direction to move.
     * @param lc The level context.
     * @return MotionEndsUp result, or GOT_BLOCKED_FUCKK if not an Ice tile or a character is there.
     */
    private MotionEndsUp tryBreakTileAndMove(MotionDirection direction, LevelContextualizer lc) {
        if (direction == null) return MotionEndsUp.GOT_BLOCKED_FUCKK;

        Position posSimulated = super.simulateMotion(direction);
        
        final Character possiblech = lc.getChatAt(posSimulated.getY(), posSimulated.getX());
        if (possiblech != null && possiblech.doesItMelt()) {
            this.wishedPos = posSimulated;
            return MotionEndsUp.PLAYER_DIYING;
        }

        final Tile tile = lc.getTileAt(posSimulated.getY(), posSimulated.getX());
        
        if (tile.isBreakable()) { 
        	try {
				lc.addTileAt(posSimulated.getY(), posSimulated.getX(), TileFactory.get(TileId.EMPTY));
			} catch (SharedException e) { e.printStackTrace(); }
            lc.updatePositionOfChar(posSimulated.getY(), posSimulated.getX(), this);
            this.pos = posSimulated;
            return MotionEndsUp.JUST_MOVING;

        }
        
        return MotionEndsUp.GOT_BLOCKED_FUCKK;
    }

	@Override
	public MotionEndsUp autonomousMode (MotionDirection towards, LevelContextualizer lc, final GameMode mode) {
        Character follow = this.decideWhoToFollow(lc);
        if (follow == null) {
            return MotionEndsUp.GOT_BLOCKED_FUCKK;
        }
        
        Position reach = follow.getPosition();
        
        MotionDirection primaryDirection = this.determineBestDirection(reach);
        MotionEndsUp result = this.tryMoveInDirection(primaryDirection, lc);
        
        if (result != MotionEndsUp.GOT_BLOCKED_FUCKK) {
            return result;
        }
        
        result = this.tryBreakTileAndMove(primaryDirection, lc);
        if (result != MotionEndsUp.GOT_BLOCKED_FUCKK) {
            return result;
        }

        MotionDirection secondaryDirection = this.determineSecondaryDirection(reach, primaryDirection);
        if (secondaryDirection != null) {
            result = this.tryMoveInDirection(secondaryDirection, lc);
            
            if (result != MotionEndsUp.GOT_BLOCKED_FUCKK) {
                return result;
            }

            result = this.tryBreakTileAndMove(secondaryDirection, lc);
            if (result != MotionEndsUp.GOT_BLOCKED_FUCKK) {
                return result;
            }
        }

        this.wishedPos = this.pos; 
        return MotionEndsUp.GOT_BLOCKED_FUCKK;
	}

	@Override
	public MotionEndsUp playableMove (MotionDirection towards, LevelContextualizer lc) {
		return this.sharedPlayableMotion(towards, lc);
	}

	@Override
	public boolean killsIceCream () {
		return true;
	}

	@Override
	public boolean doesItMelt () {
		return false;
	}
}