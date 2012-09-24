package com.smt.poker.persistence.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.smt.poker.persistence.domain.Player;

@Repository("playerDao")
public class PlayerDaoImpl extends GenericDaoImpl<Player> 
	implements PlayerDao {

	@SuppressWarnings("unchecked")
	public List<Player> findWithNames(List<String> names) {
		Query query = em.createQuery("select p from Player p where p.name in (:names)");
		query.setParameter("names", names);
		return query.getResultList();
	}

}
