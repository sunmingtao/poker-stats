package com.smt.poker.dao;

import java.util.ArrayList;
import java.util.List;

import com.smt.poker.domain.stat.PlayerStat;

/**
 * A mock implementation of <code>com.smt.dao.PlayerDao</code>
 * @author MSK374
 *
 */
public class PlayerDaoMockImpl implements PlayerDao {

	private static List<PlayerStat> players = null;
	
	
	public PlayerDaoMockImpl(){
		players = new ArrayList<PlayerStat>();
	}
	
	public List<PlayerStat> getPlayers() {
		return players;
	}

	public PlayerStat getPlayer(String name) {
		for (PlayerStat player: players){
			if (player.getName().equalsIgnoreCase(name)){
				return player;
			}
		}
		return null;
	}

	public void savePlayer(String name) {
		if (getPlayer(name) == null){
			players.add(new PlayerStat(name));
		}else{
			throw new IllegalArgumentException("Player "+name+" already exists");
		}
		
	}

	public void updatePlayers(List<PlayerStat> newPlayers) {
		players.clear();
		for (PlayerStat newPlayer: newPlayers){
			players.add(newPlayer);
		}
	}

}
