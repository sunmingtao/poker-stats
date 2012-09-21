package com.smt.poker.domain.stat;

import java.math.BigDecimal;

/**
 * Represents a player's information
 * @author Mingtao Sun
 */
public class PlayerStat implements Comparable<PlayerStat>{
	
	/** Not available */
	private static final String NOT_AVAILABLE = "N/A";
	
	/** Player's name */
	private final String name;
	/** Total number of hands played */
	private int handNum;
	/** 
	 * Number of voluntarily putting money in the pot at preflop
	 * e.g. Player calls or raises
	 */
	private int vpipNum;
	/** 
	 * Number of preflop raise
	 * e.g. Player raises  
	 */
	private int pfrNum;
	
	/**
	 * Number of preflop re-raise chance
	 * e.g. Face a open raise, now the player has the chance to re-raise
	 */
	private int threeBetChanceNum;
	/**
	 * Number of preflop re-raise
	 * e.g. Player1 raises, Player2 raises
	 * Player2's move is considered a three bet
	 */
	private int threeBetNum;
	
	/**
	 * Number of preflop re-re-raise
	 * e.g. Player1 raises, Player2 raises, Player1 raises
	 * Player1's move is considered a four bet
	 */
	private int fourBetNum;
	
	/**
	 * Number of steal chance at button
	 */
	private int buttonStealChanceNum;
	
	/**
	 * Number of steal attempt at button
	 */
	private int buttonStealNum;
	
	/**
	 * Number of steal chance at cutoff 
	 */
	private int cutoffStealChanceNum;
	
	/**
	 * Number of steal attempt at cutoff
	 */
	private int cutoffStealNum;
	
	/**
	 * Number of steal chance at small blind
	 */
	private int smallBlindStealChanceNum;
	
	/**
	 * Number of steal attempt at small blind
	 */
	private int smallBlindStealNum;
	
	/**
	 * Number of continuation bet
	 * e.g. Player1, who is the preflop raiser, bets on the flop
	 */
	private int continuationBetNum;
	
	/**
	 * Number of chances of continuation bet
	 * e.g. Player1, who is the preflop raiser, sees the flop with another player
	 * And Player1 doesn't get donk bet so he has the chance to
	 * continuation bet
	 */
	private int continuationBetChanceNum;
	
	/**
	 * Number of facing preflop aggressor's continuation bet on flop
	 * i.e. calls the aggressor's raise
	 */
	private int faceContinuationBetNum;
	
	/** 
	 * Number of calling preflop aggressor's continuation bet on flop
	 */
	private int callContinuationBetNum;
	
	/**
	 * Number of folding to 3 bet on preflop
	 */
	private int foldTo3BetNum;
	
	/**
	 * Number of getting 3 bet on preflop
	 */
	private int get3BetNum;
	
	/**
	 * Number of flop check raise chance
	 */
	private int flopCheckRaiseChanceNum;
	
	/**
	 * Number of flop check raise
	 */
	private int flopCheckRaiseNum;
	
	/**
	 * Number of calls on postflop
	 */
	private int postflopCallNum;
	
	/**
	 * Number of bets/raises on postflop
	 */
	private int postflopBetRaiseNum;
	
	/**
	 * Default Constructor
	 * @param name
	 */
	public PlayerStat(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Increase the number of hands played by 1
	 */
	public void addHandNum(){
		handNum++;
	}
	
	/**
	 * Increase the number of voluntarily putting money in the pot at preflop by 1
	 */
	public void addVpipNum(){
		vpipNum++;
	}
	
	/**
	 * Increase the number of preflop raise by 1
	 */
	public void addPfrNum(){
		pfrNum++;
	}
	
	/**
	 * Increase the number of three bet chance by 1
	 */
	public void addThreeBetChanceNum(){
		threeBetChanceNum++;
	}
	
	/**
	 * Increase the number of three bet by 1
	 */
	public void addThreeBetNum(){
		threeBetNum++;
	}
	
	/**
	 * Increase the number of four bet by 1
	 */
	public void addFourBetNum(){
		fourBetNum++;
	}
	
	/**
	 * Increase the number of steal chance at button by 1
	 */
	public void addButtonStealChanceNum(){
		buttonStealChanceNum++;
	}
	
	/**
	 * Increase the number of steal attempt at button by 1
	 */
	public void addButtonStealNum(){
		buttonStealNum++;
	}
	
	/**
	 * Increase the number of steal chance at cutoff by 1
	 */
	public void addCutoffStealChanceNum(){
		cutoffStealChanceNum++;
	}
	
	/**
	 * Increase the number of steal attempt at cutoff by 1
	 */
	public void addCutoffStealNum(){
		cutoffStealNum++;
	}
	
	/**
	 * Increase the number of steal chance at small blind by 1
	 */
	public void addSmallBlindStealChanceNum(){
		smallBlindStealChanceNum++;
	}
	
	/**
	 * Increase the number of steal attempt at small blind by 1
	 */
	public void addSmallBlindStealNum(){
		smallBlindStealNum++;
	}
	
	/**
	 * Increase the number of continuation bet by 1
	 */
	public void addContinuationBetNum(){
		continuationBetNum++;
	}
	
	/**
	 * Increase the number of seeing flop as aggressor by 1
	 */
	public void addContinuationBetChanceNum(){
		continuationBetChanceNum++;
	}

	/**
	 * Increase the number of getting continuation bet by 1
	 */
	public void addGetContinuationBetNum(){
		faceContinuationBetNum++;
	}
	
	/**
	 * Increase the number of calling continuation bet by 1
	 */
	public void addCallContinuationBetNum(){
		callContinuationBetNum++;
	}
	
	/**
	 * Increase the number of folding to 3 bet on preflop by 1
	 */
	public void addFoldTo3BetNum(){
		foldTo3BetNum++;
	}
	
	/**
	 * Increase the number of getting 3 bet on preflop by 1
	 */
	public void addGet3BetNum(){
		get3BetNum++;
	}
	
	/**
	 * Increase number of flop check raise chance by 1
	 */
	public void addFlopCheckRaiseChanceNum(){
		flopCheckRaiseChanceNum++;
	}
	
	/**
	 * Increase number of flop check raise by 1
	 */
	public void addFlopCheckRaiseNum(){
		flopCheckRaiseNum++;
	}
	
	/**
	 * Increase the number of postflop calls by the specified number
	 */
	public void addPostflopCallNum(int number){
		postflopCallNum+=number;
	}
	
	/**
	 * Increase the number of postflop bets/raises by the specified number
	 */
	public void addPostflopBetRaiseNum(int number){
		postflopBetRaiseNum+=number;
	}
	
	/**
	 * Voluntarily put money in the pot rate
	 * @return vpipNum/handNum
	 */
	public String vpip(){
		String result = NOT_AVAILABLE;
		if (handNum != 0){
			result = new BigDecimal(vpipNum*100).divide(
					new BigDecimal(handNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Preflop raise rate
	 * @return pfrNum/handNum
	 */
	public String pfr(){
		String result = NOT_AVAILABLE;
		if (handNum != 0){
			result = new BigDecimal(pfrNum*100).divide(
					new BigDecimal(handNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Three bet rate
	 * @return threeBetNum/threeBetChanceNum
	 */
	public String threeBet(){
		String result = NOT_AVAILABLE;
		if (threeBetChanceNum != 0){
			result = new BigDecimal(threeBetNum*100).divide(
					new BigDecimal(threeBetChanceNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Four bet rate
	 * @return threeBetNum/handNum
	 */
	public String fourBet(){
		String result = NOT_AVAILABLE;
		if (get3BetNum != 0){
			result = new BigDecimal(fourBetNum*100).divide(
					new BigDecimal(get3BetNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Button open raise rate
	 * @return buttonOpenRaiseNum/buttonOpenRaiseChanceNum
	 */
	public String buttonSteal(){
		String result = NOT_AVAILABLE;
		if (buttonStealChanceNum != 0){
			result = new BigDecimal(buttonStealNum*100).divide(
					new BigDecimal(buttonStealChanceNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Cutoff open raise rate
	 * @return cutoffOpenRaiseNum/cutoffOpenRaiseChanceNum
	 */
	public String cutoffSteal(){
		String result = NOT_AVAILABLE;
		if (cutoffStealChanceNum != 0){
			result = new BigDecimal(cutoffStealNum*100).divide(
					new BigDecimal(cutoffStealChanceNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Small blind open raise rate
	 * @return smallBlindOpenRaiseNum/smallBlindOpenRaiseChanceNum
	 */
	public String smallBlindSteal(){
		String result = NOT_AVAILABLE;
		if (smallBlindStealChanceNum != 0){
			result = new BigDecimal(smallBlindStealNum*100).divide(
					new BigDecimal(smallBlindStealChanceNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Continuation bet rate
	 * @return continuationBetNum/continuationBetChanceNum
	 */
	public String continuationBet(){
		String result = NOT_AVAILABLE;
		if (continuationBetChanceNum != 0){
			result = new BigDecimal(continuationBetNum*100).divide(
					new BigDecimal(continuationBetChanceNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Calling continuation bet rate
	 * @return callContinuationBetNum/getContinuationBetNum
	 */
	public String callContinuationBet(){
		String result = NOT_AVAILABLE;
		if (faceContinuationBetNum != 0){
			result = new BigDecimal(callContinuationBetNum*100).divide(
					new BigDecimal(faceContinuationBetNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Folding to 3 bet rate
	 * @return foldTo3BetNum/get3BetNum
	 */
	public String foldTo3Bet(){
		String result = NOT_AVAILABLE;
		if (get3BetNum != 0){
			result = new BigDecimal(foldTo3BetNum*100).divide(
					new BigDecimal(get3BetNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Flop check/raise rate
	 * @return flopCheckRaiseNum/flopCheckRaiseNumChance
	 */
	public String flopCheckRaise(){
		String result = NOT_AVAILABLE;
		if (flopCheckRaiseChanceNum != 0){
			result = new BigDecimal(flopCheckRaiseNum*100).divide(
					new BigDecimal(flopCheckRaiseChanceNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}
	
	/**
	 * Aggressive factor
	 * @return postflopBetRaiseNum/postflopCallNum
	 */
	public String af(){
		String result = NOT_AVAILABLE;
		if (postflopCallNum != 0){
			result = new BigDecimal(postflopBetRaiseNum).divide(
					new BigDecimal(postflopCallNum), 2, BigDecimal.ROUND_HALF_DOWN).toString();
		}
		return result;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("\n");
		sb.append("Hands played: ").append(handNum).append("\n");
		sb.append("VPIP: ").append(vpip()).append(" (").append(vpipNum).append("/").append(handNum).append(")").append("\n");
		sb.append("PFR: ").append(pfr()).append(" (").append(pfrNum).append("/").append(handNum).append(")").append("\n");
		sb.append("AF: ").append(af()).append(" (").append(postflopBetRaiseNum).append("/").append(postflopCallNum).append(")").append("\n");
		sb.append("3Bet: ").append(threeBet()).append(" (").append(threeBetNum).append("/").append(threeBetChanceNum).append(")").append("\n");
		sb.append("4Bet: ").append(fourBet()).append(" (").append(fourBetNum).append("/").append(get3BetNum).append(")").append("\n");
		sb.append("Fold to 3Bet: ").append(foldTo3Bet()).append(" (").append(foldTo3BetNum).append("/").append(get3BetNum).append(")").append("\n");
		sb.append("CBet: ").append(continuationBet()).append(" (").append(continuationBetNum).append("/").append(continuationBetChanceNum).append(")").append("\n");
		sb.append("Call CBet: ").append(callContinuationBet()).append(" (").append(callContinuationBetNum).append("/").append(faceContinuationBetNum).append(")").append("\n");
		sb.append("Flop Check/Raise: ").append(flopCheckRaise()).append(" (").append(flopCheckRaiseNum).append("/").append(flopCheckRaiseChanceNum).append(")").append("\n");
		sb.append("Button Steal: ").append(buttonSteal()).append(" (").append(buttonStealNum).append("/").append(buttonStealChanceNum).append(")").append("\n");
		sb.append("Cutoff Steal: ").append(cutoffSteal()).append(" (").append(cutoffStealNum).append("/").append(cutoffStealChanceNum).append(")").append("\n");
		sb.append("Small blind Steal: ").append(smallBlindSteal()).append(" (").append(smallBlindStealNum).append("/").append(smallBlindStealChanceNum).append(")").append("\n");
		return sb.toString();
	}
	
	public int compareTo(PlayerStat p) {
		return  p.handNum - this.handNum;
	}
	
	/************************ Standard Getters *************************/
	public int getHandNum() {
		return handNum;
	}
	public String getName() {
		return name;
	}

	
	
}
