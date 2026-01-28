/**    _
 *   ,' `,.
 *   >-.(__)
 *  (_,-' |
 *    `.  |
 *      `.| hjw
 *        `
 *
 * Manages which predefined levels are unlocked for the player.
 * The first level is always available by default, while the rest
 * may be unlocked as the player progresses.  
 *
 * Persistence is not yet implemented, but placeholders exist for
 * loading and saving level availability across sessions.
 *
 * @author juad
 */
package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import exceptions.BLogger;

public class LevelAvailableness {
	public static final int MAX_LEVELS = 40;
	public static final int IMPLEMENTED_LEVELS = 4;
	private boolean[] levelAvailableness;
	private int availableNumberFromFile;
	private int passedThisSession;

	public LevelAvailableness () {
		this.levelAvailableness = new boolean[MAX_LEVELS];
		this.loadLevelsFromPreviousSessions();
		
		for (int i = 0; i < this.availableNumberFromFile + 1; i++) {
			this.levelAvailableness[i] = true;
		}
		this.passedThisSession = 0;
	}

	private void loadLevelsFromPreviousSessions () {
        try {
            File saveFile = new File("levels/availableness");
            Scanner fileScanner = new Scanner(saveFile);
            
            if (fileScanner.hasNextInt()) {
                this.availableNumberFromFile = fileScanner.nextInt();
            } else {
                this.availableNumberFromFile = 0;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            BLogger.logError(BLogger.SEVERE, e);
        } catch (Exception e) {}
        
        if (this.availableNumberFromFile >= IMPLEMENTED_LEVELS) {
        	this.availableNumberFromFile = IMPLEMENTED_LEVELS - 1;
        }
        
        this.passedThisSession = this.availableNumberFromFile;
    } 

	/**
	 * Marks the specified level as available to the player.
	 *
	 * @param level Level number starting at 0.
	 */
	public void setLevelAsAvaialble (final int level) {
		this.levelAvailableness[level] = true;
		this.passedThisSession++;
	}

	/**
	 * Saves any changes made to the level availability state.
	 */
	public void saveChanges () {
	    try {
	        FileWriter fileWriter = new FileWriter("levels/availableness", false);
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        printWriter.print(this.passedThisSession > this.availableNumberFromFile ? this.passedThisSession : this.availableNumberFromFile);
	        printWriter.close();
	    } catch (IOException e) {
	    	BLogger.logError(BLogger.SEVERE, e);
	    }
	}

	/**
	 * Returns the boolean array representing the availability
	 * status of all predefined levels.
	 *
	 * @return Array of unlocked levels.
	 */
	public boolean[] getAvailableLevels () {
		return this.levelAvailableness;
	}
}
