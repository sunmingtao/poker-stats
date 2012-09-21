package com.smt.poker.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import junit.framework.TestCase;

import com.smt.poker.dao.PlayerDao;
import com.smt.poker.dao.PlayerDaoMockImpl;
import com.smt.poker.domain.hand.Hand;
import com.smt.poker.domain.stat.PlayerStat;
import com.smt.poker.handparser.HandParser;
import com.smt.poker.handparser.PokerStarsHandParser;
import com.smt.poker.util.BigDecimalUtils;

public class ServiceTest extends TestCase {
	
	private ServiceImpl service;
	private PlayerDao playerDao;
	private HandParser handParser;
	
	private static final File[] NL2;
	
	static{
		File file = new File("2NL");
		String[] files = file.list();
		NL2 = new File[files.length];
		for (int i=0; i<files.length; i++){
			NL2[i] = new File("2NL/"+files[i]);
		}
	}
	
	private static final File[] NL5;
	
	static{
		File file = new File("5NL");
		String[] files = file.list();
		NL5 = new File[files.length];
		for (int i=0; i<files.length; i++){
			NL5[i] = new File("5NL/"+files[i]);
		}
	}
	
	private static final File[] NEW_5NL = new File[]{
		new File("20120524-20120718_5NL.txt")};
	
	private static final File[] dummy = new File[]{new File("dummy.txt")};
	private static final File[] files1 = new File[]{new File("HH20120422.txt")};
	private static final File[] files2 = new File[]{
		new File("HH2012042901.txt"),
		new File("HH2012042902.txt"),
		new File("HH2012042903.txt")};
	private static final File[] files3 = new File[]{
		new File("HH2012050601.txt"),
		new File("HH2012050602.txt")};
	private static final File[] files4 = new File[]{
		new File("HH2012051301.txt"),
		new File("HH2012051302.txt")};
	private static final File[] files5 = new File[]{
		new File("HH2012052001.txt"),
		new File("HH2012052002.txt"),
		new File("HH2012052003.txt")};
	private static final File[] files6 = new File[]{
		new File("HH2012052701.txt"),
		new File("HH2012052702.txt"),
		new File("HH2012052703.txt")};
	private static final File[] files7 = new File[]{
		new File("HH2012060301.txt"),
		new File("HH2012060302.txt"),
		new File("HH2012060303.txt"),
		new File("HH2012060304.txt")};
	private static final File[] files8 = new File[]{
		new File("HH20120611.txt")};
	
	private static final File[] files10 = new File[]{
		new File("HH2012061701.txt"),
		new File("HH2012061702.txt")};
	private static final File[] files11 = new File[]{
		new File("HH2012062401.txt"),
		new File("HH2012062402.txt"),
		new File("HH2012062403.txt"),
		new File("HH2012062404.txt")};
	private static final File[] files12 = new File[]{
		new File("HH2012070101.txt"),
		new File("HH2012070102.txt"),
		new File("HH2012070103.txt")};
	private static final File[] files13 = new File[]{
		new File("HH2012070901.txt"),
		new File("HH2012070902.txt"),
		new File("HH2012070903.txt")};
	private static final File[] files14 = new File[]{
		new File("HH2012071501.txt"),
		new File("HH2012071502.txt"),
		new File("HH2012071503.txt")};
	
	private static final File[] all = new File[]{
		new File("HH20120422.txt"),
		new File("HH2012042901.txt"),
		new File("HH2012042902.txt"),
		new File("HH2012042903.txt"),
		new File("HH2012050601.txt"),
		new File("HH2012050602.txt"),
		new File("HH2012051301.txt"),
		new File("HH2012051302.txt"),
		new File("HH2012052001.txt"),
		new File("HH2012052002.txt"),
		new File("HH2012052003.txt"),
		new File("HH2012052701.txt"),
		new File("HH2012052702.txt"),
		new File("HH2012052703.txt"),
		new File("HH2012060301.txt"),
		new File("HH2012060302.txt"),
		new File("HH2012060303.txt"),
		new File("HH2012060304.txt"),
		new File("HH20120611.txt"),
		new File("HH2012061701.txt"),
		new File("HH2012061702.txt"),
		new File("HH2012062401.txt"),
		new File("HH2012062402.txt"),
		new File("HH2012062403.txt"),
		new File("HH2012062404.txt"),
		new File("HH2012070101.txt"),
		new File("HH2012070102.txt"),
		new File("HH2012070103.txt")};
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = new ServiceImpl();
		playerDao = new PlayerDaoMockImpl();
		handParser = new PokerStarsHandParser();
		service.setPlayerDao(playerDao);
		service.setHandParser(handParser);
	}

	public void testEndToEnd() throws IOException{
		File[] files = NEW_5NL;
		List<Hand> hands = service.importHands(files);
		BigDecimal total = BigDecimal.ZERO;
		for (Hand hand: hands){
			total = BigDecimalUtils.add(total, hand.getProfit("sunmt"));
		}
		System.out.println(total + " " +hands.size());
		service.updatePlayerStats(hands);
		List<PlayerStat> players = service.getPlayers();
		for (PlayerStat player: players){
			if (player.getHandNum() > 100){
				System.out.println(player);	
			}
		}
	}
	
	public void testPreprocess() throws IOException{
		//preprocess(new File("20120612-5NL.txt"));
	}
	
	private void preprocess(File file) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(file));
		PrintWriter pw = new PrintWriter(new FileOutputStream("tmp.txt"));
		String line = null;
		while ((line = in.readLine()) != null){
			if (!line.startsWith("Hand #")){
				pw.write(line+"\n");
			}
		}
		pw.close();
	}
	
}
