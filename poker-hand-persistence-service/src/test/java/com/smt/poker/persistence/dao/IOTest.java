package com.smt.poker.persistence.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.smt.poker.persistence.handparser.HandParser;
import com.smt.poker.persistence.handparser.PokerStarsHandParser;

public class IOTest extends TestCase {
	public void testBufferedReader() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("pom.xml"));
		String line = null;
		while ((line = in.readLine()) != null){
			System.out.println(line);
		}
		line = in.readLine();
		line = in.readLine();
		line = in.readLine();
		line = in.readLine();
		System.out.println(line);
	}
	
	public void testParserIO() throws IOException{
		HandParser hp = new PokerStarsHandParser();
		File file = new File("dummy.txt");
		hp.parseHands(file);
	}
	
	public void testDateRegularExpression() throws ParseException{
		Pattern p = Pattern.compile("\\d\\d\\d\\d/\\d\\d/\\d\\d \\d\\d:\\d\\d:\\d\\d");
		Matcher m = p.matcher("PokerStars Game #24445252289:  Hold'em No Limit ($0.01/$0.02) - 2009/01/30 21:03:14 ET");
		String dateStr = null;
		if (m.find()){
			dateStr = m.group();	
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = sf.parse(dateStr);
		System.out.println(d);
	}
}
