package com.mygdx.game.state;

import com.badlogic.gdx.utils.StringBuilder;

import java.math.BigDecimal;

/**
 * Created by EvilEntity on 10/04/2015.
 */
public class BigValue {
	private transient StringBuilder sb;
	private transient String digits;
	private transient int exponent = 0;
	private BigDecimal value;

	public BigValue () {
		sb = new StringBuilder();
		value = new BigDecimal("0");
		update();
	}

	public void add(BigValue value) {

	}

	public void remove(BigValue value) {

	}

	public void mult(BigValue value) {

	}

	private void update() {
		sb.setLength(0);
		String valAsStr = value.toString();

		int length = valAsStr.length();

		int rem = length % 3;
		// we want 3 if we get no remainder
		rem = (rem==0)? 3 : rem;
		// add significant digits
		for (int i = 0; i < rem; i++) {
			sb.append(valAsStr.charAt(i));
		}
		// we want at most 3 numbers for precision, but we cant go over max offset
		int prec = Math.min(3, length - rem);
		// we want to ignore 0 in precision
		// to achieve that, we ignore 0 starting from the end
		// of relevant part of the value
		int added = 0;
		for (int i = prec-1; i >= 0; i--) {
			char ch = valAsStr.charAt(rem + i);
			// skip trailing zeroes
			if (ch == '0' && added == 0) continue;
			sb.insert(sb.length - added, ch);
			added++;
		}

		// add . if needed
		if (added > 0) {
			sb.insert(sb.length - added, '.');
		}

		digits = sb.toString();

		// no need for exponent in representation if its 0
		exponent = length - rem;
	}
}
