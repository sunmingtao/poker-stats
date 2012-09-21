package com.smt.poker.dao;

import java.util.List;

import com.smt.poker.domain.stat.PlayerStat;

/**
 * The DAO interface that manipulates the <code>com.smt.domain.Player</code> object
 * @author Mingtao Sun
 */
public interface PlayerDao {
	/**
	 * Retrieves a list of players
	 * @return
	 */
	public List<PlayerStat> getPlayers();
	
	/**
	 * Retrieves a player by his/her name
	 * @param name the name of the player to be retrieved
	 * @return
	 */
	public PlayerStat getPlayer(String name);
	
	/**
	 * Saves a player
	 * @param name the name of the player to be saved
	 */
	public void savePlayer(String name);
	
	/**
	 * Updates players
	 * @param players
	 */
	public void updatePlayers(List<PlayerStat> players);
	
}
