package domain.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.map.chars.CharType;
import domain.map.chars.Position;
import domain.map.chars.enemies.EnemySpawn;
import domain.map.fruits.FruitSpawn;
import exceptions.BLogger;
import exceptions.ProgrammerException;
import exceptions.SharedException;

class LevelParser {
    private static final int MAP_HEIGHT = 18;
    
    private String[] mapLines;
    private Position playerOneSpawn;
    private Position playerTwoSpawn;
    private List<EnemySpawn> enemySpawns;
    private Map<Character, List<FruitSpawn>> fruitRoundsMap;
    private List<List<FruitSpawn>> fruitRounds; 

    public LevelParser(final String filePath) {
        this.mapLines = new String[MAP_HEIGHT];
        this.enemySpawns = new ArrayList<>();
        this.fruitRoundsMap = new HashMap<>();
        this.fruitRounds = new ArrayList<>();

        try {
            this.parseFile(filePath);
        } catch (IOException e) {
        	BLogger.logError(BLogger.SEVERE, e);
        }
    }

    private void parseFile(final String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null && lineNumber < MAP_HEIGHT) {
                this.mapLines[lineNumber] = line;
                lineNumber++;
            }

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                this.parseEntityLine(line.trim());
            }
        }
        
        if (this.playerOneSpawn == null) {
        	BLogger.logError(BLogger.SEVERE, new ProgrammerException(ProgrammerException.unspecifiedP1Pos(filePath)));
        }
        this.finalizeFruitRounds();
    }

    private void parseEntityLine(final String line) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
        	BLogger.logError(BLogger.SEVERE, new ProgrammerException(ProgrammerException.malformedMap(line)));
        }

        char id = parts[0].charAt(0);
        
        try {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            Position pos = new Position(x, y);
            
            switch (id) {
                case '1':
                    this.playerOneSpawn = pos;
                    break;
                case '2':
                    this.playerTwoSpawn = pos;
                    break;
                case 'T':
                    this.enemySpawns.add(new EnemySpawn(pos, CharType.TROLL));
                    break;
                case 'M':
                	this.enemySpawns.add(new EnemySpawn(pos, CharType.MACETA));
                	break;
                case 'S':
                	this.enemySpawns.add(new EnemySpawn(pos, CharType.SQUID));
                	break;
                case 'N':
                	this.enemySpawns.add(new EnemySpawn(pos, CharType.NARWHAL));
                	break;
                case 'B':
                case 'G':
                case 'P':
                case 'C':
                case '&':
                    FruitSpawn fruitSpawn = new FruitSpawn(pos, id);
                    this.fruitRoundsMap
                        .computeIfAbsent(id, k -> new ArrayList<>())
                        .add(fruitSpawn);
                    break;
                default:
                	BLogger.logError(BLogger.SEVERE, new ProgrammerException(ProgrammerException.malformedMap(line)));
            }
        } catch (NumberFormatException e) {
        	BLogger.logError(BLogger.SEVERE, new ProgrammerException(ProgrammerException.malformedMap(line)));
        }
    }
    
    private void finalizeFruitRounds() {
        this.fruitRounds.addAll(this.fruitRoundsMap.values());
    }

    public String[] getMapLines() {
        return this.mapLines;
    }

    public Position getPlayerOneSpawn() {
        return this.playerOneSpawn;
    }

    public Position getPlayerTwoSpawn() {
        return this.playerTwoSpawn;
    }

    public List<EnemySpawn> getEnemySpawns() {
        return this.enemySpawns;
    }

    public List<List<FruitSpawn>> getFruitRounds() {
        return this.fruitRounds;
    }
}

public class BluePrint {
	public static final int NUMBER_OF_ROWS = 18;
	public static final int NUMBER_OF_COLS = 18;
	
	private final String [] map;
	private final Position player1position;
	private final Position player2position;
	
	private List<EnemySpawn> enemies;
	private int secondsLevel;
	
	private List<List<FruitSpawn>> fruitRoundSpawns;
	
	private BluePrint (final String [] map, final Position p1p, final Position p2p,
			final List<EnemySpawn> enemies, final int sl, final List<List<FruitSpawn>> frounds) {
		this.map = map;
		this.player1position = p1p;
		this.enemies = enemies;
		this.secondsLevel = sl;
		this.fruitRoundSpawns = frounds;
		this.player2position = p2p;
	}
	
	public String [] getMap () {
		return this.map;
	}
	
	public char getLocatedAt (final int row, final int col) {
		return this.map[row].charAt(col);
	}

	public Position getPlayer1Position () {
		return this.player1position;
	}

	public Position getPlayer2Position () {
		return this.player2position;
	}
		
	public static BluePrint getMap0 () throws SharedException {	
		LevelParser parser = new LevelParser("levels/0.txt");
		return new BluePrint(
			parser.getMapLines(),
			parser.getPlayerOneSpawn(),
			parser.getPlayerTwoSpawn(),
			parser.getEnemySpawns(),
			60 * 3,
			parser.getFruitRounds()
		);
	}	

	public static BluePrint getMap1 () throws SharedException {	
		LevelParser parser = new LevelParser("levels/1.txt");
		return new BluePrint(
			parser.getMapLines(),
			parser.getPlayerOneSpawn(),
			parser.getPlayerTwoSpawn(),
			parser.getEnemySpawns(),
			60 * 3,
			parser.getFruitRounds()
		);
	}	

	public static BluePrint getMap2 () throws SharedException {	
		LevelParser parser = new LevelParser("levels/2.txt");
		return new BluePrint(
			parser.getMapLines(),
			parser.getPlayerOneSpawn(),
			parser.getPlayerTwoSpawn(),
			parser.getEnemySpawns(),
			60 * 3,
			parser.getFruitRounds()
		);
	}	

	public static BluePrint getMap3 () throws SharedException {	
		LevelParser parser = new LevelParser("levels/3.txt");
		return new BluePrint(
			parser.getMapLines(),
			parser.getPlayerOneSpawn(),
			parser.getPlayerTwoSpawn(),
			parser.getEnemySpawns(),
			60 * 3,
			parser.getFruitRounds()
		);
	}	
	
	public List<EnemySpawn> getEnemies () {
		return this.enemies;
	}
	public int getSeconds () {
		return this.secondsLevel;
	}
	public List<FruitSpawn> getFruitRound (int round) {
		return this.fruitRoundSpawns.get(round);
	}
	public int getNumberOfRounds () {
		return this.fruitRoundSpawns.size();
	}
}
