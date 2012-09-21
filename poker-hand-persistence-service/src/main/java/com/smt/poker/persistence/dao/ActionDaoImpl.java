package com.smt.poker.persistence.dao;

import org.springframework.stereotype.Repository;

import com.smt.poker.persistence.domain.Action;

@Repository("actionDao")
public class ActionDaoImpl extends GenericDaoImpl<Action> 
	implements ActionDao {

}
