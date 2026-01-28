package domain;

import java.io.Serializable;
import domain.map.chars.CharType;
import domain.map.chars.Position;

public class Saver implements Serializable {	
	private String [] map;
	private final int levelNumber;
	private final int secondsElapsed;
	private final int roundNumber;
	private final GameMode gameMode;
	private final CharType ch1Type;
	private final CharType ch2Type;
	private final int p1Score;
	private final int p2Score;
	private final Position p1Pos;
	private final Position p2Pos;
	private Position [] enemiesPos;
	
	public Saver (final Control control) {
		this.getCurrentStateOfTheMpa(control.getContextzr());
		this.levelNumber = control.getLevelNumber();
		this.secondsElapsed = control.getTimeLeft();
		this.roundNumber = control.getFround();
		this.gameMode = control.getMode();
		this.ch1Type = control.getT1();
		this.ch2Type = control.getT2();
		this.p1Score = control.getCh1Score();
		this.p2Score = (GameMode.isTwoPlayerMode(gameMode)) ? control.getCh2Score() : 0;
		this.p1Pos = control.getCharacter1().getPosition();
		this.p2Pos = (GameMode.isTwoPlayerMode(gameMode)) ? control.getCharacter2().getPosition() : null;

		this.enemiesPos = new Position[control.getEnemies().size()];
		for (int i = 0; i < control.getEnemies().size(); i++) {
			this.enemiesPos[i] = control.getEnemies().get(i).getPosition();
		}
	}
	
	private void getCurrentStateOfTheMpa (final LevelContextualizer context) {
		this.map = new String[18];
		for (int i = 0; i < 18; i++) {
			char []line = new char[18];
			for (int j = 0; j < 18; j++) {
				line[j] = context.getTileAt(i, j).getId();
			}	
			this.map[i] = new String(line);
		}
	}
	
	public String [] getMap () {
		return this.map;
	}
	
	public int getLevelNumber () {
		return this.levelNumber;
	}
	
	public int getTimeLeft () {
		return this.secondsElapsed;
	}
	
	public int getRoundNumber () {
		return this.roundNumber;
	}
	
	public GameMode getGameMode () {
		return this.gameMode;
	}
	
	public CharType getCh1Type () {
		return this.ch1Type;
	}

	public CharType getCh2Type () {
		return this.ch2Type;
	}
	
	public int getCh1Score () {
		return this.p1Score;
	}

	public int getCh2Score () {
		return this.p2Score;
	}
	
	public Position getCh1 () {
		return this.p1Pos;
	}

	public Position getCh2 () {
		return this.p2Pos;
	}
	
	public Position[] getEnemiesPositions () {
		return this.enemiesPos;
	}
}