package com.smt.poker.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.smt.poker.dao.PlayerDao;
import com.smt.poker.domain.hand.Hand;
import com.smt.poker.domain.stat.PlayerStat;
import com.smt.poker.handparser.HandParser;

/**
 * An implementation of <code>com.smt.service.Service</code>
 * @author MSK374
 *
 */
public class ServiceImpl implements Service {
	
	/** Player DAO to manipulate player data*/
	private PlayerDao playerDao;
	
	/** Hand parser to parse the hands store in files */
	private HandParser handParser;
	
	public List<Hand> importHands(File file) throws IOException {
		return handParser.parseHands(file);
	}
	
	public List<Hand> importHands(File[] files) throws IOException {
		List<Hand> allHands = new ArrayList<Hand>();
		for (File file: files){
			List<Hand> hands = importHands(file);
			allHands.addAll(hands);
		}
		return allHands;
	}
	
	public List<PlayerStat> getPlayers() {
		List<PlayerStat> players = new ArrayList<PlayerStat>();
		for (PlayerStat player: playerDao.getPlayers()){
			players.add(player);
		}
		Collections.sort(players);
		return players;
	}

	public void updatePlayerStats(Hand hand) {
		//Retrieve the current list of players from the database
		List<PlayerStat> players = getPlayers();
		updatePreflopStats(hand, players);
		updatePostflopStats(hand, players);
		playerDao.updatePlayers(players);
	}

	/**
	 * Updates preflop stats such as
	 * the number of voluntarily putting money in the pot at preflop,
	 * the number of preflop raise,
	 * and the number of three bet
	 * Also updates the number of hands played
	 * @param hand
	 * @param players the current list of players in the database
	 */
	private void updatePreflopStats(Hand hand, List<PlayerStat> players) {
		//Get names of players that participate in this hand
		String[] playerNames = hand.getPlayerNames();
		for (String playerName: playerNames){
			PlayerStat player = getPlayer(players, playerName);
			//If player not found in the list
			if (player == null){
				//Create a new player and add this player
				//to the player list
				player = new PlayerStat(playerName);
				players.add(player);
			}
			player.addHandNum();
			if (hand.isVpip(playerName)){
				player.addVpipNum();
			}
			if (hand.isPfr(playerName)){
				player.addPfrNum();
			}
			if (hand.hasButtonStealChance(playerName)){
				player.addButtonStealChanceNum();
			}
			if (hand.isButtonStealer(playerName)){
				player.addButtonStealNum();
			}
			if (hand.hasCutoffStealChance(playerName)){
				player.addCutoffStealChanceNum();
			}
			if (hand.isCutoffStealer(playerName)){
				player.addCutoffStealNum();
			}
			if (hand.hasSmallBlindStealChance(playerName)){
				player.addSmallBlindStealChanceNum();	
			}
			if (hand.isSmallBlindStealer(playerName)){
				player.addSmallBlindStealNum();
			}
			if (hand.has3BetChance(playerName)){
				player.addThreeBetChanceNum();
			}
			if (hand.isPreflop3Better(playerName)){
				player.addThreeBetNum();
			}
			if (hand.isGetting3BetPreflop(playerName)){
				player.addGet3BetNum();
			}
			if (hand.isFoldingTo3BetPreflop(playerName)){
				player.addFoldTo3BetNum();
			}
			if (hand.isPreflop4Better(playerName)){
				player.addFourBetNum();
			}
			if (hand.hasFlopCheckRaiseChance(playerName)){
				player.addFlopCheckRaiseChanceNum();
			}
			if (hand.isFlopCheckRaise(playerName)){
				player.addFlopCheckRaiseNum();
			}
		}
	}
	
	
	/**
	 * Updates flop stats such as
	 * the number of continuation bet,
	 * the number of fold to continuation bet
	 * @param hand
	 * @param players
	 */
	private void updatePostflopStats(Hand hand, List<PlayerStat> players) {
		//Get names of players that participate in this hand
		String[] playerNames = hand.getPlayerNames();
		for (String playerName: playerNames){
			//player object will never be null here
			//because updatePreflopStats() has added new player to 
			//the player list
			PlayerStat player = getPlayer(players, playerName);
			if (hand.hasCBetChance(playerName)){
				player.addContinuationBetChanceNum();
			}
			if (hand.isCBet(playerName)){
				player.addContinuationBetNum();
			}
			if (hand.isGettingCBet(playerName)){
				player.addGetContinuationBetNum();
			}
			if (hand.isCallingCBet(playerName)){
				player.addCallContinuationBetNum();
			}
			player.addPostflopCallNum(hand.postflopCallNum(playerName));
			player.addPostflopBetRaiseNum(hand.postflopBetRaiseNum(playerName));
		}
	}
	

	/**
	 * Returns the player with the specified name in the player list
	 * @param players the player list
	 * @param name the name of the player to be returned
	 * @return null if no player with such name exists
	 *         in the player list
	 */
	private PlayerStat getPlayer(List<PlayerStat> players, String name){
		for (PlayerStat player: players){
			if (player.getName().equalsIgnoreCase(name)){
				return player;
			}
		}
		return null;
	}
	

	public void updatePlayerStats(List<Hand> hands) {
		for (Hand hand: hands){
			updatePlayerStats(hand);
		}
	}
	
	/***************** Standard Setters ***********************/
	public void setPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}

	public void setHandParser(HandParser handParser) {
		this.handParser = handParser;
	}

}
