package domain;

import exceptions.BLogger;
import exceptions.ProgrammerException;

public enum MotionDirection {
	UP,
	LEFT,
	DOWN,
	RIGHT,
	AUTO;
	
	public static GoingDirection turnIntoGoingDirection (final MotionDirection dir) {
		switch (dir) {
			case UP: return GoingDirection.BACK;
			case DOWN: return GoingDirection.FRONT;
			case LEFT: return GoingDirection.LEFT;
			case RIGHT: return GoingDirection.RIGHT;
		}
		
		BLogger.logError(BLogger.SEVERE, new ProgrammerException(ProgrammerException.unreachable("MotionDirection")));
		return null;
	}
}
