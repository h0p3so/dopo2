/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Acts as the communication bridge between the visual layer (views)
 * and the domain logic. Following the MVC pattern, this class exposes
 * high-level operations that the view can request without needing to
 * touch the underlying model or internal game mechanics.
 *
 * @author juad
 */
package domain;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import domain.map.BluePrint;
import domain.map.Loader;
import domain.map.TileFactory;
import domain.map.chars.CharType;
import domain.map.chars.Character;
import domain.map.chars.CharacterFactory;
import domain.map.chars.Position;
import domain.map.chars.cream.IceCream;
import domain.map.chars.enemies.EnemySpawn;
import domain.map.fruits.Fruit;
import domain.map.fruits.FruitFactory;
import domain.map.fruits.FruitSpawn;
import domain.map.tiles.Tile;
import domain.map.tiles.TileId;
import exceptions.BLogger;
import exceptions.SharedException;
import exceptions.UserException;
import presentation.BaDopoCreamGUI;
import presentation.ViewsId;
import presentation.render.LevelView;

public class Control {

	private LevelAvailableness levelAvailableness;
	private int level;
	private Loader loader;
	private BluePrint bp;
	
	public static Character ch1 = null;
	public static Character ch2 = null;
	private int fround;
	
	private ArrayList<Character> enemies;
	private int [] fruitsPerLevel;
	
	private LevelContextualizer contextzr;
	private int timeLeft;
	private int fruitsCollectedSoFarThisRound;
	
	private int alreadyDead;
	
	private GameMode mode;
	private boolean restarting = false;
	
	private CharType t1;
	private CharType t2;
	private char currentFruitId;

	public Control () {	
		this.levelAvailableness = new LevelAvailableness();
		this.loader = new Loader();
		this.contextzr = new LevelContextualizer();	
		this.enemies = new ArrayList<Character>();
		this.timeLeft = 0;
		this.fround = 0;
		this.alreadyDead = 0;
		this.fruitsCollectedSoFarThisRound = 0;
		this.level = 0;
	}

	/**
	 * Provides the view with the current list of which predefined
	 * levels are available to play.
	 *
	 * @return Boolean array where each index indicates whether a level is unlocked.
	 */
	public boolean[] pleaseProvideListOfAvailableLevels () {
		return this.levelAvailableness.getAvailableLevels();
	}
	
	/**
	 * Persists any changes made to the level availability configuration.
	 * Typically invoked when the application is closing.
	 */
	public void pleaseSaveChangesOnLevelAvailableness () {
		this.levelAvailableness.saveChanges();
	}

	public void activateNextLevelAsAvailable () {
		if (level + 1 >= LevelAvailableness.IMPLEMENTED_LEVELS) {
			return;
		}
		this.levelAvailableness.setLevelAsAvaialble(level + 1);
	}
	
	public void goNextLevel () {
		this.activateNextLevelAsAvailable();
		this.pleaseLoadPredefinedLevel(++this.level);
		BaDopoCreamGUI.getInstance(null).showThisView(ViewsId.LEVEL);
	}

	/**
	 * Requests the loading of a predefined level. This method also stores
	 * the level number so the GUI can display the correct title or metadata.
	 *
	 * Any issue encountered during level loading is captured and logged.
	 *
	 * @param level Index of the level selected by the user (starting at 0).
	 */
	public void pleaseLoadPredefinedLevel (final int level) {
		this.hardReset();

		try {
			this.bp = this.loader.setBluePrintToBeUsed(level);
		} catch (final SharedException e) {
			BLogger.logError(BLogger.SEVERE, e);
		}

		this.level = level;
		this.contextzr = this.loader.loadLevel();
		
		List<EnemySpawn> enemies = this.bp.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			final Position xy = enemies.get(i).getPosition();
			final CharType type = enemies.get(i).getType();
			
			final Character e = CharacterFactory.get(type);
			e.getPosition().setX(xy.getX());
			e.getPosition().setY(xy.getY());

			this.contextzr.setCharAt(xy.getY(), xy.getX(), e);
			this.enemies.add(e);
		}
		
		this.loadRoundOfFruits();
		this.timeLeft = this.bp.getSeconds();
	}
	
	private void hardReset () {
		this.loader = new Loader();
		this.contextzr = new LevelContextualizer();	
		this.enemies = new ArrayList<Character>();
		this.timeLeft = 0;
		this.fround = 0;
		this.alreadyDead = 0;
		this.fruitsCollectedSoFarThisRound = 0;
		
		
		if (this.restarting) {
			this.setCharTypeOne(this.t1);
			if (GameMode.isTwoPlayerMode(this.mode)) {
				this.setCharTypeTwo(this.t2);
			}
		}
	}
	
	private void loadRoundOfFruits () {
		for (final FruitSpawn fp: this.bp.getFruitRound(this.fround)) {
			final Position p = fp.getSpawnPos();
			this.currentFruitId = fp.getId();
			this.contextzr.addTileAt(p.getY(), p.getX(), FruitFactory.get(fp.getId()));
		}
	}

	public List<Fruit> getRoundFruits () {
		return this.contextzr.getFruitsOfType(this.currentFruitId);
	}	
	
	public Tile[][] getTiles () {
		return this.contextzr.getTiles();
	}
	
	/**
	 * Returns the index of the current level that was requested for loading.
	 *
	 * @return Level number selected by the user.
	 */
	public int getLevelNumber () {
		return this.level;
	}
	
	public BluePrint getBluePrint () {
		return this.bp;
	}
	
	public Tile getTileAt (final int row, final int col) {
		return this.contextzr.getTileAt(row, col);
	}
		
	public void setCharTypeOne (final CharType type) {
		this.ch1 = CharacterFactory.get(type);
		this.t1 = type;
	}

	public void setCharTypeTwo (final CharType type) {
		this.ch2 = CharacterFactory.get(type);
		this.t2 = type;
	}
	
	public Character getCharacter1 () {
		return this.ch1;
	}

	public Character getCharacter2 () {
		return this.ch2;
	}	
	
	public void setGameMode (final GameMode mode) {
		this.mode = mode;
	}
	
	public MotionEndsUp characterTryingToMove (final MotionDirection towards, final Character ch) {
		if (this.mode == GameMode.OPP_VS_ICE && ch == this.ch2) {
			return ch.playableMove(towards, this.contextzr);
		}
		if (!ch.doesItMelt()) {
			return ch.autonomousMode(towards, this.contextzr, this.mode);
		}
		return ch.playableMove(towards, this.contextzr);
	}
	
	public ArrayList<Character> getEnemies () {
		return this.enemies;
	}
	
	public void moveEnemies () {
		for (Character c: this.enemies) {
			if (c.autonomousMode(MotionDirection.AUTO, this.contextzr, this.mode) == MotionEndsUp.PLAYER_DIYING) {
				final Position p = c.getWishedPositionToMove();
				final IceCream ic = (IceCream) this.contextzr.getChatAt(p.getY(), p.getX());
				this.deadCharacter(p, ic);
			}
		}
	}
	
	public void generateAutonomousMoveForIceCream () {
		MotionEndsUp e = ch1.autonomousMode(MotionDirection.AUTO, contextzr, this.mode);
		if (e == MotionEndsUp.COLLECTING_FRUIT) {
			this.playerGotFood(ch1);
		}
	}
	
	public void deadCharacter (final Position p, final IceCream deadone) {
		deadone.setDead();
		try {
			this.contextzr.addTileAt(p.getY(), p.getX(), TileFactory.get(deadone.getDeadId()));
			this.contextzr.setCharAt(p.getY(), p.getX(), null);
		} catch (SharedException e) {}
		
		this.alreadyDead++;
		if (this.ch2 == null) {
			LevelView.getInstace(null).showLostScreen();
		}
		if (this.alreadyDead == 2 || this.mode == GameMode.OPP_VS_ICE) {
			LevelView.getInstace(null).showLostScreen();
		}
	}
	
	public void playerKillsPlayer () {
		LevelView.getInstace(null).showLostScreen();
	}
	
	public int getCh1Score () {
		return this.ch1.getScore();
	}

	public int getCh2Score () {
		return this.ch2.getScore();
	}
	
	public void oneSecondPassedBy () {
		this.timeLeft--;
		if (this.timeLeft == 0) {
			LevelView.getInstace(null).showLostScreen();
		}
	}
	
	public int getTimeLeft () {
		return this.timeLeft;
	}
	
	public void createDestroyWall (final Character ch) {
		this.contextzr.createRemoveIceWall(ch);
	}
	
	public void goForNextRoundOfFruits () {
		this.fround++;
		if (this.fround == this.bp.getNumberOfRounds()) {
			LevelView.getInstace(null).justWon();
			return;
		}
		this.loadRoundOfFruits();
		this.fruitsCollectedSoFarThisRound = 0;
	}

	public void playerGotFood (final Character who) {
		Position p = who.getPosition();
		Fruit f = (Fruit) this.contextzr.getTileAt(p.getY(), p.getX());
		
		try {
			this.contextzr.addTileAt(p.getY(), p.getX(), TileFactory.get(TileId.EMPTY));
		} catch (SharedException e) {}
		
		who.incScore(f.getPointsGiven());
		if (this.fruitsPerLevel[this.fround] == ++this.fruitsCollectedSoFarThisRound) {
			this.goForNextRoundOfFruits();
		}
	}
	
	public List<Image> loadFruitPreviewImages () {
		this.fruitsPerLevel = new int[this.bp.getNumberOfRounds()];
		List<Image> imgs = new ArrayList<Image>();

		for (int i = 0; i < this.bp.getNumberOfRounds(); i++) {
			this.fruitsPerLevel[i] = this.bp.getFruitRound(i).size();
			char id = this.bp.getFruitRound(i).get(0).getId();
			imgs.add(FruitFactory.getFruitImage(id));
		}
		return imgs;
	}
	
	public GameMode getMode () {
		return this.mode;
	}
	
	public void restartLevel () {
		int lvl = this.level;
		GameMode m = this.mode;
		this.restarting = true;
		this.hardReset();
		this.pleaseLoadPredefinedLevel(lvl);
		this.setGameMode(m);
		LevelView.getInstace(null);
		this.restarting = false;
	}	
	
	public String pickFile() throws UserException {
	    JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
	    fileChooser.setDialogTitle("Select a File");
	    int userSelection = fileChooser.showOpenDialog(null);

	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
	        return selectedFile.getAbsolutePath();
	    }
	    throw new UserException(UserException.UNEXISTING_FILE);
	}
	
	public void storeCurrentStateOfGame (final String filename) {
		try {
			FileOutputStream out = new FileOutputStream(filename);
			ObjectOutputStream sout = new ObjectOutputStream(out);
			sout.writeObject(new Saver(this));	
			sout.close();
			out.close();
		} catch (IOException e) { /* Already handled */ }
	}
	
	public void loadStateFromFile (final String filename) {
		Saver saver = null;
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			saver = (Saver) in.readObject();
			in.close();
			file.close();
		} catch (IOException | ClassNotFoundException e) {
			BLogger.logError(BLogger.SEVERE, e);
		}
		
		this.setGameMode(saver.getGameMode());
	    this.setCharTypeOne(saver.getCh1Type());
	    
	    if (GameMode.isTwoPlayerMode(this.mode)) {
	    	this.setCharTypeTwo(saver.getCh2Type());
	    }
	    
	    this.pleaseLoadPredefinedLevel(saver.getLevelNumber());
	    this.fround = saver.getRoundNumber();
	    this.timeLeft = saver.getTimeLeft();
	    
	    String[] savedMap = saver.getMap();
	    for (int i = 0; i < savedMap.length; i++) {
	        for (int j = 0; j < savedMap[i].length(); j++) {
	            char tileId = savedMap[i].charAt(j);
	            try {
	                this.contextzr.addTileAt(i, j, TileFactory.get(tileId)); 
	            } catch (SharedException e) {
	                BLogger.logError(BLogger.WARNING, e);
	            }
	        }
	    }
	    
	    if (ch1 != null) {
	        ch1.incScore(saver.getCh1Score());
	        ch1.getPosition().setX(saver.getCh1().getX());
	        ch1.getPosition().setY(saver.getCh1().getY());
	        contextzr.setCharAt(saver.getCh1().getY(), saver.getCh1().getX(), ch1);
	    }
	    
	    if (GameMode.isTwoPlayerMode(saver.getGameMode()) && ch2 != null) {
	        ch2.incScore(saver.getCh2Score());
	        ch2.getPosition().setX(saver.getCh2().getX());
	        ch2.getPosition().setY(saver.getCh2().getY());
	        this.contextzr.setCharAt(saver.getCh2().getY(), saver.getCh2().getX(), ch2);
	    }
	    
	    this.enemies.clear();	
	    Position [] savedEnemies = saver.getEnemiesPositions();
	    for (int i = 0; i < savedEnemies.length; i++) {
	    	Position savedPos = savedEnemies[i];
	        
	        CharType type = this.bp.getEnemies().get(i).getType(); 
	        
	        final Character e = CharacterFactory.get(type);
	        e.getPosition().setX(savedPos.getX());
	        e.getPosition().setY(savedPos.getY());

	        this.contextzr.setCharAt(savedPos.getY(), savedPos.getX(), e);
	        this.enemies.add(e);
	    }
	    
	    this.loadFruitPreviewImages();
	    LevelView.getInstace(BaDopoCreamGUI.getInstance(null)).resetView();
	}

	public LevelAvailableness getLevelAvailableness() {
		return this.levelAvailableness;
	}

	public CharType getT2() {
		return this.t2;
	}

	public CharType getT1() {
		return this.t1;
	}

	public boolean getAlreadyDead() {
		return ch1.isDead();
	}

	public int[] getFruitsPerLevel() {
		return this.fruitsPerLevel;
	}

	public int getFruitsCollectedSoFarThisRound() {
		return this.fruitsCollectedSoFarThisRound;
	}

	public int getFround() {
		return this.fround;
	}

	public LevelContextualizer getContextzr() {
		return this.contextzr;
	}	
}