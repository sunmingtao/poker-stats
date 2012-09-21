package com.smt.poker.dao;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.smt.poker.domain.test.TempHand;

public class TempHandDaoTest extends TestCase {
	private ApplicationContext applicationContext = null;
	
	protected void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("serviceApplicationContext.xml");
	}
	
	public void testHandDao(){
		HandDao dao = (HandDao)applicationContext.getBean("handDao");
		TempHand hand = new TempHand("12345", new Date(), new BigDecimal("0.01"), new BigDecimal("0.02"));
		dao.saveTempHand(hand);
		System.out.println("Successfully saved");
	}
}
