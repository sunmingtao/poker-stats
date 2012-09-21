package com.smt.poker.util;

import java.math.BigDecimal;

/**
 * Provides utility methods to do math operations for BigDecimal objects
 * @author Mingtao Sun
 */
public class BigDecimalUtils {
	
	/**
	 * Calculates a+b. 
	 * A null value is considered zero
	 * @param a can be null
	 * @param b can be null
	 * @return
	 */
	public static BigDecimal add(BigDecimal a, BigDecimal b){
		BigDecimal result = a;
		if (a == null){
			result = BigDecimal.ZERO;
		}
		if (b != null){
			result = result.add(b);
		}
		return result;
	}
	
	/**
	 * Calculates a-b. 
	 * A null value is considered zero
	 * @param a can be null
	 * @param b can be null
	 * @return
	 */
	public static BigDecimal substract(BigDecimal a, BigDecimal b){
		BigDecimal result = a;
		if (a == null){
			result = BigDecimal.ZERO;
		}
		if (b != null){
			result = result.subtract(b);
		}
		return result;
	}
	
	/**
	 * Checks if two BigDecimal objects are equal mathmatically
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equal(BigDecimal a, BigDecimal b){
		boolean result = false;
		if (a == null && b == null){
			result = true;
		}else if (a != null && b != null){
			result = a.compareTo(b) == 0;
		}
		return result;
	}
}
