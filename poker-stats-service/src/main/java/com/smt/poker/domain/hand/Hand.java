package com.smt.poker.domain.hand;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.smt.poker.util.BigDecimalUtils;

/**
 * Represents a hand
 * @author Mingtao Sun
 */
//@Entity
//@Table(name="T_HAND")
public class Hand {
	//@Id
	/** Id of the hand */
	private final String id;
	/** Date and time of the hand */
	private final Date dateTime;
	/** An array of players that participate in the hand */
	private Player[] players;
	/** Button player name */
	private String buttonPlayerName;
	/** Rake amount */
	private BigDecimal rake;
	/** Small blind amount */
	private final BigDecimal smallBlind;
	/** Big blind amount */
	private final BigDecimal bigBlind;
	/** A list of preflop actions */
	private List<Action> preflopActions = new ArrayList<Action>();
	/** A list of flop actions */
	private List<Action> flopActions = new ArrayList<Action>();
	/** A list of turn actions */
	private List<Action> turnActions = new ArrayList<Action>();
	/** A list of river actions */
	private List<Action> riverActions = new ArrayList<Action>();
	/** A list of win pot actions */
	private List<Action> winPotActions = new ArrayList<Action>();
	
	public Hand(String id, Date dateTime, BigDecimal smallBlind, BigDecimal bigBlind) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
	}
	
	/**
	 * Validates an action is valid
	 * @param action
	 */
	private void validateAction(Action action){
		//Checks if the player name matches those in the players array
		if (action != null && getPlayer(action.getPlayerName()) == null){
			throw new IllegalArgumentException("Player's name is invalid: "+action.getPlayerName());
		}
	}
	
	/**
	 * Adds a preflop action to the preflop action list
	 * @param action the action to add
	 */
	public void addPreflopAction(Action action){
		validateAction(action);
		if (action != null){
			preflopActions.add(action);
		}
	}
	
	/**
	 * Adds a flop action to the flop action list
	 * @param action the action to add
	 */
	public void addFlopAction(Action action){
		validateAction(action);
		if (action != null){
			flopActions.add(action);
		}
	}
	
	/**
	 * Adds a turn action to the flop action list
	 * @param action the action to add
	 */
	public void addTurnAction(Action action){
		validateAction(action);
		if (action != null){
			turnActions.add(action);
		}
	}
	
	/**
	 * Adds a river action to the flop action list
	 * @param action the action to add
	 */
	public void addRiverAction(Action action){
		validateAction(action);
		if (action != null){
			riverActions.add(action);
		}
	}
	
	/**
	 * Adds an action to the win pot action list
	 * @param action the action to add
	 */
	public void addWinPotAction(Action action){
		validateAction(action);
		if (action != null){
			winPotActions.add(action);	
		}
	}
	
	/**
	 * Sets player names and initialises the <code>Player</code> array
	 * @param playerNames
	 */
	public void setPlayerNames(String[] playerNames) {
		if (playerNames == null){
			throw new IllegalArgumentException("playerNames cannot be null");
		}
		this.players = new Player[playerNames.length];
		for (int i=0; i<playerNames.length; i++){
			this.players[i] = new Player(playerNames[i]);
		}
	}
	
	/**
	 * Returns an array of player names
	 * @return
	 */
	public String[] getPlayerNames() {
		String[] playerNames = new String[this.players.length];
		for (int i=0; i<this.players.length; i++){
			playerNames[i] = this.players[i].getName();
		}
		return playerNames;
	}
	
	/**
	 * Returns the profit of the specified player name
	 * @param playerName
	 * @return
	 */
	public BigDecimal getProfit(String playerName){
		BigDecimal amount = null;
		for (Player player: this.players){
			if (player.getName().equalsIgnoreCase(playerName)){
				amount = player.getProfit();
			}
		}
		return amount;
	}
	
	/**
	 * Calculates the profit of all players for this hand
	 */
	public void calculateProfit(){
		for (Action action: this.preflopActions){
			Player player = getPlayer(action.getPlayerName());
			BigDecimal amount = action.getAmount();
			if (action.isBetOrRaise()){
				if (hasPostExtraSmallBlind(action.getPlayerName())){
					player.setPreflopAmount(BigDecimalUtils.add(amount, smallBlind));
				}else{
					player.setPreflopAmount(amount);
				}
			}else if(action.isPost()){
				player.setPreflopAmount(amount);
			}else if (action.isCall()){
				player.addPreflopAmount(amount);
			}else if (action.isUncall()){
				player.substractPreflopAmount(amount);
			}
		}
		for (Action action: this.flopActions){
			Player player = getPlayer(action.getPlayerName());
			BigDecimal amount = action.getAmount();
			if (action.isBetOrRaise()){
				player.setFlopAmount(amount);	
			}else if (action.isCall()){
				player.addFlopAmount(amount);
			}else if (action.isUncall()){
				player.substractFlopAmount(amount);
			}
		}
		for (Action action: this.turnActions){
			Player player = getPlayer(action.getPlayerName());
			BigDecimal amount = action.getAmount();
			if (action.isBetOrRaise()){
				player.setTurnAmount(amount);	
			}else if (action.isCall()){
				player.addTurnAmount(amount);
			}else if (action.isUncall()){
				player.substractTurnAmount(amount);
			}
		}
		for (Action action: this.riverActions){
			Player player = getPlayer(action.getPlayerName());
			BigDecimal amount = action.getAmount();
			if (action.isBetOrRaise()){
				player.setRiverAmount(amount);	
			}else if (action.isCall()){
				player.addRiverAmount(amount);
			}else if (action.isUncall()){
				player.substractRiverAmount(amount);
			}
		}
		for (Action action: this.winPotActions){
			Player player = getPlayer(action.getPlayerName());
			player.setWinAmount(action.getAmount());
		}
		BigDecimal total = rake;
		for (Player player: players){
			total = BigDecimalUtils.add(total, player.getProfit());
		}
		if (total.compareTo(BigDecimal.ZERO) != 0){
			System.out.println(this);
			throw new IllegalArgumentException("Total amount must be 0: "+total);
		}
	}
	
	/**
	 * Checks the the specified player name has posted extra small blind
	 * e.g. Player sunmt posts small & big blinds
	 * @param playerName
	 * @return
	 */
	private boolean hasPostExtraSmallBlind(String playerName) {
		boolean result = false;
		if (preflopActions.size() >=2){
			for (int i=2; i<preflopActions.size(); i++){
				Action action = preflopActions.get(i);
				if (action.isPost() && action.performedBy(playerName)){
					//The posted amount is equal to small blind or (small blind+big blind)
					if (BigDecimalUtils.equal(action.getAmount(), smallBlind)
						|| BigDecimalUtils.equal(action.getAmount(), 
								BigDecimalUtils.add(smallBlind, bigBlind))){
						result = true;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returns an instance of <code>Player</code> by the specified player name
	 * from the players array
	 * @param playerName
	 * @return
	 */
	private Player getPlayer(String playerName){
		Player result = null;
		for (Player player: this.players){
			if (player.getName().equalsIgnoreCase(playerName)){
				result = player;
			}
		}
		return result;
	}
	
	/** 
	 * Checks if the player has voluntarily put money in the pot on preflop
	 * in the specified hand
	 * @param playerName
	 * @return
	 */
	public boolean isVpip(String playerName){
		boolean result = false;
		for (Action action: preflopActions){
			//Any call or raise means the player is willing to put the money
			//into pot
			//A check at the Big Blind, however, doesn't mean player voluntarily
			//put money into pot
			if (action.performedBy(playerName) && action.isCallOrRaise()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/** 
	 * Checks if the player has raised preflop
	 * in the specified hand
	 * @param playerName
	 * @return
	 */
	public boolean isPfr(String playerName){
		boolean result = false;
		for (Action action: preflopActions){
			if (action.performedBy(playerName) && action.isRaise()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Returns the preflop aggressor (the last person who raises)
	 * @return
	 */
	public String preflopAggressor(){
		String playerName = null;
		for (Action action: preflopActions){
			if (action.isRaise()){
				playerName = action.getPlayerName();
			}
		}
		return playerName;
	}
	
	/**
	 * Checks if the specified player name is the preflop aggressor
	 * @param playerName
	 * @return
	 */
	public boolean isPreflopAggressor(String playerName){
		return playerName.equalsIgnoreCase(preflopAggressor());
	}
	
	/**
	 * Returns the first player who raises preflop
	 * @return
	 */
	public String preflopOpenRaiser(){
		String playerName = null;
		for (Action action: preflopActions){
			if (action.isRaise()){
				playerName = action.getPlayerName();
				break;
			}
		}
		return playerName;
	}
	
	/**
	 * Checks if the specified player name is the preflop open raiser
	 * (the first player to raise)
	 * @param playerName
	 * @return
	 */
	private boolean isPreflopOpenRaiser(String playerName){
		return playerName.equalsIgnoreCase(preflopOpenRaiser());
	}
	
	/**
	 * Checks if the specified player name has the chance to steal
	 * (No one has called or raised before him. In other words,
	 * it has been folded to the player)
	 * @param playerName
	 * @return
	 */
	public boolean hasStealChance(String playerName){
		boolean calledOrRaised = false;
		for (Action action: preflopActions){
			if (action.performedBy(playerName) && !action.isPost()){
				break;
			}else if (action.isCallOrRaise()){
				calledOrRaised = true;
			}
		}
		return !calledOrRaised;
	}
	
	/**
	 * Checks if the specified player name tries to steal at button position
	 * (It has been folded to the player)
	 * @param playerName
	 * @return
	 */
	public boolean isButtonStealer(String playerName){
		return hasButtonStealChance(playerName)
			&& isPreflopOpenRaiser(playerName);
	}
	
	/**
	 * Checks if the specified player name has the chance to steal at button
	 * (It has been folded to the player)
	 * @param playerName
	 * @return
	 */
	public boolean hasButtonStealChance(String playerName){
		return hasStealChance(playerName) && isButton(playerName);
	}
	
	/**
	 * Checks if the specified player name attempts to steal at cutoff
	 * (It has been folded to the player)
	 * @param playerName
	 * @return
	 */
	public boolean isCutoffStealer(String playerName){
		return hasCutoffStealChance(playerName) && isPreflopOpenRaiser(playerName);
	}
	
	/**
	 * Checks if the specified player name has the chance to steal at cutoff
	 * (It has been folded to the player)
	 * @param playerName
	 * @return
	 */
	public boolean hasCutoffStealChance(String playerName){
		return isCutoff(playerName) && hasStealChance(playerName);
	}
	
	/**
	 * Checks if the specified player name attemps to steal at small blind
	 * (the first player to raise)
	 * @param playerName
	 * @return
	 */
	public boolean isSmallBlindStealer(String playerName){
		return hasSmallBlindStealChance(playerName) && isPreflopOpenRaiser(playerName);
	}
	
	/**
	 * Checks if the specified player name has the chance to open raise at small blind
	 * @param playerName
	 * @return
	 */
	public boolean hasSmallBlindStealChance(String playerName){
		return hasStealChance(playerName) && isSmallBlind(playerName);
	}
	
	/**
	 * Checks if the player sees the flop as the preflop aggressor
	 * @param playerName
	 * @return
	 */
	public boolean isSeeingFlopAsAggressor(String playerName) {
		//If the player is the preflop aggressor and
		//there are flop actions
		return (isPreflopAggressor(playerName) && seesFlop(playerName)); 
	}
	
	/**
	 * Checks if the player has the chance to continuation bet
	 * i.e. no one else donk bets before him
	 * @param playerName
	 * @return
	 */
	public boolean hasCBetChance(String playerName){
		boolean result = false;
		if (isSeeingFlopAsAggressor(playerName)){
			boolean donkBet = false;
			for (Action action: flopActions){
				if (!action.performedBy(playerName) && action.isBet()){
					donkBet = true;
				}else{
					break;
				}
			}
			result = !donkBet;	
		}
		return result;
	}
	
	/**
	 * Checks if the player sees the flop not as the preflop aggressor
	 * @param playerName
	 * @return
	 */
	private boolean isSeeingFlopNotAsAggressor(String playerName) {
		//If the player is the preflop aggressor and
		//there are flop actions
		return (!isPreflopAggressor(playerName)&& seesFlop(playerName));
	}
	
	/**
	 * Checks if the player gets continuation bet by the preflop aggressor
	 * @param playerName
	 * @return
	 */
	public boolean isGettingCBet(String playerName){
		//A player other than the specified player is the continuation better
		//And the specified player sees the flop
		return StringUtils.isNotBlank(flopCBetter()) 
				&& !isCBet(playerName) && seesFlop(playerName);
	}
	
	/**
	 * Returns the player who bets on the flop
	 * @return
	 */
	public String flopBetter(){
		String playerName = null;
		for (Action action: flopActions){
			if (action.isBet()){
				playerName = action.getPlayerName();
				break;
			}
		}
		return playerName;
	}
	
	/**
	 * Returns the player who is the preflop aggressor bets on the flop
	 * @return
	 */
	public String flopCBetter(){
		//Player bets on the flop
		String flopBetter = flopBetter();
		//The preflop aggressor
		String preflopAggressor = preflopAggressor();
		String playerName = null;
		if (StringUtils.isNotBlank(flopBetter) && StringUtils.equals(flopBetter, preflopAggressor)){
			playerName = flopBetter;
		}
		return playerName;
	}
	
	/**
	 * Checks if the specified player name continuation bets on the flop
	 * @param playerName
	 * @return
	 */
	public boolean isCBet(String playerName) {
		return playerName.equalsIgnoreCase(flopCBetter());
	}
	
	/**
	 * Returns the player who three bets (re-raises) on preflop
	 * @return
	 */
	public String preflop3Better(){
		String playerName = null;
		//Number of raises
		int raiseNum = 0;
		for (Action action: preflopActions){
			//If some player raises
			if (action.isRaise()){
				//If there is exactly one raise before, then 
				//this is a 3 bet
				if (raiseNum == 1){
					playerName = action.getPlayerName();
					break;		
				}else{
					raiseNum++;	
				}
			}
		}
		return playerName;
	}
	
	
	/**
	 * Checks if the specified player name has a 3 bet chance
	 * i.e. The player faces a single raised pot so he has a chance to 3 bet
	 * @param playerName
	 * @return
	 */
	public boolean has3BetChance(String playerName){
		boolean result = false;
		//Number of raises
		int raiseNum = 0;
		for (Action action: preflopActions){
			//If some other player raises
			if (action.isRaise() &&  !action.performedBy(playerName)){
				raiseNum++;
			}
			//When it's this player's turn to move, and there is exactly 1 raise before
			if (action.performedBy(playerName) && raiseNum==1){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Checks if the specified player name 3 bets on preflop
	 * @param playerName
	 * @return
	 */
	public boolean isPreflop3Better(String playerName){
		return playerName.equalsIgnoreCase(preflop3Better());
	}
	
	/**
	 * Checks if the specified player name folds to 3 bet on preflop
	 * @param playerName
	 * @return
	 */
	public boolean isFoldingTo3BetPreflop(String playerName){
		boolean result = false;
		//Number of raises
		int raiseNum = 0;
		//If the player gets 3 bet
		if (isGetting3BetPreflop(playerName)){
			for (Action action: preflopActions){
				if (action.isRaise()){
					raiseNum++;
				}
				if (raiseNum ==2 
					&& action.performedBy(playerName) 
					&& action.isFold()){
					result = true;
					break;
				}	
			}
		}
		return result;
	}
	
	/**
	 * Checks if the specified player name gets 3 bet on prefop
	 * @param playerName
	 * @return
	 */
	public boolean isGetting3BetPreflop(String playerName){
		return isPreflopOpenRaiser(playerName) && preflop3Better() != null;
	}
	
	/**
	 * Returns the player who four bets (re-re-raises) on preflop
	 * @return
	 */
	public String preflop4Better(){
		String playerName = null;
		//Number of raises
		int raiseNum = 0;
		for (Action action: preflopActions){
			//If some player raises
			if (action.isRaise()){
				//If there are exactly two raises before, then 
				//this is a 3 bet
				if (raiseNum == 2){
					playerName = action.getPlayerName();
					break;		
				}else{
					raiseNum++;	
				}
			}
		}
		return playerName;
	}
	
	/**
	 * Checks if the specified player name 4 bets on preflop
	 * @param playerName
	 * @return
	 */
	public boolean isPreflop4Better(String playerName){
		return playerName.equalsIgnoreCase(preflop4Better());
	}
	
	/**
	 * Checks if the specified player name sees the flop
	 * (didn't fold preflop)
	 * @param playerName
	 * @return
	 */
	private boolean seesFlop(String playerName){
		boolean result = false;
		for (Action action: flopActions){
			if (action.performedBy(playerName)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Checks if the specified player name calls continuation bet on the flop
	 * @param hand
	 * @return
	 */
	public boolean isCallingCBet(String playerName) {
		boolean result = false;
		//Indicator that continuation bet happens
		boolean cBet = false;
		//If the player is not the preflop aggressor and sees the flop
		if (isSeeingFlopNotAsAggressor(playerName)){
			for (Action action: flopActions){
				//Update the Continuation bet indicator
				//The preflop aggressor bets on the flop
				if (!cBet){
					cBet = action.performedBy(preflopAggressor()) 
						&& action.isBet();	
				}
				//If continuation bet happens
				//And the player calls
				if (cBet && action.isCall() 
						&& action.performedBy(playerName)){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Checks if the specified player name has check/raise chance on flop
	 * @param playerName
	 * @return
	 */
	public boolean hasFlopCheckRaiseChance(String playerName){
		boolean result = false;
		for (Action action: flopActions){
			if (action.performedBy(playerName) && action.isCheck()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Checks if the specified player name has check/raised on flop
	 * @param playerName
	 * @return
	 */
	public boolean isFlopCheckRaise(String playerName){
		boolean result = false;
		boolean checked = false;
		for (Action action: flopActions){
			if (action.performedBy(playerName) && action.isCheck()){
				checked = true;
			}else if (checked && action.performedBy(playerName) && action.isRaise()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Returns the number of postflop calls by the specified player name
	 * @param playerName
	 * @return
	 */
	public int postflopCallNum(String playerName) {
		int result = 0;
		result += callNum(flopActions, playerName);
		result += callNum(turnActions, playerName);
		result += callNum(riverActions, playerName);
		return result;
	}
	
	/**
	 * Returns the number of calls by the specified player name
	 * in the specified action list
	 * @param actions
	 * @param playerName
	 * @return
	 */
	private int callNum(List<Action> actions, String playerName){
		int result = 0;
		for (Action action: actions){
			if (action.performedBy(playerName) && action.isCall()){
				result++;
			}
		}
		return result;
	}
	
	/**
	 * Returns the number of postflop bets/raises by the specified player name
	 * @param playerName
	 * @return
	 */
	public int postflopBetRaiseNum(String playerName) {
		int result = 0;
		result += betRaiseNum(flopActions, playerName);
		result += betRaiseNum(turnActions, playerName);
		result += betRaiseNum(riverActions, playerName);
		return result;
	}
	
	/**
	 * Returns the number of bets/raises by the specified player name
	 * in the specified action list
	 * @param actions
	 * @param playerName
	 * @return
	 */
	private int betRaiseNum(List<Action> actions, String playerName){
		int result = 0;
		for (Action action: actions){
			if (action.performedBy(playerName) && action.isBetOrRaise()){
				result++;
			}
		}
		return result;
	}
	
	/**
	 * Checks to see if the specified player name is the button
	 * @param playerName
	 * @return
	 */
	private boolean isButton(String playerName){
		return buttonPlayerName.equalsIgnoreCase(playerName);
	}
	
	/**
	 * Checks to see if the specified player name is the cutoff
	 * @param playerName
	 * @return
	 */
	private boolean isCutoff(String playerName){
		return getCutoffPlayerName().equalsIgnoreCase(playerName);
	}
	
	/**
	 * Checks to see if the specified player name is the small blind
	 * @param playerName
	 * @return
	 */
	private boolean isSmallBlind(String playerName){
		return getSmallBlindPlayerName().equalsIgnoreCase(playerName);
	}
	
	/**
	 * Returns the cutoff player name
	 * @return
	 */
	public String getCutoffPlayerName() {
		String cutoffPlayerName = null;
		String[] playerNames = getPlayerNames();
		for (int i=0; i<playerNames.length; i++){
			String playerName = playerNames[i];
			if (isButton(playerName)){
				cutoffPlayerName =  playerNames[getPrevIndex(i, playerNames.length)];
			}
		}
		if (cutoffPlayerName == null){
			throw new RuntimeException("cutoff player is null");
		}
		return cutoffPlayerName;
	}
	
	/**
	 * Returns the small blind player name
	 * @return
	 */
	public String getSmallBlindPlayerName() {
		String smallBlindPlayerName = null;
		String[] playerNames = getPlayerNames();
		for (int i=0; i<playerNames.length; i++){
			String playerName = playerNames[i];
			if (isButton(playerName)){
				smallBlindPlayerName =  playerNames[getNextIndex(i, playerNames.length)];
			}
		}
		if (smallBlindPlayerName == null){
			throw new RuntimeException("small blind player is null");
		}
		return smallBlindPlayerName;
	}

	/**
	 * Returns the big blind player name
	 * @return
	 */
	public String getBigBlindPlayerName() {
		String bigBlindPlayerName = null;
		String[] playerNames = getPlayerNames();
		for (int i=0; i<playerNames.length; i++){
			String playerName = playerNames[i];
			if (isButton(playerName)){
				bigBlindPlayerName = playerNames[getNextNextIndex(i, playerNames.length)];
			}
		}
		if (bigBlindPlayerName == null){
			throw new RuntimeException("big blind player is null");
		}
		return bigBlindPlayerName;
	}
	
	/**
	 * Returns the previous index of the specified index in an array with
	 * specified length
	 * e.g. if the index is 2, returns 1
	 * if the index is 0, returns length-1
	 * @param index
	 * @param length
	 * @return
	 */
	private int getPrevIndex(int index, int length) {
		int prevIndex = index-1;
		if (prevIndex == -1){
			prevIndex = length - 1;
		}
		return prevIndex;
	}
	
	/**
	 * Returns the next index of the specified index in an array with
	 * specified length
	 * e.g. if the index is 0, returns 1
	 * if the index is 8, and the length is 9, returns 0
	 * @param index
	 * @param length
	 * @return
	 */
	private int getNextIndex(int index, int length) {
		int nextIndex = index+1;
		if (nextIndex == length){
			nextIndex = 0;
		}
		return nextIndex;
	}
	
	/**
	 * Returns the 2nd next index of the specified index in an array with
	 * specified length
	 * e.g. if the index is 0, returns 2
	 * if the index is 8, and the length is 9, returns 1
	 * if the index is 8, and the length is 10, returns 0
	 * @param index
	 * @param length
	 * @return
	 */
	private int getNextNextIndex(int index, int length) {
		int nextNextIndex = index+2;
		if (nextNextIndex == length){
			nextNextIndex = 0;
		}else if (nextNextIndex == length+1){
			nextNextIndex = 1;
		}
		return nextNextIndex;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("Hand ID: "+id);
		//Player names
		sb.append("\nPlayers: ");
		for (String name: getPlayerNames()){
			sb.append(name).append(" / ");
		}
		//Cutoff player name
		sb.append("\nCutoff: ");
		sb.append(getCutoffPlayerName());
		//Button player name
		sb.append("\nButton: ");
		sb.append(buttonPlayerName);
		//Small blind name
		sb.append("\nSmall blind: ");
		sb.append(getSmallBlindPlayerName());
		//Big blind name
		sb.append("\nBig blind: ");
		sb.append(getBigBlindPlayerName()).append("\n");
		//Preflop actions
		sb.append("\nPreflop actions:\n");
		for (Action action: preflopActions){
			sb.append(action.toString()).append("\n");
		}
		//Flop actions
		sb.append("\nFlop actions:\n");
		for (Action action: flopActions){
			sb.append(action.toString()).append("\n");
		}
		//Turn actions
		sb.append("\nTurn actions:\n");
		for (Action action: turnActions){
			sb.append(action.toString()).append("\n");
		}
		//River actions
		sb.append("\nRiver actions:\n");
		for (Action action: riverActions){
			sb.append(action.toString()).append("\n");
		}
		//Summary
		sb.append("\nSummary:\n");
		sb.append("Rake: "+rake).append("\n");
		for (Player player: players){
			sb.append(player).append("\n");
		}
		return sb.toString();
	}
	
	/************************ Standard Getters and Setters *************************/
	public String getButtonPlayer() {
		return buttonPlayerName;
	}

	public void setButtonPlayerName(String buttonPlayerName) {
		this.buttonPlayerName = buttonPlayerName;
	}
	
	public String getId() {
		return id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setRake(BigDecimal rake) {
		this.rake = rake;
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
