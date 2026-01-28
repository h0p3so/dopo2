package domain.map.fruits;

import domain.LevelContextualizer;
import domain.map.chars.Position;
import domain.map.tiles.TileId;
import java.util.Random;

public class Cherry extends Fruit {
    private static final int TELEPORT_COOLDOWN = 1;
    private int teleportTimer = 0;

	public Cherry() {
		super(
			"assets/fruits/cherry/normal.png",
			"assets/fruits/cherry/frozen.png",
			150,
			TileId.FRUIT_CHERRY
		);
	}
    
	@Override
	public void interact(LevelContextualizer lc) {
		Position newPos = this.findEmptyPosition(lc);
        if (newPos != null) {
        	lc.updatePositionOfFruit(newPos.getY(), newPos.getX(), this); 
            this.pos = newPos;
        }
        this.teleportTimer = 0;
	}
    
    private Position findEmptyPosition(LevelContextualizer lc) {
        final int MAX_ATTEMPTS = 100;
        Random rand = new Random();
        
        final int maxRow = 18;
        final int maxCol = 18;

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            int randomRow = rand.nextInt(maxRow);
            int randomCol = rand.nextInt(maxCol);
            
            if (lc.getChatAt(randomRow, randomCol) == null) {
                if (lc.getTileAt(randomRow, randomCol).isWalkable()) {
                    return new Position(randomCol, randomRow);
                }
            }
        } 
        return null;
    }
    
    @Override public boolean moveable () {
    	return true;
    }
}