package com.smt.poker.persistence.domain;

/**
 * Represents a player's street
 * e.g. preflop, flop, turn, river
 * @author Mingtao Sun
 */
public enum Street {
	
	PREFLOP(100),
	
	FLOP(200),
	
	TURN(300),
	
	RIVER(400);
	
	private int value;
	
	private Street(int value){
		this.value = value;
	}
	
	public int getValue() { 
		return value; 
	}
	
	public static Street parse(int value){
		Street result = null;
		for (Street street : Street.values()){
			if (street.getValue() == value){
				result = street;
				break;
			}
		}
		return result;
	}
}
