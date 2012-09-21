package com.smt.poker.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smt.poker.domain.hand.Hand;
import com.smt.poker.domain.test.TempHand;

@Repository("handDao")
public class HandDaoImpl implements HandDao {

	@PersistenceContext
	private EntityManager entityManager;
	
//	@Transactional
//	public void saveHand(Hand hand) {
//		entityManager.persist(hand);
//	}
	
	@Transactional
	public void saveTempHand(TempHand hand) {
		entityManager.persist(hand);
	}

}
