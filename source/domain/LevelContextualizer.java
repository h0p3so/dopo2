package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domain.map.BluePrint;
import domain.map.TileFactory;
import domain.map.chars.Character;
import domain.map.chars.Position;
import domain.map.fruits.Fruit;
import domain.map.tiles.Ice;
import domain.map.tiles.Snow;
import domain.map.tiles.Tile;
import domain.map.tiles.TileId;
import exceptions.SharedException;

public class LevelContextualizer {
    private Tile [][] tiles;
    private domain.map.chars.Character [][] chars;
    
    private Map<Position, List<Fruit>> fruitsLocs;
    private Map<java.lang.Character, List<Fruit>> fruitsTypes;
    
    public LevelContextualizer () {
        this.tiles = new Tile [BluePrint.NUMBER_OF_ROWS][BluePrint.NUMBER_OF_COLS];
        this.chars = new domain.map.chars.Character [BluePrint.NUMBER_OF_ROWS][BluePrint.NUMBER_OF_COLS];    
        
        this.fruitsLocs = new HashMap<>();
        this.fruitsTypes = new HashMap<>();
    }
    
    
    /**
     * Finds the Position of the nearest available (unfrozen) Fruit to the given character position.
     * Uses Manhattan distance for calculation: |x1 - x2| + |y1 - y2|.
     * * @param currentPos The character's current Position.
     * @return The Position of the nearest fruit, or null if no unfrozen fruits exist.
     */
    public Position getNearestFruitPosition(final Position currentPos) {
        int minDistance = Integer.MAX_VALUE;
        Position nearestFruitPos = null;
        for (Entry<Position, List<Fruit>> entry : this.fruitsLocs.entrySet()) {
            
            Position fruitMapPos = entry.getKey();
            List<Fruit> fruitsAtPos = entry.getValue();
            
            boolean hasAvailableFruit = false;
            for (Fruit f : fruitsAtPos) {
                if (!f.isFrozen()) {
                    hasAvailableFruit = true;
                    break;
                }
            }
            
            if (hasAvailableFruit) {
                int dx = Math.abs(fruitMapPos.getX() - currentPos.getX());
                int dy = Math.abs(fruitMapPos.getY() - currentPos.getY());
                int distance = dx + dy;
                
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestFruitPos = fruitMapPos;
                }
            }
        }
        
        return nearestFruitPos;
    }

    public void rmFruit (final Position pos) { 
        try {
            this.addTileAt(pos.getY(), pos.getX(), TileFactory.get(TileId.EMPTY));
        } catch (Exception e) {}
        this.fruitsLocs.remove(pos);
    }
    
    public void addTileAt (final int row, final int col, final Tile tile) {
        if (tile.isCollectable()) {
            Position pos = new Position(col, row);
            ((Fruit) tile).setPosition(row, col);
            
            if (!this.fruitsLocs.containsKey(pos)) {
                this.fruitsLocs.put(pos, new ArrayList<>());
                
                if (!this.fruitsTypes.containsKey(tile.getId())) {
                    this.fruitsTypes.put(tile.getId(), new ArrayList<>());
                }
            }
            this.fruitsLocs.get(pos).add((Fruit) tile);
            
            this.fruitsTypes.get(tile.getId()).add((Fruit) tile);
        }

        this.tiles[row][col] = tile;
    }
    
    public Tile[][] getTiles () {
        return this.tiles;
    }
    
    public List<Fruit> getFruitsOfType (final char type) {
        return this.fruitsTypes.get(type);
    }
    
    public void setCharAt (final int row, final int col, final domain.map.chars.Character ch) {
        this.chars[row][col] = ch;
    }
    
    public Tile getTileAt (final int row, final int col) {
        return this.tiles[row][col];
    }

    public domain.map.chars.Character getChatAt (final int row, final int col) {
        return this.chars[row][col];
    }
    
    public void updatePositionOfChar (final int row, final int col, final Character ch) {
        final int oldrow = ch.getPosition().getY();
        final int oldcol = ch.getPosition().getX();
        
        this.chars[oldrow][oldcol] = null;
        this.chars[row][col] = ch;
    }

    public void updatePositionOfFruit (final int row, final int col, final Fruit f) {
        final int oldrow = f.getPosition().getY();
        final int oldcol = f.getPosition().getX();
        
        this.fruitsLocs.remove(new Position(oldcol, oldrow));
        
        Position newPos = new Position(col, row);
        this.fruitsLocs.put(newPos, new ArrayList<>(List.of(f)));
        
        try {
            this.tiles[oldrow][oldcol] = TileFactory.get(TileId.EMPTY);
        } catch (SharedException e) { e.printStackTrace(); }
        this.tiles[row][col] = f;
    }
    
    private void advanceBy (final Position p, final GoingDirection dir) {
        switch (dir) {
            case FRONT: { p.setY(p.getY() + 1); break; }
            case BACK: { p.setY(p.getY() - 1); break; }
            case LEFT: { p.setX(p.getX() - 1); break; }
            case RIGHT: { p.setX(p.getX() + 1); break; }
        }
    }
    
    public void createRemoveIceWall (final Character ch) {
        final GoingDirection direction = ch.getGoingDirection();
        Position p = new Position(
            ch.getPosition().getX(),
            ch.getPosition().getY()
        );
        
        this.advanceBy(p, direction);
        final char placing = this.getTileAt(p.getY(), p.getX()).getId();
        
        final Tile replace4;
        final boolean takeCoverableConditionIntoAccount;
        
        if (placing == TileId.BLOCK_ICE) {
            replace4 = new Snow();
            takeCoverableConditionIntoAccount = false;
        }
        else {
            replace4 = new Ice();
            takeCoverableConditionIntoAccount = true;
        }
        
        Tile nextile, prev;
        Character anybody;
        
        do {
            final int row = p.getY();
            final int col = p.getX();      
            
            prev = this.getTileAt(row, col);
            if (!prev.isCoverable() && !prev.isBreakable()) {
                return;
            }

            this.addTileAt(row, col, replace4);
            this.advanceBy(p, direction);      
            
            if (prev.isCoverable() && prev.isCollectable()) {
                prev.toggleFrozen();
                prev.swapBetweenFrontAndAlternative();
                this.addTileAt(row, col, prev);
            }
            if (prev.isCoverable() && prev.isDeadly()) {
                prev.swapBetweenFrontAndAlternative();
                this.addTileAt(row, col, prev);
            }

            nextile = this.getTileAt(p.getY(), p.getX());
            anybody = this.getChatAt(p.getY(), p.getX());
            
            if (anybody != null) {
                break;
            }      
        } while ((nextile.getId() == placing) || (takeCoverableConditionIntoAccount && nextile.isCoverable()) || nextile.isFrozen());
    }
}