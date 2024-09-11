package org.aldanari.asciiinc.item;

public enum Symbols {
	PIPE('|');


	private final Character skin;

	Symbols(Character skin) {
		this.skin = skin;
	}

	public Character getSkin() {
		return skin;
	}
}
