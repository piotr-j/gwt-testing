package com.mygdx.game.state;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.screens.GameScreen;

import java.math.BigDecimal;

/**
 * Created by EvilEntity on 09/04/2015.
 */
public class GameStateSerializer {
	private final static String TAG = GameStateSerializer.class.getSimpleName();
	private final static String KEY = "goon";
	protected GameScreen game;
	protected Json json;
	protected StringBuilder sb;

	public GameStateSerializer (GameScreen game) {
		this.game = game;
		json = new Json(JsonWriter.OutputType.minimal);
		// save all the data so we get defaults in the json
		json.setUsePrototypes(false);
		json.setSerializer(BigDecimal.class, new Json.Serializer<BigDecimal>() {
			@Override public void write (Json json, BigDecimal bigDecimal, Class knownType) {
				json.writeValue(bigDecimal.toString());
			}

			@Override public BigDecimal read (Json json, JsonValue jsonData, Class type) {
				return new BigDecimal(jsonData.asString());
			}
		});
		sb = new StringBuilder();
	}

	public String toJson(GameState state){
		// this should never fail in production
		String jsonState = json.toJson(state);
		byte[] xor = xor(jsonState).getBytes();
		return Base64Clean.encodeBytes(xor);
	}

	public GameState fromJson(String data) {
		byte[] decoded;
		try {
			// can fail if data contains invalid characters
			decoded = Base64Clean.decode(data.getBytes());
		} catch (IllegalArgumentException e) {
			game.log(TAG, "Loading from data failed");
			return null;
		}

		String b64d = xor(new String(decoded));

		GameState gameState;
		try {
			gameState = json.fromJson(GameState.class, b64d);
		} catch (SerializationException e) {
			game.log(TAG, "Loading from data failed");
			return null;
		}
		return gameState;
	}

	private String xor(String input) {
		sb.setLength(0);
		for(int i = 0; i < input.length(); i++) {
			sb.append((char)(input.charAt(i) ^ KEY.charAt(i % KEY.length())));
		}
		return sb.toString();
	}
}
