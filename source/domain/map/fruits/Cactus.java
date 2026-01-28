package domain.map.fruits;

import domain.LevelContextualizer;
import domain.map.tiles.TileId;

public class Cactus extends Fruit {
    private static final int TELEPORT_COOLDOWN = 5 * 60;
    private int changeTimer = 0;

	public Cactus () {
		super(
			"assets/fruits/cactus/normal.png",
			"assets/fruits/cactus/frozen.png",
			"assets/fruits/cactus/danger.png",
			250,
			TileId.FRUIT_CACTUS
		);
	}

	@Override
	public void interact (LevelContextualizer lc) {
		this.changeTimer++;
		if (this.changeTimer >= TELEPORT_COOLDOWN) {
			if (!this.deadly) {
				this.setDeadlyVersion();
			}
			else {
				this.setDeadlyVersion();
			}
			this.deadly = !this.deadly;
			this.changeTimer = 0;
		}
	}

	@Override public boolean moveable () {
		return false;
	}
}