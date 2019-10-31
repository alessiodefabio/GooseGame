package com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model;

public enum PlayerCommandEnum {
	ADD_PLAYER("add player"),
	MOVE_PLAYER("move");

	private String value;

	PlayerCommandEnum(String value){
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static PlayerCommandEnum fromValue(String text) {
		for (PlayerCommandEnum b : PlayerCommandEnum.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
