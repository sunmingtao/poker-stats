package com.smt.poker.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Extract amount from a string
 * @author Mingtao Sun
 */
public class AmountUtils {
	
	private static final char DOLLAR = '$';
	private static final char DECIMAL_POINT = '.';
	private static final char MINUS_SIGN = '-';
	/**
	 * Extracts and returns the amount from a string
	 * e.g. PokerStars Hand #79256863732:  Hold'em No Limit ($0.05/$0.10 USD) - 2012/04/22 5:31:50 ET
	 * returns '{0.05, 0.1}'
	 * @param str
	 * @return
	 */
	public static BigDecimal[] extractAmount(String str){
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		if (StringUtils.isNotBlank(str)){
			boolean foundDollar = false;
			StringBuffer sb = null;
			for (int i=0; i<str.length(); i++){
				char currentChar = str.charAt(i);
				if (!foundDollar && currentChar == DOLLAR){
					foundDollar = true;
				}else if(foundDollar && isPartOfAmount(currentChar)){
					if (sb == null){
						sb = new StringBuffer();
					}
					sb.append(currentChar);
				}else if(foundDollar && !isPartOfAmount(currentChar)){
					if (currentChar != DOLLAR){
						foundDollar = false;
					}
					if (sb != null){
						result.add(new BigDecimal(sb.toString()));
						sb = null;
					}
				}
			}
			if (sb != null){
				result.add(new BigDecimal(sb.toString()));
			}
		}
		return result.toArray(new BigDecimal[]{});
	}
	
	/**
	 * Checks if a char is part of the amount
	 * e.g. 0-9, decimal point, minus sign
	 * @param ch
	 * @return
	 */
	private static boolean isPartOfAmount(char ch) {
		return Character.isDigit(ch) || ch==MINUS_SIGN || ch==DECIMAL_POINT;
	}
}
