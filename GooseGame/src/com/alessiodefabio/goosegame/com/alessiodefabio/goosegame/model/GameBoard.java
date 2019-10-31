package com.alessiodefabio.goosegame.com.alessiodefabio.goosegame.model;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

	public final static Integer MAX_SPACES = 63;
	public final static Integer MIN_PLAYER = 2;
	public final static Integer MAX_PLAYER = 6;

	private static GameBoard gameBoard = null;
	private Integer numPlayers;
	private Map<Player, Integer> boardSituation;
	//private Map<Player, Integer> previousBoardSituation;

	public GameBoard(){
		this.numPlayers = 0;
		this.boardSituation = new HashMap<>();
	}

	public static GameBoard getInstance(){
		if(gameBoard == null)
			gameBoard = new GameBoard();
		return gameBoard;
	}

	public Integer getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(Integer numPlayers) {
		this.numPlayers = numPlayers;
	}

	public Map<Player, Integer> getBoardSituation() {
		return boardSituation;
	}

	public void setBoardSituation(Map<Player, Integer> boardSituation) {
		this.boardSituation = boardSituation;
	}

	public void addPlayer(Player newPlayer){
		this.boardSituation.put(newPlayer, 0);
		this.numPlayers++ ;
	}

	public Player getPlayerByName(String name){
		for (Map.Entry<Player, Integer> entry : this.boardSituation.entrySet()) {
			Player k = entry.getKey();
			if (k.getName().equals(name))
				return k;
		}
		return null;
	}

	public Integer getPlayerPosition(Player player){
		for (Map.Entry<Player, Integer> entry : this.boardSituation.entrySet()) {
			Player k = entry.getKey();
			Integer v = entry.getValue();
			if (k.equals(player))
				return v;
		}
		return null;
	}

	public void updateBoard(Player player, Integer step){
		Integer space = 0;
		for (Map.Entry<Player, Integer> entry : this.boardSituation.entrySet()) {
			Player k = entry.getKey();
			if (k.getName().equals(player.getName()))
				space += step;
		}
		this.boardSituation.put(player, space);
	}

	public Boolean existPlayer(String name){
		for (Map.Entry<Player, Integer> entry : this.boardSituation.entrySet()) {
			Player k = entry.getKey();
			if (k.getName().equals(name))
				return true;
		}
		return false;
	}

	public String getAllPlayersNames(){
		String names = "";
		for (Map.Entry<Player, Integer> entry : this.boardSituation.entrySet()) {
			Player k = entry.getKey();
			names = names + k.getName() + ", " ;
		}
		return names;
	}

}
