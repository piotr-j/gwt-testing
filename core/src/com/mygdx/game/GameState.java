package com.mygdx.game;

import java.math.BigInteger;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class GameState {
	int version = 1;
	long timestamp = 0;
	BigInteger gold;

	public GameState () {
		gold = new BigInteger("1234");

	}

	public void update() {
		timestamp = System.currentTimeMillis();
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
		return "GameState<"+ timestamp + ", " +gold.toString()+ ">";
	}

	public void addGold (long amount) {
		gold = gold.add(BigInteger.valueOf(amount));
	}
}
