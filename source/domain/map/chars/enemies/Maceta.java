package domain.map.chars.enemies;

import domain.Control;
import domain.GameMode;
import domain.LevelContextualizer;
import domain.MotionDirection;
import domain.MotionEndsUp;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.tiles.Tile;
import presentation.constants.Paths;

public class Maceta extends Enemy {
    public Maceta() {
        super(
        	"assets/characters/maceta/front.png",
        	"assets/characters/maceta/back.png",
        	"assets/characters/maceta/left.png",
        	"assets/characters/maceta/right.png"
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
        
        MotionDirection secondaryDirection = this.determineSecondaryDirection(reach, primaryDirection);
        if (secondaryDirection != null) {
            result = this.tryMoveInDirection(secondaryDirection, lc);
            if (result != MotionEndsUp.GOT_BLOCKED_FUCKK) {
                return result;
            }
        }
        
        this.wishedPos = this.pos; 
        return MotionEndsUp.GOT_BLOCKED_FUCKK;
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
    
    @Override
    public MotionEndsUp playableMove (MotionDirection towards, LevelContextualizer lc) {
        return this.playableMove(towards, lc);
    }

    @Override
    public boolean killsIceCream() {
        return true;
    }

    @Override
    public boolean doesItMelt() {
        return false;
    }
}