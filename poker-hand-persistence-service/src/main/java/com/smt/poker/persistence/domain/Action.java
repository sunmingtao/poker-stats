package com.smt.poker.persistence.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Represents a player's action
 * e.g. Sunmt bets/John folds
 * @author Mingtao Sun
 */
@Entity
@Table(name="T_ACTION")
public class Action {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="ACTION_GENERATOR")
	@TableGenerator(name="ACTION_GENERATOR", initialValue=1, allocationSize=1)
	private Integer id;
	
	/** Player's name */
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="F_PLAYER_ID")
	private Player player;
	/** 
	 * Player's move in this action
	 * e.g. folds/checks/bets/calls/raises 
	 */
	@Enumerated(EnumType.STRING)
	private Move move;
	/**
	 * The amount involved in this action
	 * e.g. the amount for 'fold' and 'check' is 0
	 * the amount for 'bets $0.30' is 0.3
	 * the amount for 'calls $0.30' is 0.3
	 * the amount for 'raises $0.50 to $0.80' is 0.8
	 * the amount for 'posts big blind $0.10' is 0.1
	 */
	@Column(name="BIG_BLIND", precision=5, scale=2)
	private BigDecimal amount;
	/**
	 * Constructor
	 * @param playerName
	 * @param move
	 */
	public Action(Player player, Move move, BigDecimal amount) {
		super();
		validateAction(move, amount);
		this.player = player;
		this.move = move;
		this.amount = amount;
	}
	
	public Action(){
	}
	
	/**
	 * Validates an action
	 * e.g. if move is 'fold', the amount must be 0
	 * if move is 'raise', the amount must not be 0
	 * @param move2
	 * @param amount2
	 */
	private void validateAction(Move move, BigDecimal amount) {
		//When move is bet/raise/call/post, amount must be greater than 0
		if (move.hasAmount() && amount.compareTo(BigDecimal.ZERO) == 0){
			throw new IllegalArgumentException("When move is "+move+", amount must not be 0");
		}
		if (!move.hasAmount() && amount.compareTo(BigDecimal.ZERO) != 0){
			throw new IllegalArgumentException("When move is "+move+", amount must be 0. " +
					"Current amount is "+amount);
		}
	}

	/** 
	 * Checks if this action is performed by the specified player name
	 * @param playerName
	 * @return
	 */
	public boolean performedBy(String playerName){
		return true;
		//return this.playerName.equalsIgnoreCase(playerName);
	}
	
	/**
	 * Checks if this action is a call move
	 * @return
	 */
	public boolean isCall(){
		return move == Move.CALL;
	}
	
	/**
	 * Checks if this action is a raise move
	 * @return
	 */
	public boolean isRaise(){
		return move == Move.RAISE;
	}
	
	/**
	 * Checks if this action is a raise move
	 * @return
	 */
	public boolean isBet(){
		return move == Move.BET;
	}
	
	/**
	 * Checks if this action is a fold move
	 * @return
	 */
	public boolean isFold(){
		return move == Move.FOLD;
	}
	
	/**
	 * Checks if this action is a check move
	 * @return
	 */
	public boolean isCheck(){
		return move == Move.CHECK;
	}
	
	/**
	 * Checks if this action is a post move
	 * @return
	 */
	public boolean isPost(){
		return move == Move.POST;
	}
	
	/**
	 * Checks if this action is an uncall move
	 * @return
	 */
	public boolean isUncall(){
		return move == Move.UNCALL;
	}
	
	/**
	 * Checks if this action is a call or raise move
	 * @return
	 */
	public boolean isCallOrRaise(){
		return isCall() || isRaise();
	}
	
	/**
	 * Checks if this action is a bet or raise move
	 * @return
	 */
	public boolean isBetOrRaise(){
		return isBet() || isRaise();
	}
	
	/**
	 * Returns a String representation of this action
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(player.getName()).append(" ").append(move).append(" ").append(amount);
		return sb.toString();
	}
	
	/**** Standard getters *****/
	
	public Integer getId() {
		return id;
	}
	
	public Player getPlayer() {
		return player;
	}

	public Move getMove() {
		return move;
	}
	public BigDecimal getAmount(){
		return amount;
	}
}
