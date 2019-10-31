package com.alessiodefabio.goosegame;

import com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model.Dice;
import com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model.GameBoard;
import com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model.Player;
import com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model.PlayerCommandEnum;


import java.util.Map;
import java.util.Scanner;

public class GooseGameApplication {

	public static void main (String args[]){

		Scanner scanner;
		GameBoard gameBoard;
		String insertedLine = "";

		System.out.println("*************************Starting Goose Game application*************************");
		try{
			gameBoard = GameBoard.getInstance();
			System.out.println("Please type \"'add player' or 'move' follow by player name\" for add a new player or start the game");
			while(winner(gameBoard) == null){
				scanner = new Scanner(System.in);
				insertedLine = scanner.nextLine();
				try {
					PlayerCommandEnum insertedCommand = getCommandFromLine(insertedLine);

					switch (insertedCommand) {
						case ADD_PLAYER:
							manageAddPlayerCommand(insertedLine);
							break;
						case MOVE_PLAYER:
							manageMovePlayerCommand(insertedLine);
							break;
						default:
							System.out.println("Please type \"'add player' or 'move' follow by player name\" for add a new player or start the game");
					}
				}catch (Exception e){
					System.out.println("Attention wrong command inserted: " + insertedLine);
				}
			}
		}catch (Exception e){
			System.out.println("Warning, an error occurred during Game Goose application execution: " + e);
		}
		System.out.println("*************************End of Goose Game application*************************");
	}

	private static void manageMovePlayerCommand(String insertedLine){
		GameBoard gameBoard = GameBoard.getInstance();
		String playerName = "";
		Integer dice1Result = 0;
		Integer dice2Result = 0;
		if(gameBoard.getNumPlayers() >= GameBoard.MIN_PLAYER) {
			playerName = getPlayerNameFromMoveCommandLine(insertedLine);
			Player playingPLayer = gameBoard.getPlayerByName(playerName);
			if(playingPLayer != null) {
				dice1Result = Dice.roll();
				dice2Result = Dice.roll();
				Integer lastPosition = gameBoard.getPlayerPosition(playingPLayer);
				Integer diceSum = dice1Result + dice2Result;
				StringBuilder resultStamp = new StringBuilder();
				resultStamp.append(playerName)
						.append(" rolls ")
						.append(dice1Result)
						.append( ", " )
						.append(dice2Result)
						.append(". ")
						.append(playerName)
						.append(" moves from ")
						.append(lastPosition)
						.append(" to ");
				Integer newPosition = getNewPlayerPosition((lastPosition+diceSum), diceSum, resultStamp, playerName);
				System.out.println(resultStamp);
				gameBoard.updateBoard(playingPLayer, newPosition);
			}else{
				System.out.println(playerName + " :not existing player");
			}
		}else{
			System.out.println(gameBoard.getNumPlayers() + " :insufficient number of players");
		}
	}

	/*Recursive function that check the player position in order to manage the spaces effect (jump, bounces etc)*/
	private static Integer getNewPlayerPosition(Integer newPosition, Integer diceResult, StringBuilder resultStamp, String playerName) {
		Integer newPositionComputed = 0;
		String spaceName = "";
		switch(newPosition){
			case 6: //Bridge position, jump to the space 12
				newPositionComputed = 12;
				spaceName = ", The Bridge.";
				resultStamp.append(newPosition)
						.append(" ")
						.append(spaceName)
						.append("I jump to space \"12\"");
				break;
			case 5: //The Goose space, move forward with a jump
			case 9:
			case 14:
			case 18:
			case 23:
			case 27:
				newPositionComputed = newPosition + diceResult;
				spaceName = ", The goose. ";
				resultStamp.append(newPosition)
						.append(spaceName)
						.append(playerName)
						.append(" moves again and goes to ");
				break;
			case 63: //Win space
				resultStamp.append(GameBoard.MAX_SPACES).append(" ").append(playerName).append(" Wins!");
				newPositionComputed = GameBoard.MAX_SPACES;
				break;
			default: //in this case there's no space effect, i just control if player exceed the max space allowed
				newPositionComputed = newPosition;

				if(newPositionComputed > GameBoard.MAX_SPACES){
					newPositionComputed = outOfRangeSpace(newPositionComputed);
					resultStamp.append(GameBoard.MAX_SPACES)
							.append(" ")
							.append(playerName)
							.append(" bounces! ")
							.append(playerName)
							.append(" Returns to ")
							.append(newPosition);
				}else
					resultStamp.append(newPosition);
				break;
		}

		if(newPosition != newPositionComputed && newPosition != GameBoard.MAX_SPACES)
			newPositionComputed = getNewPlayerPosition(newPositionComputed, diceResult, resultStamp, playerName);
;
		return newPositionComputed;
	}

	/*This function return the position in case of bounces, it compute the overflow and subtracts it to the max allowed space number*/
	private static Integer outOfRangeSpace(Integer newPositionComputed) {
		Integer overflow = newPositionComputed -  GameBoard.MAX_SPACES;
		return GameBoard.MAX_SPACES - overflow;
	}

	private static void manageAddPlayerCommand(String insertedLine){
		GameBoard gameBoard = GameBoard.getInstance();
		String playerName = "";
		if(gameBoard.getNumPlayers() <= GameBoard.MAX_PLAYER) {
			playerName = getPlayerNameFromAddCommandLine(insertedLine);
			if(!gameBoard.existPlayer(playerName)) {
				gameBoard.addPlayer(new Player(playerName));
				System.out.println("Players: " + gameBoard.getAllPlayersNames());
			}else {
				System.out.println(playerName + " :already existing player");
			}
		}else{
			System.out.println("You can insert only six players!");
		}
	}

	private static String getPlayerNameFromAddCommandLine(String insertedLine) {
		return insertedLine.substring(PlayerCommandEnum.ADD_PLAYER.toString().length() + 1);
	}

	private static String getPlayerNameFromMoveCommandLine(String insertedLine){
		return insertedLine.substring(PlayerCommandEnum.MOVE_PLAYER.toString().length() + 1);
	}

	/*Extract the command, if consistent, from the inserted line */
	private static PlayerCommandEnum getCommandFromLine(String line){
		if(line.startsWith(PlayerCommandEnum.MOVE_PLAYER.toString()))
			return PlayerCommandEnum.MOVE_PLAYER;
		else if (line.startsWith(PlayerCommandEnum.ADD_PLAYER.toString()))
			return PlayerCommandEnum.ADD_PLAYER;
		else
			return null;
	}

	/*Return the players in game (is a unique string and not a list for practicality)*/
	private static String getInGamePlayers(GameBoard gameBoard){
		String playersName = "";
		for (Map.Entry<Player, Integer> entry : gameBoard.getBoardSituation().entrySet()) {
			Player player = entry.getKey();
			playersName = playersName.concat(player.getName() + ", ");
		}
		return playersName;
	}

	/*Return if exist the winner player*/
	private static Player winner(GameBoard gameBoard){
		for (Map.Entry<Player, Integer> entry : gameBoard.getBoardSituation().entrySet()) {
			Player player = entry.getKey();
			Integer position = entry.getValue();
			if (position == GameBoard.MAX_SPACES)
				return player;
		}
		return null;
	}
}
