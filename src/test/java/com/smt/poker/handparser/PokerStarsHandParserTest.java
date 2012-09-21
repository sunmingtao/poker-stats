package com.smt.poker.handparser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.smt.poker.domain.hand.Hand;
import com.smt.poker.handparser.HandParser;
import com.smt.poker.handparser.PokerStarsHandParser;

import junit.framework.TestCase;

public class PokerStarsHandParserTest extends TestCase{
	public void testReadHands() throws IOException{
		File file = new File("hand1.txt");
		HandParser parser = new PokerStarsHandParser();
		List<Hand> hands = parser.parseHands(file);
		for (Hand hand: hands){
			System.out.println(hand);
		}
	}
}
