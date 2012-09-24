package com.smt.poker.persistence.dao;

import org.springframework.stereotype.Repository;

import com.smt.poker.persistence.domain.Player;

@Repository("playerDao")
public class PlayerDaoImpl extends GenericDaoImpl<Player> 
	implements PlayerDao {

}
