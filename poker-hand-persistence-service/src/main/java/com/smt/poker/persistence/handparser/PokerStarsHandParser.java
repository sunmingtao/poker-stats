package com.smt.poker.persistence.handparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.smt.poker.persistence.domain.Action;
import com.smt.poker.persistence.domain.Hand;
import com.smt.poker.persistence.domain.Move;
import com.smt.poker.persistence.domain.Player;
import com.smt.poker.persistence.domain.Winner;
import com.smt.poker.util.AmountUtils;

/**
 * The hand parser for PokerStars.com
 * 
 * Here is an example of a hand 
 * 
 * PokerStars Hand #78891241091:  Hold'em No Limit ($0.05/$0.10 USD) - 2012/04/15 5:45:22 ET
 * Table 'Kriemhild IV' 9-max Seat #9 is the button
 * Seat 1: westhamas ($9.85 in chips) 
 * Seat 2: RuslanX95 ($6.05 in chips) 
 * Seat 3: SmithWR ($17.28 in chips) 
 * Seat 4: eg.Denzel ($9.84 in chips) 
 * Seat 5: pike773 ($13.35 in chips) 
 * Seat 6: oozywaters ($11.49 in chips) 
 * Seat 7: fasa1971 ($5 in chips) 
 * Seat 8: sunmt ($9.85 in chips) 
 * Seat 9: Dr.Butcher23 ($16.29 in chips) 
 * westhamas: posts small blind $0.05
 * RuslanX95: posts big blind $0.10
 * *** HOLE CARDS ***
 * Dealt to sunmt [Ah 3c]
 * SmithWR: folds 
 * eg.Denzel: folds 
 * pike773: folds 
 * oozywaters: raises $0.30 to $0.40
 * fasa1971: raises $0.80 to $1.20
 * sunmt: folds 
 * Dr.Butcher23: folds 
 * westhamas: folds 
 * RuslanX95: folds 
 * oozywaters: raises $2.80 to $4
 * fasa1971: raises $1 to $5 and is all-in
 * oozywaters: calls $1
 * *** FLOP *** [6h Kd 5d]
 * *** TURN *** [6h Kd 5d] [Ts]
 * *** RIVER *** [6h Kd 5d Ts] [Th]
 * *** SHOW DOWN ***
 * oozywaters: shows [Kc Kh] (a full house, Kings full of Tens)
 * fasa1971: mucks hand 
 * oozywaters collected $9.69 from pot
 * *** SUMMARY ***
 * Total pot $10.15 | Rake $0.46 
 * Board [6h Kd 5d Ts Th]
 * Seat 1: westhamas (small blind) folded before Flop
 * Seat 2: RuslanX95 (big blind) folded before Flop
 * Seat 3: SmithWR folded before Flop (didn't bet)
 * Seat 4: eg.Denzel folded before Flop (didn't bet)
 * Seat 5: pike773 folded before Flop (didn't bet)
 * Seat 6: oozywaters showed [Kc Kh] and won ($9.69) with a full house, Kings full of Tens
 * Seat 7: fasa1971 mucked [Ks Ad]
 * Seat 8: sunmt folded before Flop (didn't bet)
 * Seat 9: Dr.Butcher23 (button) folded before Flop (didn't bet)
 * 
 * @author Mingtao Sun
 */
@Component("pokerStarsHandParser")
public class PokerStarsHandParser implements HandParser{

	/** A new line */
	private static final String NEW_LINE = "\n";
	/** PokerStars */
	private static final String POKERSTARS = "PokerStars";
	/** Seat */
	private static final String SEAT = "Seat";
	/** Hash (#) */
	private static final String HASH = "#";
	/** Colon (:) */
	private static final String COLON = ":";
	/** Seat # */
	private static final String SEAT_HASH = "Seat #";
	/** Seat  */
	private static final String SEAT_SPACE = "Seat ";
	/** Left Parenthesis and dollar */
	private static final String LEFT_PARENTHESIS_DOLLAR = "($";
	/** Slash and dollar */
	private static final String SLASH_DOLLAR = "/$";
	/** Right Parenthesis and space */
	private static final String RIGHT_PARENTHESIS_SPACE = ") ";
	/** Dollar */
	private static final String DOLLAR = "$";
	/** Rake */
	private static final String RAKE = "Rake";
	/** Returned to */
	private static final String RETURNED_TO = "returned to";
	/** Said */
	private static final String SAID = "said";
	/** Posts small & big blinds */
	private static final String POSTS_SMALL_BIG_BLINDS = "posts small & big blinds";
	/** Regular expression for splitting the amount out of a string */
	private static final String AMOUNT_REG = "[ )]";
	/** Hole cards asteriscs */
	private static final String HOLE_CARDS_ASTERISCS = "*** HOLE CARDS ***";
	/** Flop asteriscs */
	private static final String FLOP_ASTERISCS = "*** FLOP ***";
	/** Turn asteriscs */
	private static final String TURN_ASTERISCS = "*** TURN ***";
	/** River asteriscs */
	private static final String RIVER_ASTERISCS = "*** RIVER ***";
	/** Show down asteriscs */
	private static final String SHOWDOWN_ASTERISCS = "*** SHOW DOWN ***";
	/** Summary asteriscs */
	private static final String SUMMARY_ASTERISCS = "*** SUMMARY ***";
	private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d\\d\\d\\d/\\d\\d/\\d\\d \\d\\d:\\d\\d:\\d\\d");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public List<Hand> parseHands(File file) throws IOException {
		List<Hand> hands = new ArrayList<Hand>();
		BufferedReader in = new BufferedReader(new FileReader(file));
		String nextHand = null;
		//Read till the end of the file
		while ((nextHand = readNextHand(in)) != null){
			hands.add(parseHand(nextHand));
		}
		return hands;
	}

	private String readNextHand(BufferedReader in) throws IOException {
		String line = readNextNonEmptyLine(in);
		if (line != null){
			StringBuilder sb = new StringBuilder();
			//Put all the non empty lines to the StringBuilder
			while (!StringUtils.isBlank(line)){
				sb.append(line).append(NEW_LINE);
				line = in.readLine();
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * Returns the next non empty line in an instance of 
	 * <code>BufferedReader</code>
	 * Or returns null if reaching the end of the 
	 * <code>BufferedReader</code>
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private String readNextNonEmptyLine(BufferedReader in) throws IOException {
		String line = in.readLine();
		while (line != null && StringUtils.isBlank(line)){
			line = in.readLine();
		}
		return line;
	}

	private Hand parseHand(String str) {
		String[] lines = str.split(NEW_LINE);
		int currentLineNum = getHandFirstLineNumber(lines);
		String firstLine = lines[currentLineNum];
		//The 1st line contains hand ID/date time/Small blind/Big Blind
		Hand hand = new Hand(getHandId(firstLine), 
				getDateTime(firstLine),
				getSmallBlind(firstLine), 
				getBigBlind(firstLine));
		currentLineNum++;
		//The 2nd line contains the seat number of the button
		int buttonSeatNumber = getButtonSeatNumber(lines[currentLineNum++]);
		//From the 3rd line to the line that start with "Seat"
		//These lines contain the players name
		Set<Player> players = new HashSet<Player>();
		while(lines[currentLineNum].startsWith(SEAT)){
			Player player = new Player(getPlayerName(lines[currentLineNum]));
			//Find who the button is
			if (getSeatNumber(lines[currentLineNum]) == buttonSeatNumber){
				hand.setButtonPlayer(player);
			}
			players.add(player);
			currentLineNum++;
		}
		hand.setPlayers(players);
		//Next few lines until *** HOLE CARDS *** 
		//are posting small and big blind, sitting out, coming back, etc
		//e.g. westhamas: posts small blind $0.05
		//     RuslanX95: posts big blind $0.10
		//     sharequest88: posts small & big blinds $0.15
		//     oozywaters: is sitting out 
		//     oozywaters leaves the table
		while (!lines[currentLineNum].trim().equalsIgnoreCase(HOLE_CARDS_ASTERISCS)){
			hand.addPreflopAction(getAction(lines[currentLineNum], players));
			currentLineNum++;
		}
		//Next line is *** HOLE CARDS ***
		currentLineNum++;
		//Next line is the hole cards dealt to myself
		//e.g. Dealt to sunmt [Ah 3c]
		//Not interested now
		currentLineNum++;
		//From the next line until the line the next stree starts
		//These lines are preflop actions
		while (!isNextStreeStart(lines[currentLineNum].trim())){
			hand.addPreflopAction(getAction(lines[currentLineNum++], players));
		}
		//If there is a flop
		if (lines[currentLineNum].contains(FLOP_ASTERISCS)){
			currentLineNum++;
			//From the next line until the line that starts with ***
			//These lines are flop actions
			while (!isNextStreeStart(lines[currentLineNum].trim())){
				hand.addFlopAction(getAction(lines[currentLineNum++], players));
			}
			//If there is a turn
			if (lines[currentLineNum].contains(TURN_ASTERISCS)){
				currentLineNum++;
				//From the next line until the line that starts with ***
				//These lines are turn actions
				while (!isNextStreeStart(lines[currentLineNum].trim())){
					hand.addTurnAction(getAction(lines[currentLineNum++], players));
				}
				//If there is a river
				if (lines[currentLineNum].contains(RIVER_ASTERISCS)){
					currentLineNum++;
					//From the next line until the line that starts with ***
					//These lines are turn actions
					while (!isNextStreeStart(lines[currentLineNum].trim())){
						hand.addRiverAction(getAction(lines[currentLineNum++], players));
					}
				}
			}
		}
		//Jump to *** SUMMARY ***
		while (!lines[currentLineNum].contains(SUMMARY_ASTERISCS)){
			currentLineNum++;
		}
		//Next line is *** SUMMARY ***
		currentLineNum++;
		//Next line is total pot and rake
		//e.g. Total pot $0.10 | Rake $0 
		// Total pot $29.70 Main pot $17.09. Side pot $11.27. | Rake $1.34 
		hand.setRake(getRake((lines[currentLineNum++])));
		//The rest is a summary of the hand
		//e.g. Seat 1: scav3 (button) folded before Flop (didn't bet)
		//Seat 3: BCXNSCD (small blind) folded before Flop
		//Seat 4: sunmt (big blind) collected ($0.30)
		//Seat 5: pplayer83 folded before Flop (didn't bet)
		//Seat 7: DkH.StaN folded before Flop (didn't bet)
		//Seat 8: Piter3m folded before Flop (didn't bet)
		//Seat 9: Schmiddi83 folded before Flop
		// The player who wins the pot and how much is won are the information
		// I am interested in 
		while (currentLineNum < lines.length){
			hand.addWinner(getWinners(lines[currentLineNum++], players));
		}
		System.out.println(hand);
		return hand;
	}
	
	/**
	 * Returns the first line number of the hand
	 * e.g PokerStars Hand #78891241091:  
	 *     Hold'em No Limit ($0.05/$0.10 USD) - 2012/04/15 5:45:22 ET
	 * It is usually line 0. However, when the hand history is obtained 
	 * through email, it has extra line(s), such as
     * *********** # 185 **************
	 * @param lines
	 * @return
	 */
	private int getHandFirstLineNumber(String[] lines){
		int currentLineNum = 0;
		while(!lines[currentLineNum].contains(POKERSTARS)){
			currentLineNum++;
		}	
		return currentLineNum;
	}
	
	/**
	 * Checks if the next street (flop/turn/river/showdown/summary) starts
	 * @param line
	 * @return
	 */
	private boolean isNextStreeStart(String line){
		return line.contains(FLOP_ASTERISCS) ||
				line.contains(TURN_ASTERISCS) ||
				line.contains(RIVER_ASTERISCS) ||
				SHOWDOWN_ASTERISCS.equalsIgnoreCase(line) ||
				SUMMARY_ASTERISCS.equalsIgnoreCase(line);
	}
	
	/**
	 * Assembles the <code>Action</code> object from the specified string
	 * @param str
	 * @param playerNames
	 * @return
	 */
	private Action getAction(String str, Set<Player> players) {
		//Here is an example of input string:
		//westhamas: folds 
		//oozywaters: raises $0.20 to $0.30
		//sunmt: posts big blind $0.10
		//yura6775: bets $1.40
		//sunmt: checks
		//sunmt: calls $0.60
		//Uncalled bet ($0.20) returned to oozywaters
		//oozywaters collected $0.25 from pot
		Action action = null;
		//Checks if the line is a valid action
		//Can only be folds/calls/bets/checks/raises/posts 
		if (isValidAction(str)){
			try {
				Player player = getPlayer(str, players);
				Move move = getMove(str);
				BigDecimal amount = getAmount(str, move);
				action = new Action(player, move, amount);
			} catch (RuntimeException e) {
				//If encounter any unexpected input
				System.out.println(str);
				e.printStackTrace();
				//throw e;
			}
		}else if(isValidUncallAction(str)){
			Player player = getPlayer(str, players);
			int index = str.indexOf(RETURNED_TO);
			//Trim the player name in case the name contains '$' sign
			String noPlayerNameStr = str.substring(0, index);
			BigDecimal amount = getAmount(noPlayerNameStr, Move.UNCALL);
			action = new Action(player, Move.UNCALL, amount);
		}
		return action;
	}
	
	/**
	 * Extracts the move from the specified str
	 * @param str
	 * @return
	 */
	private Move getMove(String str) {
		return Move.forName(str.substring(str.lastIndexOf(COLON)));
	}
	
	/**
	 * Returns the amount involved in the action
	 * e.g. the amount for 'fold' and 'check' is 0
	 * the amount for 'bets $0.30' is 0.3
	 * the amount for 'calls $0.30' is 0.3
	 * the amount for 'raises $0.50 to $0.80' is 0.8
	 * the amount for 'posts big blind $0.10' is 0.1
	 * @param str
	 * @param move
	 * @return
	 */
	private BigDecimal getAmount(String str, Move move) {
		BigDecimal amount = null;
		//If move is bet/raise/call/post(blind)
		if (move.hasAmount()){
			//Look for last index of '$' sign
			//in the case of 'raises $0.50 to $0.80',
			//only the second amount is important
			int dollarIndex = str.lastIndexOf(DOLLAR);
			if (dollarIndex != -1){
				//Trim the rest of the string after the amount
				String tmp = str.substring(dollarIndex+1).trim().split(AMOUNT_REG)[0];
				try {
					amount = new BigDecimal(tmp);
				} catch (RuntimeException e) {
					System.out.println(str);
					e.printStackTrace();
					throw e;
				}
			}else{
				throw new IllegalArgumentException("$ sign cannot be found. The line is invalid: "+str);
			}
		}else{ //If move is fold/check
			amount = BigDecimal.ZERO;
		}
		return amount;
	}
	
	/**
	 * Assembles the <code>Action</code> object for win pot action
	 * from the specified string
	 * @param str
	 * @param playerNames
	 * @return
	 */
	private Winner getWinners(String str, Set<Player> players) {
		//Here is an example of input string when there is a showdown:
		//Seat 1: KentaroIhara folded before Flop (didn't bet)
		//Seat 2: Ponton110 showed [Ks Kd] and lost with a pair of Kings
		//Seat 3: jackzrj folded before Flop (didn't bet)
		//Seat 4: B.B.C.news folded before Flop (didn't bet)
		//Seat 5: ZuJIOk folded before Flop
		//Seat 6: loehry8 folded before Flop (didn't bet)
		//Seat 7: robgra689 (button) showed [Ac As] and won ($28.36) with a pair of Aces
		//Seat 8: sunmt (small blind) folded before Flop
		//Seat 9: ShogunRob (big blind) showed [Jd Jc] and lost with a pair of Jacks
		
		//example 2 when there is no showdown
		//Seat 2: Putulica007 collected ($0.25)
		//Seat 3: jackzrj folded before Flop (didn't bet)
		//Seat 4: B.B.C.news folded before Flop (didn't bet)
		//Seat 5: kartargo folded before Flop (didn't bet)
		//Seat 6: loehry8 folded before Flop (didn't bet)
		//Seat 7: robgra689 (button) folded before Flop (didn't bet)
		//Seat 8: sunmt (small blind) folded before Flop
		//Seat 9: ShogunRob (big blind) folded before Flop
		Winner winner = null;
		if (isValidWinner(str)){
			try {
				Player player = getPlayer(str, players);
				BigDecimal amount = getWinPotAmount(str);
				winner = new Winner(player, amount);
			} catch (RuntimeException e) {
				//If encounter any unexpected input
				System.out.println(str);
				e.printStackTrace();
				throw e;
			}
		}
		return winner;
	}
	
	/**
	 * Extracts the amount won from the pot 
	 * from the specified string
	 * @param str
	 * @return
	 */
	private BigDecimal getWinPotAmount(String str) {
		return getAmount(str, Move.WIN);
	}
	
	/**
	 * Checks if the specified line is a valid winner line
	 * @param str
	 * @return
	 */
	private boolean isValidWinner(String str) {
		//Check if the line has keywords 'won' or 'collected' and the '$' sign
		return str.contains(DOLLAR) && Move.containsWinPotValidMove(str);
	}
	/**
	 * Extracts who won the pot
	 * from the specified string
	 * @param str
	 * @param players
	 * @return
	 */
	private Player getPlayer(String str, Set<Player> players) {
		//Here is an example of input string:
		//Seat 2: Putulica007 collected ($0.25)
		//Seat 7: robg ra689 (button) showed [Ac As] and won ($28.36) with a pair of Aces
		Player result = null;
		for (Player player: players){
			if (str.contains(player.getName())){
				result = player;
				break;
			}
		}
		if (result == null){
			throw new IllegalArgumentException("player name won't be null: "+str);
		}
		return result;
	}
	/**
	 * Checks if the specified line is a valid action
	 * @param str
	 * @return
	 */
	private boolean isValidAction(String str){
		//Checks if the line is a valid action
		//Can only be folds/calls/bets/checks/raises/posts 
		//And the line contains a colon in case some player's name 
		//contains the key word: folds, calls, bets checks and raises
		boolean result = false;
		int colonIndex = str.indexOf(COLON);
		if (colonIndex > 0){
			String newStr = str.substring(colonIndex+1);
			result = Move.containsValidMove(newStr) && !str.contains(SAID);
		}
		return result;
	}
	
	/**
	 * Checks if the specified line is a valid 'Uncall' action
	 * @param str
	 * @return
	 */
	private boolean isValidUncallAction(String str){
		//Here is an example of input string:
		//Uncalled bet ($0.08) returned to huelepega26
		//Check if the line has a '$' sign, 'return', and 'uncalled',
		//unless a player's name is weird enough to contain
		//all these keywords, it's a pretty safe check
		return str.contains(DOLLAR) && str.contains(RETURNED_TO) 
			&& Move.containsUncallMove(str);
	}
	
	/**
	 * Extracts the player's seat number from the specified string
	 * @param str
	 * @return
	 */
	private int getSeatNumber(String str) {
		//Here is an example of input string:
		//Seat 1: westhamas ($9.80 in chips) 
		int index = str.indexOf(SEAT_SPACE)+5;
		return Integer.parseInt(str.substring(index, index+1));
	}
	
	/**
	 * Extracts the player's name from the specified string
	 * @param str
	 * @return
	 */
	private String getPlayerName(String str) {
		//Here is an example of input string:
		//Seat 1: westhamas ($9.80 in chips) 
		//Begin index is the index of : + 2
		int beginIndex = str.indexOf(COLON)+2;
		//End index is the index of ($ - 1
		int endIndex = str.indexOf(LEFT_PARENTHESIS_DOLLAR)-1;
		return str.substring(beginIndex, endIndex).trim();
	}
	
	/**
	 * Extracts the button seat number from the specified string
	 * @param str
	 * @return
	 */
	private int getButtonSeatNumber(String str) {
		//Here is an example of input string:
		//Table 'Kriemhild IV' 9-max Seat #9 is the button
		int index = str.indexOf(SEAT_HASH)+6;
		return Integer.parseInt(str.substring(index, index+1));
	}
	
	/**
	 * Extracts the date time from the specified string 
	 * @param str
	 * @return
	 */
	private Date getDateTime(String str) {
		Matcher m = DATE_TIME_PATTERN.matcher(str);
		Date date = null;
		if (m.find()){
			try {
				date = DATE_FORMAT.parse(m.group());
			} catch (ParseException e) {
				throw new IllegalArgumentException("The date format is incorrect: "+str);
			}
		}else{
			throw new IllegalArgumentException("The date format is incorrect: "+str);
		}
		return date;
	}

	/**
	 * Extracts the big blind amount from the specified string 
	 * @param str
	 * @return
	 */
	private BigDecimal getBigBlind(String str) {
		return AmountUtils.extractBlind(str)[1];
	}

	/**
	 * Extracts the small blind amount from the specified string 
	 * @param str
	 * @return
	 */
	private BigDecimal getSmallBlind(String str) {
		return AmountUtils.extractBlind(str)[0];
	}
	
	/**
	 * Extracts the hand Id from the specified string
	 * @param str
	 * @return
	 */
	private String getHandId(String str) {
		int startIndex = str.indexOf(HASH);
		int endIndex = str.indexOf(COLON);
		return str.substring(startIndex+1, endIndex);
	}
	
	/**
	 * Extracts the rake from the specified string
	 * @param str
	 * @return
	 */
	private BigDecimal getRake(String str) {
		//Here is an example of input string:
		//Total pot $0.90 | Rake $0.04
		int index = str.indexOf(RAKE)+4;
		//str is now $0.04
		str = str.substring(index).trim();
		//remove the '$' sign
		return new BigDecimal(str.substring(1));
	}

}
