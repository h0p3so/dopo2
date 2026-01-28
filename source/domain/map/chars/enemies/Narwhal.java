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

public class Narwhal extends Enemy {
    private MotionDirection currentPatternDirection;

    public Narwhal() {
        super(
            "assets/characters/narwhal/front.png",
            "assets/characters/narwhal/back.png",
            "assets/characters/narwhal/left.png",
            "assets/characters/narwhal/right.png"
        );
        this.currentPatternDirection = MotionDirection.RIGHT;
    }

    private Character decideWhoToFollow(final LevelContextualizer lc) {
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

    private MotionDirection getAlignmentDirection(final Position targetPos) {
        int dx = targetPos.getX() - this.pos.getX();
        int dy = targetPos.getY() - this.pos.getY();

        if (dy == 0 && dx != 0) {
            return (dx > 0) ? MotionDirection.RIGHT : MotionDirection.LEFT;
        }
        
        if (dx == 0 && dy != 0) {
            return (dy > 0) ? MotionDirection.DOWN : MotionDirection.UP;
        }
        
        return null;
    }

    private void rotatePatternDirection() {
        switch (this.currentPatternDirection) {
            case RIGHT: this.currentPatternDirection = MotionDirection.DOWN; break;
            case DOWN:  this.currentPatternDirection = MotionDirection.LEFT; break;
            case LEFT:  this.currentPatternDirection = MotionDirection.UP; break;
            case UP:    this.currentPatternDirection = MotionDirection.RIGHT; break;
        }
    }

    private MotionEndsUp tryMove(MotionDirection direction, LevelContextualizer lc, boolean canBreakIce) {
        if (direction == null) return MotionEndsUp.GOT_BLOCKED_FUCKK;

        Position posSimulated = super.simulateMotion(direction);
        
        final Tile tile = lc.getTileAt(posSimulated.getY(), posSimulated.getX());
        final Character possiblech = lc.getChatAt(posSimulated.getY(), posSimulated.getX());

        if (possiblech != null && possiblech.doesItMelt()) {
            this.wishedPos = posSimulated;
            return MotionEndsUp.PLAYER_DIYING;
        }
        
        if (canBreakIce && (tile.isBreakable())) {
            try {
				lc.addTileAt(posSimulated.getY(), posSimulated.getX(), TileFactory.get(TileId.EMPTY));
			} catch (SharedException e) {
				e.printStackTrace();
			}
            
            lc.updatePositionOfChar(posSimulated.getY(), posSimulated.getX(), this);
            this.pos = posSimulated;
            return MotionEndsUp.JUST_MOVING;
        }

        if (tile.isWalkable()) {
            lc.updatePositionOfChar(posSimulated.getY(), posSimulated.getX(), this);
            this.pos = posSimulated;
            return MotionEndsUp.JUST_MOVING;
        }
        
        return MotionEndsUp.GOT_BLOCKED_FUCKK;
    }

    @Override
    public MotionEndsUp autonomousMode(MotionDirection towards, LevelContextualizer lc, final GameMode mode) {
        Character target = this.decideWhoToFollow(lc);
        MotionDirection chargeDirection = null;

        if (target != null) {
            chargeDirection = this.getAlignmentDirection(target.getPosition());
        }

        if (chargeDirection != null) {
            MotionEndsUp chargeResult = this.tryMove(chargeDirection, lc, true);
            if (chargeResult != MotionEndsUp.GOT_BLOCKED_FUCKK) {
                return chargeResult;
            }
        }

        int attempts = 0;
        final int maxAttempts = 4;

        while (attempts < maxAttempts) {
            MotionEndsUp patternResult = this.tryMove(this.currentPatternDirection, lc, false);

            if (patternResult != MotionEndsUp.GOT_BLOCKED_FUCKK) {
                return patternResult;
            }

            this.rotatePatternDirection();
            attempts++;
        }

        this.wishedPos = this.pos;
        return MotionEndsUp.GOT_BLOCKED_FUCKK;
    }

    @Override
    public MotionEndsUp playableMove(MotionDirection towards, LevelContextualizer lc) {
        return this.sharedPlayableMotion(towards, lc);
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