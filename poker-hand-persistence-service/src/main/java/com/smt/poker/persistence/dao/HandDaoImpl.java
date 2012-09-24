package com.smt.poker.persistence.dao;

import org.springframework.stereotype.Repository;

import com.smt.poker.persistence.domain.Hand;

@Repository("handDao")
public class HandDaoImpl extends GenericDaoImpl<Hand> 
	implements HandDao {
	
}
