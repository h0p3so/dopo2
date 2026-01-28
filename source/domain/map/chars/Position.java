package domain.map.chars;

import java.io.Serializable;

public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;
	
	public Position (final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX () {
		return this.x;
	}

	public int getY () {
		return this.y;
	}
	
	public void setX (final int x) {
		this.x = x;
	}

	public void setY (final int y) {
		this.y = y;
	}
}
