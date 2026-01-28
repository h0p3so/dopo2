package domain.map.chars.enemies;

import domain.map.chars.CharType;
import domain.map.chars.Position;

public class EnemySpawn {

	private Position spawnsAt;
	private CharType type;
	
	public EnemySpawn (final Position pos, final CharType type) {
		this.spawnsAt = pos;
		this.type = type;
	}
	
	public Position getPosition () {
		return this.spawnsAt;
	}
	
	public CharType getType () {
		return this.type;
	}
}
