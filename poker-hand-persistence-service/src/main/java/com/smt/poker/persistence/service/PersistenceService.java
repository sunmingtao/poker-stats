package com.smt.poker.persistence.service;

import java.util.List;

import com.smt.poker.persistence.domain.Hand;

public interface PersistenceService {
	public void saveHands(List<Hand> hands);
}
