package com.smt.poker.dao;

import com.smt.poker.domain.hand.Hand;
import com.smt.poker.domain.test.TempHand;

public interface HandDao {
	//public void saveHand(Hand hand);
	
	public void saveTempHand(TempHand hand);
}
