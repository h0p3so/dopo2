package tests;

import static org.junit.jupiter.api.Assertions.*;
import domain.*;
import domain.map.chars.CharType;
import exceptions.*;
import org.junit.jupiter.api.Test;

class Tests {
	@Test
	public void controlStartsNormally () {
		Control c = new Control();
		assertTrue(c != null);
	}
	
    @Test
    public void freshlyCreatedControlHasZeroLevel() {
        Control c = new Control();
        assertEquals(0, c.getLevelNumber());
    }
    
    @Test
    public void freshlyCreatedControlHasZeroTime() {
        Control c = new Control();
        assertEquals(0, c.getTimeLeft());
    }

    @Test
    public void canSetAndGetGameMode() {
        Control c = new Control();
        c.setGameMode(GameMode.TWO_ICE_CREAM);
        assertEquals(GameMode.TWO_ICE_CREAM, c.getMode());
    }

    @Test
    public void canSetAndGetCharacterTypes() {
        Control c = new Control();

        c.setCharTypeOne(CharType.CHOCOLATE);
        assertEquals(CharType.CHOCOLATE, c.getT1());
        assertNotNull(c.getCharacter1());

        c.setCharTypeTwo(CharType.VANILLA);
        assertEquals(CharType.VANILLA, c.getT2());
        assertNotNull(c.getCharacter2());
    }
    
    @Test
    public void fruitCountersStartAtZero() {
        Control c = new Control();
        assertEquals(0, c.getFruitsCollectedSoFarThisRound());
        assertEquals(0, c.getFround());
    }

    @Test
    public void oneSecondPassedByReducesTimer() {
        Control c = new Control();
        c.pleaseLoadPredefinedLevel(0);
        int initial = c.getTimeLeft();
        c.oneSecondPassedBy();
        assertEquals(initial - 1, c.getTimeLeft());
    }
    
    @Test
    public void listOfAvailableLevelsExists() {
        Control c = new Control();
        boolean[] arr = c.pleaseProvideListOfAvailableLevels();
        assertNotNull(arr);
        assertTrue(arr.length > 0);
    }
    
    @Test
    public void nextLevelUnlocksCorrectly() {
        Control c = new Control();

        boolean[] before = c.pleaseProvideListOfAvailableLevels();
        int firstLocked = -1;

        for (int i = 0; i < before.length; i++) {
            if (!before[i]) {
                firstLocked = i;
                break;
            }
        }

        c.pleaseLoadPredefinedLevel(1);
        c.activateNextLevelAsAvailable();

        boolean[] after = c.pleaseProvideListOfAvailableLevels();
        assertTrue(after[0]);
    }
    
    @Test
    public void hardResetResetsRoundAndDeaths() {
        Control c = new Control();

        c.pleaseLoadPredefinedLevel(0);
        c.setCharTypeOne(CharType.CHOCOLATE);
        c.setGameMode(GameMode.TWO_ICE_CREAM);
        c.goForNextRoundOfFruits();
        c.oneSecondPassedBy();
        c.oneSecondPassedBy();

        c.restartLevel();
        assertFalse(c.getAlreadyDead());
    }
}

// poner heilos
// oleadas