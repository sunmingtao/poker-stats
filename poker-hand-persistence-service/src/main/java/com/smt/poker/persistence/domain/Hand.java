package com.smt.poker.persistence.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;



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
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinTable(
	      name="T_HAND_PLAYER",
	      joinColumns={@JoinColumn(name="HAND_ID", referencedColumnName="ID")},
	      inverseJoinColumns={@JoinColumn(name="PLAYER_ID", referencedColumnName="ID")})
	@OrderColumn
	private Set<Player> players = null;
	
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

	@OneToMany(cascade={CascadeType.MERGE}, mappedBy="hand", fetch=FetchType.EAGER)
	private Set<Action> actions = new HashSet<Action>();
	
	@OneToMany(cascade={CascadeType.MERGE}, mappedBy="hand", fetch=FetchType.EAGER)
	private Set<Winner> winners = new HashSet<Winner>();
	
	public Hand(String number) {
		super();
		this.number = number;
	}
	
	public Hand() {
		super();
	}

	
	public Hand(String number, Date dateTime, BigDecimal smallBlind,
			BigDecimal bigBlind) {
		this.number = number;
		this.dateTime = dateTime;
		this.bigBlind = bigBlind;
		this.smallBlind = smallBlind;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public void setButtonPlayer(Player buttonPlayer){
		this.buttonPlayer = buttonPlayer;
	}
	
	private void addAction(Action action, Street street){
		if (action != null){
			action.setStreet(street);
			action.setHand(this);
			actions.add(action);	
		}
	}
	
	public void addWinner(Winner winner){
		if (winner != null){
			winner.setHand(this);
			winners.add(winner);	
		}
	}
	
	public Player getPlayer(String playerName){
		Player result = null;
		for (Player player: players){
			if (StringUtils.equals(player.getName(), playerName)){
				result = player;
			}
		}
		return result;
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

	public Set<Player> getPlayers() {
		return players;
	}
	
	public List<String> getPlayerNames() {
		List<String> playerNames = new ArrayList<String>();
		for (Player player: players){
			playerNames.add(player.getName());	
		}
		return playerNames;
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
	
	public Set<Action> getActions() {
		return actions;
	}
	
	public Set<Winner> getWinners() {
		return winners;
	}

	public BigDecimal getBigBlind() {
		return bigBlind;
	}
	
	public void setRake(BigDecimal rake) {
		this.rake = rake;
	}

	public void addPreflopAction(Action action) {
		addAction(action, Street.PREFLOP);
	}

	public void addFlopAction(Action action) {
		addAction(action, Street.FLOP);
	}

	public void addTurnAction(Action action) {
		addAction(action, Street.TURN);
	}

	public void addRiverAction(Action action) {
		addAction(action, Street.RIVER);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("Hand Number: "+number);
		sb.append(" Small/Big Blind:").append(smallBlind).append("/").append(bigBlind);
		sb.append(" Date time:").append(dateTime);
		//Player names
		sb.append("\nPlayers: ");
		for (Player player: players){
			sb.append(player.getName()).append(" / ");
		}
		//Preflop actions
		sb.append("\nPreflop actions:\n");
		for (Action action: actions){
			if (action.getStreet() == Street.PREFLOP){
				sb.append(action.toString()).append("\n");	
			}
		}
		//Flop actions
		sb.append("\nFlop actions:\n");
		for (Action action: actions){
			if (action.getStreet() == Street.FLOP){
				sb.append(action.toString()).append("\n");	
			}
		}
		//Turn actions
		sb.append("\nTurn actions:\n");
		for (Action action: actions){
			if (action.getStreet() == Street.TURN){
				sb.append(action.toString()).append("\n");	
			}
		}
		//River actions
		sb.append("\nRiver actions:\n");
		for (Action action: actions){
			if (action.getStreet() == Street.RIVER){
				sb.append(action.toString()).append("\n");	
			}
		}
		//Winner
		sb.append("\nThe winner is....:\n");
		for (Winner winner: winners){
			sb.append(winner.toString()).append("\n");	
		}
		//Rake
		sb.append("The rake is ").append(rake).append("\n");
		return sb.toString();
	}
}
