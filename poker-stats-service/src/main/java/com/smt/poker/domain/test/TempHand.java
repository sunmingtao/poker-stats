package com.smt.poker.domain.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.smt.poker.domain.hand.Player;

@Entity
@Table(name="T_HAND")
public class TempHand {
	@Id
	private final String id;
	@Column(name="DATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private final Date dateTime;
	
	/** Small blind amount */
	@Column(name="SMALL_BLIND", precision=5, scale=2)
	private final BigDecimal smallBlind;
	
	/** Big blind amount */
	@Column(name="BIG_BLIND", precision=5, scale=2)
	private final BigDecimal bigBlind;
	
	private Player[] players;
	
	public TempHand(String id, Date dateTime, BigDecimal smallBlind, BigDecimal bigBlind) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
	}

	public String getId() {
		return id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public BigDecimal getSmallBlind() {
		return smallBlind;
	}

	public BigDecimal getBigBlind() {
		return bigBlind;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
}
