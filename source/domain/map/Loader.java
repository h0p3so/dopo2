package domain.map;

import java.io.Serializable;

import domain.LevelContextualizer;
import domain.map.tiles.Tile;
import exceptions.BLogger;
import exceptions.ProgrammerException;
import exceptions.SharedException;
import presentation.Drawable;

public class Loader implements Serializable {
	private static final long serialVersionUID = 1245489935963556057L;
	private BluePrint bp;
	
	public BluePrint setBluePrintToBeUsed (final int level) throws SharedException {
		switch (level) {
			case 0: { this.bp = BluePrint.getMap0(); break; }
			case 1: { this.bp = BluePrint.getMap1(); break; }
			case 2: { this.bp = BluePrint.getMap2(); break; }
			case 3: { this.bp = BluePrint.getMap3(); break; }
			default: {
				throw new ProgrammerException(ProgrammerException.unimplementedLevel(level));
			}
		}
		
		return this.bp;
	}

	public Drawable [][] loadLevelGivenByUser (final String filePath) {
		return null;
	}
	
	public LevelContextualizer loadLevel () {
		final LevelContextualizer lc = new LevelContextualizer();
		
		for (int row = 0; row < BluePrint.NUMBER_OF_ROWS; row++) {
			for (int col = 0; col < BluePrint.NUMBER_OF_COLS; col++) {
				final char id = this.bp.getLocatedAt(row, col);
				try {
					Tile tile = TileFactory.get(id);
					lc.addTileAt(row, col, TileFactory.get(id));
				} catch (final SharedException e) {
					BLogger.logError(BLogger.SEVERE, e);
				}
			}
		}
		return lc;
	}
	
	public BluePrint getBluePrint () {
		return this.bp;
	}
}
