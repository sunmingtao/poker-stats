package com.smt.poker.persistence.dao;

import java.util.List;

import com.smt.poker.persistence.domain.Player;

public interface PlayerDao extends GenericDao<Player> {
	public List<Player> findWithNames(List<String> names);
}
