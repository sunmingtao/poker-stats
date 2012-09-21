package com.smt.poker.util;

import java.math.BigDecimal;

import junit.framework.TestCase;

public class AmountUtilsTest extends TestCase{
	
	public void testExtractAmount(){
		//String str = "$1.23";
		String str = "PokerStars Hand #79256863732:  Hold'em No Limit ($0.05/$0.10 USD) - 2012/04/22 5:31:50 ET";
		BigDecimal bs[] = AmountUtils.extractAmount(str);
		for (BigDecimal b: bs){
			System.out.println(b);	
		}
	}
}
