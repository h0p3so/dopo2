package domain.map.fruits;

import domain.map.chars.Position;

public class FruitSpawn {

	private Position spawnPos;
	private char fruitId;
	
	public FruitSpawn (final Position pos, final char id) {
		this.spawnPos = pos;
		this.fruitId = id;
	}
	
	public Position getSpawnPos () {
		return this.spawnPos;
	}
	
	public char getId () {
		return this.fruitId;
	}
}
