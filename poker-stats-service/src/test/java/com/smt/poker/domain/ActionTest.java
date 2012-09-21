package com.smt.poker.domain;

import java.math.BigDecimal;

import junit.framework.TestCase;

import com.smt.poker.domain.hand.Action;
import com.smt.poker.domain.hand.Move;

public class ActionTest extends TestCase {
	
	public void testAmountTag(){
		System.out.println(convertAmount(new BigDecimal("-123456789")));
		System.out.println(convertAmount(new BigDecimal("-12345678")));
		System.out.println(convertAmount(new BigDecimal("-1234567")));
		System.out.println(convertAmount(new BigDecimal("-123456")));
		System.out.println(convertAmount(new BigDecimal("-12345")));
		System.out.println(convertAmount(new BigDecimal("-1234")));
		System.out.println(convertAmount(new BigDecimal("-123")));
		System.out.println(convertAmount(new BigDecimal("-12")));
		System.out.println(convertAmount(new BigDecimal("-1")));
		System.out.println(convertAmount(new BigDecimal("123456789")));
		System.out.println(convertAmount(new BigDecimal("12345678")));
		System.out.println(convertAmount(new BigDecimal("1234567")));
		System.out.println(convertAmount(new BigDecimal("123456")));
		System.out.println(convertAmount(new BigDecimal("12345")));
		System.out.println(convertAmount(new BigDecimal("1234")));
		System.out.println(convertAmount(new BigDecimal("123")));
		System.out.println(convertAmount(new BigDecimal("12")));
		System.out.println(convertAmount(new BigDecimal("1")));
	
	}
	
	private String convertAmount(BigDecimal amount){
		StringBuffer sb = new StringBuffer(amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());  
		int decimalPointIndex = sb.indexOf(".");
		//Since the scale is set as 2, the decimalPointIndex shouldn't be -1
		for (int i = decimalPointIndex-1; i>0; i--){
			//Add a comma every three digits
			if ((decimalPointIndex-i) % 3==0 && sb.charAt(i-1) != '-'){
				sb.insert(i, ",");
			}
		}
		return "$"+sb.toString();
	}
}
