package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;

import java.math.BigDecimal;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class GameState {
	public BigDecimal gold;
	public BigDecimal ts;

	public GameState () {
		gold = new BigDecimal("0");
	}

	public void update() {
		ts = BigDecimal.valueOf(System.currentTimeMillis());
	}

	public void addGold (long amount) {
		gold = gold.add(BigDecimal.valueOf(amount));
	}

	public void addGold (double amount) {
		gold = gold.add(BigDecimal.valueOf(amount));
	}

	public void addGold (BigDecimal amount) {
		gold = gold.add(amount);
	}

	public void multGold (long amount) {
		gold = gold.multiply(BigDecimal.valueOf(amount));
	}

	public void multGold (double amount) {
		gold = gold.multiply(BigDecimal.valueOf(amount));
	}

	public void multGold (BigDecimal amount) {
		gold = gold.multiply(amount);
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
		return "GameState<"+ BigValueFormat.formatEngineer(ts) + ", " + BigValueFormat.formatEngineer(gold) + ">";
	}

	public String getGoldStr () {
		return BigValueFormat.formatEngineer(gold);
	}

	public long getTS () {
		return ts.toBigInteger().longValue();
	}
}
