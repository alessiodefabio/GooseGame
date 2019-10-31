package com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model;

public class Player {

	private String name;
	private int actualPosition;

	public Player(){}

	public Player(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(int actualPosition) {
		this.actualPosition = actualPosition;
	}
}
