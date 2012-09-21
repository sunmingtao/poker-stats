package com.smt.poker.persistence.dao;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.smt.poker.persistence.domain.Action;
import com.smt.poker.persistence.domain.Hand;
import com.smt.poker.persistence.domain.Move;
import com.smt.poker.persistence.domain.Player;

public class DaoTest extends TestCase {
	private ApplicationContext applicationContext = null;
	
	protected void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("persistenceAppContext.xml");
	}
	
	public void testPlayerDao(){
		PlayerDao dao = (PlayerDao)applicationContext.getBean("playerDao");
		dao.saveOrUpdate(new Player("Mingtao1"));
		System.out.println("Successfully saved");
	}
	
	public void testActionDao(){
		
		ActionDao actionDao = (ActionDao)applicationContext.getBean("actionDao");
		PlayerDao playerDao = (PlayerDao)applicationContext.getBean("playerDao");
		//Player player = new Player("John2");
		Player player = playerDao.find(6);
		Action action = new Action(player, Move.RAISE, new BigDecimal("0.05"));
		actionDao.saveOrUpdate(action);
	}
	
	public void testHandDao(){
		HandDao handDao = (HandDao)applicationContext.getBean("handDao");
		PlayerDao playerDao = (PlayerDao)applicationContext.getBean("playerDao");
		Hand hand = new Hand("12349");
		hand.getPlayers().add(playerDao.find(2));
		hand.getPlayers().add(new Player("John4"));
		handDao.saveOrUpdate(hand);
		
	}
}
