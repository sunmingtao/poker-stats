package com.smt.poker.persistence.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.smt.poker.persistence.domain.Hand;
import com.smt.poker.persistence.handparser.HandParser;
import com.smt.poker.persistence.service.PersistenceService;

public class ServiceTest extends TestCase {
	
	private ApplicationContext applicationContext = null;
	private PersistenceService service = null;
	private HandParser handParse = null;
	
	protected void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("persistenceAppContext.xml");
		handParse = (HandParser)applicationContext.getBean("pokerStarsHandParser");
		service = (PersistenceService)applicationContext.getBean("persistenceService");
	}
	
	public void testPersistMultipleHands() throws IOException{
		File file = new File("single.txt");
		List<Hand> hands = handParse.parseHands(file);
		service.saveHands(hands);
	}
	
	
}
