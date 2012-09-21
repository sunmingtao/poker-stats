package com.smt.poker.domain.hand;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a player's move
 * e.g. folds, checks, bets, calls, raises, posts (for blinds)
 * @author Mingtao Sun
 */
public enum Move {
	FOLD {
		@Override
		public String toString() {
			return FOLDS_STR;
		}
		@Override
		public boolean hasAmount() {
			return false;
		}
	}, 
	CHECK {
		@Override
		public String toString() {
			return CHECKS_STR;
		}

		@Override
		public boolean hasAmount() {
			return false;
		}
	}, 
	BET {
		@Override
		public String toString() {
			return BETS_STR;
		}
		@Override
		public boolean hasAmount() {
			return true;
		}
	}, 
	CALL {
		@Override
		public String toString() {
			return CALLS_STR;
		}
		@Override
		public boolean hasAmount() {
			return true;
		}
	}, 
	RAISE {
		@Override
		public String toString() {
			return RAISES_STR;
		}
		@Override
		public boolean hasAmount() {
			return true;
		}
	},
	POST {
		@Override
		public String toString() {
			return POSTS_STR;
		}
		@Override
		public boolean hasAmount() {
			return true;
		}
	},
	WIN {
		@Override
		public String toString() {
			return WINS_STR;
		}
		@Override
		public boolean hasAmount() {
			return true;
		}
	},
	UNCALL{
		@Override
		public String toString() {
			return UNCALLS_STR;
		}
		@Override
		public boolean hasAmount() {
			return true;
		}
	};
	
	private static final String FOLD_STR = "fold";
	private static final String CHECK_STR = "check";
	private static final String BET_STR = "bet";
	private static final String CALL_STR = "call";
	private static final String RAISE_STR = "raise";
	private static final String POST_STR = "post";
	public static final String WON_STR = "won";
	public static final String COLLECTED_STR = "collected";
	private static final String UNCALLED_STR = "Uncalled";
	
	public static final String FOLDS_STR = "folds";
	public static final String CHECKS_STR = "checks";
	public static final String BETS_STR = "bets";
	public static final String CALLS_STR = "calls";
	public static final String RAISES_STR = "raises";
	public static final String POSTS_STR = "posts";
	public static final String WINS_STR = "wins";
	public static final String UNCALLS_STR = "uncalls";
	
	public static Move forName(String moveName){
		Move move;
		if (moveName.toLowerCase().contains(FOLD_STR)){
			move = FOLD;
		}else if (moveName.toLowerCase().contains(CHECK_STR)){
			move = CHECK;
		}else if (moveName.toLowerCase().contains(BET_STR)){
			move = BET;
		}else if (moveName.toLowerCase().contains(CALL_STR)){
			move = CALL;
		}else if (moveName.toLowerCase().contains(RAISE_STR)){
			move = RAISE;
		}else if (moveName.toLowerCase().contains(POST_STR)){
			move = POST;
		}else if (moveName.toLowerCase().contains(COLLECTED_STR) 
				|| moveName.toLowerCase().contains(WON_STR)){
			move = WIN;
		}else if (moveName.toLowerCase().contains(UNCALLED_STR)){
			move = UNCALL;
		}else{
			throw new IllegalArgumentException("Move name is illagal: "+moveName);
		}
		return move;
	}
	
	
	/**
	 * Checks if the specified string contains a valid move
	 * including fold/check/bet/call/raise/post/uncall
	 * @param str
	 * @return
	 */
	public static boolean containsValidMove(String str){
		return StringUtils.isNotBlank(str) && 
				(str.contains(FOLDS_STR) 
				|| str.contains(CALLS_STR) 
				|| str.contains(BETS_STR)
				|| str.contains(CHECKS_STR)
				|| str.contains(RAISES_STR)
				|| str.contains(POSTS_STR));
	}
	
	/**
	 * Checks if the specified string contains an 'Uncall' move
	 * @param str
	 * @return
	 */
	public static boolean containsUncallMove(String str){
		return StringUtils.isNotBlank(str) && str.contains(UNCALLED_STR);
	}
	
	/**
	 * Checks if the specified string contains a valid win pot move
	 * including won/collected
	 * @param str
	 * @return
	 */
	public static boolean containsWinPotValidMove(String str){
		return StringUtils.isNotBlank(str) && 
			(str.contains(WON_STR) || str.contains(COLLECTED_STR));
	}
	
	/**
	 * Checks if the move should be associated with a positive amount
	 * e.g. fold/check has no associated amount
	 * bet/raise/call/post(blind) has
	 * @return
	 */
	public abstract boolean hasAmount();
	
}
