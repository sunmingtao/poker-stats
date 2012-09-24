package com.smt.poker.persistence.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="T_WINNER")
public class Winner {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="WINNER_GENERATOR")
	@TableGenerator(name="WINNER_GENERATOR", initialValue=1, allocationSize=1)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="F_PLAYER_ID")
	private Player player;
	
	@Column(name="RAKE", precision=5, scale=2)
	private BigDecimal amount; 
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="F_HAND_ID")
	private Hand hand;

	
	public Winner(){
		
	}
	
	public Winner(Player player, BigDecimal amount) {
		super();
		this.player = player;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	/**
	 * Returns a String representation of this action
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(player.getName()).append(" ").append("won").append(" ").append(amount);
		return sb.toString();
	}
}
