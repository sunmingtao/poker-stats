package com.smt.poker.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.smt.poker.domain.hand.Hand;
import com.smt.poker.domain.stat.PlayerStat;

/**
 * The service interface
 * @author Mingtao Sun
 */
public interface Service {
	/**
	 * Retrieves a list of players
	 * @return
	 */
	public List<PlayerStat> getPlayers();
	
	/**
	 * Updates players' stats for a hand
	 * @param hand the hand the players played
	 */
	public void updatePlayerStats(Hand hand);
	
	/**
	 * Updates players' stats for a list of hands
	 * @param hands the hands the players played
	 */
	public void updatePlayerStats(List<Hand> hands);
	
	/**
	 * Imports hands from the specified file
	 * @return a list of hands imported from the specified file
	 */
	public List<Hand> importHands(File file) throws IOException;
	
	/**
	 * Imports hands from the specified files
	 * @return a list of hands imported from the specified files
	 */
	public List<Hand> importHands(File[] files) throws IOException;
}
