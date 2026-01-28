package domain.map.chars;

import domain.map.chars.cream.Chocolate;
import domain.map.chars.cream.Strawberry;
import domain.map.chars.cream.Vanilla;
import domain.map.chars.enemies.Maceta;
import domain.map.chars.enemies.Narwhal;
import domain.map.chars.enemies.Squid;
import domain.map.chars.enemies.Troll;
import exceptions.BLogger;
import exceptions.ProgrammerException;

public class CharacterFactory {
	public static Character get (final CharType type) {
		switch (type) {
			case VANILLA: { return new Vanilla(); }
			case CHOCOLATE: { return new Chocolate(); }
			case STRAWBERRY: { return new Strawberry(); }
			case TROLL: { return new Troll(); }
			case MACETA: { return new Maceta(); }
			case SQUID: { return new Squid(); }
			case NARWHAL: { return new Narwhal(); }
		}
		
		BLogger.logError(BLogger.SEVERE, new ProgrammerException(ProgrammerException.unreachable("CharacterFactory")));
		return null;
	}
}
