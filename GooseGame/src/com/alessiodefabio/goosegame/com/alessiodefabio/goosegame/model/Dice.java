package com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model;

import java.util.Random;

public class Dice {

	private final static Integer DICE_SIZE = 6;

	private String result;

	public static Integer roll(){
		Random random = new Random();
		return random.nextInt(DICE_SIZE) + 1;
	}
}
