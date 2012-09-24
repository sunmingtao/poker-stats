package com.smt.poker.persistence.dao;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.smt.poker.persistence.domain.Action;
import com.smt.poker.persistence.domain.Hand;
import com.smt.poker.persistence.domain.Move;
import com.smt.poker.persistence.domain.Player;
import com.smt.poker.persistence.handparser.HandParser;

public class DaoTest extends TestCase {
	private ApplicationContext applicationContext = null;
	private ActionDao actionDao = null;
	private PlayerDao playerDao = null;
	private HandDao handDao = null;
	private HandParser handParse = null;
	
	
	protected void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("persistenceAppContext.xml");
		playerDao = (PlayerDao)applicationContext.getBean("playerDao");
		actionDao = (ActionDao)applicationContext.getBean("actionDao");
		handDao = (HandDao)applicationContext.getBean("handDao");
		handParse = (HandParser)applicationContext.getBean("pokerStarsHandParser");
	}
	
	public void testPlayerDao(){
		playerDao.saveOrUpdate(new Player("Mingtao1"));
		System.out.println("Successfully saved");
	}
	
	public void testActionDao(){
		
		//Player player = new Player("John2");
		Player player = playerDao.find(6);
		Action action = new Action(player, Move.RAISE, new BigDecimal("0.05"));
		actionDao.saveOrUpdate(action);
	}
	
	public void testHandDao(){
		Hand hand = new Hand("12349");
		Player player = playerDao.find(16);
		hand.getPlayers().add(new Player("John4"));
		hand.getPlayers().add(player);
		Action action1 = new Action(player, Move.RAISE, new BigDecimal("0.05"));
		Action action2 = new Action(player, Move.RAISE, new BigDecimal("0.05"));
		hand.addPreflopAction(action1);
		hand.addPreflopAction(action2);
		handDao.saveOrUpdate(hand);
	}
	
	public void testImportPersist() throws IOException{
		File file = new File("single.txt");
		List<Hand> hands = handParse.parseHands(file);
		handDao.saveOrUpdate(hands.get(0));
	}
	
	public void testRetrieveHand(){
		Hand hand = handDao.find(13);
		System.out.println(hand);
	}
	
	public void testRetrievePlayers(){
		List<String> names = new ArrayList<String>();
		names.add("tranemaster");
		names.add("sunmt");
		names.add("xxx");
		List<Player> players = playerDao.findWithNames(names);
		for (Player p: players){
			System.out.println(p);
		}
		
		
	}
	
	
}
