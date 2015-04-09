package com.mygdx.game.state;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.math.BigInteger;

/**
 * De/serializes BigInteger instances from/to json
 * Created by EvilEntity on 09/04/2015.
 */
public class BigIntSerializer implements Json.Serializer<BigInteger> {
	@Override public void write (Json json, BigInteger bigInteger, Class knownType) {
		json.writeValue(bigInteger.toByteArray());
	}

	@Override public BigInteger read (Json json, JsonValue jsonData, Class type) {
		return new BigInteger(jsonData.asByteArray());
	}
}
