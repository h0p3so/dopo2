package domain;

public enum GoingDirection {
	FRONT(0),
	BACK(1),
	LEFT(2),
	RIGHT(3);
	
	private int asIndex;

	GoingDirection (final int asIndex) {
		this.setAsIndex(asIndex);
	}

	public int getAsIndex() {
		return asIndex;
	}

	public void setAsIndex(int asIndex) {
		this.asIndex = asIndex;
	} 	
}
