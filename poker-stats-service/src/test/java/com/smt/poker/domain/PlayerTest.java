package com.smt.poker.domain;

import com.smt.poker.domain.stat.PlayerStat;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {
	public void testVpipRate(){
		PlayerStat player = new PlayerStat("ming");
		player.addPfrNum();
		player.addVpipNum();
		assertEquals("N/A", player.vpip());
		assertEquals("N/A", player.pfr());
		player.addHandNum();
		player.addHandNum();
		player.addHandNum();
		player.addVpipNum();
		assertEquals("66.67", player.vpip());
		assertEquals("33.33", player.pfr());
	}
}
