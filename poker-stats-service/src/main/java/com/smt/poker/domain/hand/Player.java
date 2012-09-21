package com.smt.poker.domain.hand;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.smt.poker.util.BigDecimalUtils;

/**
 * Represents a player
 * @author Mingtao Sun
 */
@Entity
@Table(name="T_PLAYER")
public class Player {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="PLAYER_GENERATOR")
	@TableGenerator(name="PLAYER_GENERATOR", initialValue=1, allocationSize=1)
	private BigInteger id;
	
	/** Player's name */
	@Column(name="NAME")
	private final String name;
	
	/** Amount paid at preflop */
	@Column(name="PREFLOP_AMOUNT", precision=5, scale=2)
	private BigDecimal preflopAmount = BigDecimal.ZERO;
	
	/** Amount paid at flop */
	@Column(name="FLOP_AMOUNT", precision=5, scale=2)
	private BigDecimal flopAmount = BigDecimal.ZERO;
	
	/** Amount paid at turn */
	@Column(name="TURN_AMOUNT", precision=5, scale=2)
	private BigDecimal turnAmount = BigDecimal.ZERO;
	
	/** Amount paid at river */
	@Column(name="RIVER_AMOUNT", precision=5, scale=2)
	private BigDecimal riverAmount = BigDecimal.ZERO;
	
	/** Amount collected from pot */
	@Column(name="WIN_AMOUNT", precision=5, scale=2)
	private BigDecimal winAmount = BigDecimal.ZERO;
	
	/**
	 * Default constructor
	 * @param name
	 */
	public Player(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Adds an amount to total amount paid at preflop
	 * @param amount
	 */
	public void addPreflopAmount(BigDecimal amount) {
		this.preflopAmount = BigDecimalUtils.add(this.preflopAmount, amount);
	}
	
	/**
	 * Substracts an amount to total amount paid at preflop
	 * @param amount
	 */
	public void substractPreflopAmount(BigDecimal amount) {
		this.preflopAmount = BigDecimalUtils.substract(this.preflopAmount, amount);
	}
	
	/**
	 * Adds an amount to total amount paid at flop
	 * @param amount
	 */
	public void addFlopAmount(BigDecimal amount) {
		this.flopAmount = BigDecimalUtils.add(this.flopAmount, amount);
	}
	
	/**
	 * Substracts an amount to total amount paid at flop
	 * @param amount
	 */
	public void substractFlopAmount(BigDecimal amount) {
		this.flopAmount = BigDecimalUtils.substract(this.flopAmount, amount);
	}
	
	/**
	 * Adds an amount to total amount paid at turn
	 * @param amount
	 */
	public void addTurnAmount(BigDecimal amount) {
		this.turnAmount = BigDecimalUtils.add(this.turnAmount, amount);
	}
	
	/**
	 * Substracts an amount to total amount paid at turn
	 * @param amount
	 */
	public void substractTurnAmount(BigDecimal amount) {
		this.turnAmount = BigDecimalUtils.substract(this.turnAmount, amount);
	}
	
	/**
	 * Adds an amount to total amount paid at river
	 * @param amount
	 */
	public void addRiverAmount(BigDecimal amount) {
		this.riverAmount = BigDecimalUtils.add(this.riverAmount, amount);
	}
	
	/**
	 * Substracts an amount to total amount paid at river
	 * @param amount
	 */
	public void substractRiverAmount(BigDecimal amount) {
		this.riverAmount = BigDecimalUtils.substract(this.riverAmount, amount);
	}
	
	/**
	 * Calculates the profit in the hand
	 * @return
	 */
	public BigDecimal getProfit() {
		//Total paid = blind + preflop + flop + turn + river
		BigDecimal totalPaidAmount = preflopAmount.add(flopAmount)
			.add(turnAmount).add(riverAmount);
		return BigDecimalUtils.substract(winAmount, totalPaidAmount);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(", ");
		sb.append("preflop paid: ").append(preflopAmount).append(", ");
		sb.append("flop paid: ").append(flopAmount).append(", ");
		sb.append("turn paid: ").append(turnAmount).append(", ");
		sb.append("river paid: ").append(riverAmount).append(", ");
		sb.append("win amount: ").append(winAmount).append(", ");
		sb.append("total profit: ").append(getProfit());
		return sb.toString();
	}
	
	/************************ Standard Getters *************************/
	public String getName() {
		return name;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigDecimal getPreflopAmount() {
		return preflopAmount;
	}

	public void setPreflopAmount(BigDecimal preflopAmount) {
		this.preflopAmount = preflopAmount;
	}

	public BigDecimal getFlopAmount() {
		return flopAmount;
	}

	public void setFlopAmount(BigDecimal flopAmount) {
		this.flopAmount = flopAmount;
	}

	public BigDecimal getTurnAmount() {
		return turnAmount;
	}

	public void setTurnAmount(BigDecimal turnAmount) {
		this.turnAmount = turnAmount;
	}

	public BigDecimal getRiverAmount() {
		return riverAmount;
	}

	public void setRiverAmount(BigDecimal riverAmount) {
		this.riverAmount = riverAmount;
	}

	public BigDecimal getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}

}
