package com.smt.poker.persistence.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="T_HAND")
public class Hand {
	/** Database id */
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="HAND_GENERATOR")
	@TableGenerator(name="HAND_GENERATOR", initialValue=1, allocationSize=1)
	private Integer id;
	
	/** Number of the hand */
	@Column(name="NUMBER", nullable=false, unique=true)
	private String number;
	
	/** Date and time of the hand */
	@Column(name="DATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	
	/** An array of players that participate in the hand */
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
	      name="T_HAND_PLAYER",
	      joinColumns={@JoinColumn(name="HAND_ID", referencedColumnName="ID")},
	      inverseJoinColumns={@JoinColumn(name="PLAYER_ID", referencedColumnName="ID")})
	@OrderColumn
	private List<Player> players = new ArrayList<Player>();
	
	/** Button player name */
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="F_BUTTON_PLAYER_ID")
	private Player buttonPlayer;
	
	/** Rake amount */
	@Column(name="RAKE", precision=5, scale=2)
	private BigDecimal rake;
	
	/** Small blind amount */
	@Column(name="SMALL_BLIND", precision=5, scale=2)
	private BigDecimal smallBlind;
	
	/** Big blind amount */
	@Column(name="BIG_BLIND", precision=5, scale=2)
	private BigDecimal bigBlind;

	public Hand(String number) {
		super();
		this.number = number;
	}
	
	public Hand() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getButtonPlayer() {
		return buttonPlayer;
	}

	public BigDecimal getRake() {
		return rake;
	}

	public BigDecimal getSmallBlind() {
		return smallBlind;
	}

	public BigDecimal getBigBlind() {
		return bigBlind;
	}
}
