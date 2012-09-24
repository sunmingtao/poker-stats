package com.smt.poker.persistence.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smt.poker.persistence.dao.HandDao;
import com.smt.poker.persistence.dao.PlayerDao;
import com.smt.poker.persistence.domain.Hand;
import com.smt.poker.persistence.domain.Player;

@Service("persistenceService")
public class PersistenceServiceImpl implements PersistenceService {

	@Autowired
	private HandDao handDao;
	
	@Autowired
	private PlayerDao playerDao;
	
	public void saveHands(List<Hand> hands) {
		for(Hand hand: hands){
			Set<Player> players = hand.getPlayers();
			List<Player> playersInDB = playerDao.findWithNames(hand.getPlayerNames());
			for (Player player: players){
				for (Player playerInDB: playersInDB){
					if (StringUtils.equals(player.getName(), player.getName())){
						player.setId(playerInDB.getId());
					}
				}
			}
			handDao.saveOrUpdate(hand);
		}
	}

}
