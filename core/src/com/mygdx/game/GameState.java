package com.mygdx.game;

import java.math.BigInteger;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class GameState {
	public int version = 1;
	public BigInteger gold;
	public BigInteger ts;

	public GameState () {
		gold = new BigInteger("1234");
	}

	public void update() {
		ts = BigInteger.valueOf(System.currentTimeMillis());
	}

	public void persist() {

	}

	public void importFromJson(String json) {

	}

	public String exportFromJson() {

		return "";
	}

	@Override public String toString () {
		// format doesnt work in gwt
		return "GameState<"+ ts.toString() + ", " +gold.toString()+ ">";
	}

	public void addGold (long amount) {
		gold = gold.add(BigInteger.valueOf(amount));
	}
}
