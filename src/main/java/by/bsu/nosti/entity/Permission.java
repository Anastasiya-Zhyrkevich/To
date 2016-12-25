package by.bsu.nosti.entity;

public enum Permission {
	Edit(2), Delete(4), View(8);
	private final int value;

	private Permission(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
