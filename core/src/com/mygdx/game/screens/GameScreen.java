package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Base64Clean;
import com.mygdx.game.GameState;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyCE;
import com.mygdx.game.state.BigIntSerializer;
import com.strongjoshua.console.Console;

import java.math.BigInteger;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class GameScreen extends BaseScreen {
	private final static String TAG = GameScreen.class.getSimpleName();
	private final static String PREFS = "ProjectIDLE";

	private final Console console;
	private final Texture img;
	private final Skin skin;
	private final Stage stage;
	private InputMultiplexer multiplexer;
	private Preferences prefs;

	public GameScreen (MyGdxGame game) {
		super(game);
		skin = assets.getSkin();
		multiplexer = new InputMultiplexer();
		stage = new Stage(new ScreenViewport(), batch);
		multiplexer.addProcessor(stage);
		console = new Console(skin, false);
		console.setKeyID(Input.Keys.F2);
		console.setCommandExecutor(new MyCE());
		console.setSizePercent(100, 40);
		console.setPositionPercent(0, 60);
		multiplexer.addProcessor(console.getInputProcessor());

		img = new Texture("badlogic.jpg");

		TextButton button = new TextButton("Welp !", skin);
		button.addListener(new ClickListener() {
			@Override public void clicked (InputEvent event, float x, float y) {
				log("welp!");
			}
		});
		Table root = new Table();
		root.setFillParent(true);
		root.add(button);
		stage.addActor(root);
		prefs = Gdx.app.getPreferences(PREFS);
		GameState state = new GameState();
		state.update();
		state.addGold(5234442323L);

		log("State new:" + state.toString());

		Json json = new Json(JsonWriter.OutputType.minimal);
		// save all the data
		json.setUsePrototypes(false);
		json.setSerializer(BigInteger.class, new BigIntSerializer());

		log("State json: " + json.prettyPrint(state));
		String jsonState = json.toJson(state);
		String b64e = Base64Clean.encodeBytes(xor(jsonState.getBytes()));

		log("State json encoded: "+ b64e);

		String b64d = new String(xor(Base64Clean.decode(b64e.getBytes())));

		log("State json decoded: "+ b64d);

		// check version and decode it...
		JsonReader jsonReader = new JsonReader();
		JsonValue parsed = jsonReader.parse(b64d);
		int version = parsed.get("version").asInt();
		log("v: "+version);

		GameState stateFromJson;
		switch (version) {
		case 1:
			stateFromJson = json.fromJson(GameState.class, b64d);
			break;
		default:
			stateFromJson = json.fromJson(GameState.class, b64d);
		}
		log("State from json: " + stateFromJson.toString());
	}

	private String xor(String input) {
		String key = "goon";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < input.length(); i++) {
			sb.append((char)(input.charAt(i) ^ key.charAt(i % key.length())));
		}
		return sb.toString();
	}
//	private String xor(byte[] bytes) {
//		String key = "goon";
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0; i < bytes.length; i++) {
//			sb.append((char)(bytes[i] ^ key.charAt(i % key.length())));
//		}
//		return sb.toString();
//	}

	private byte[] xor(byte[] bytes) {
		String key = "goon";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			sb.append((char)(bytes[i] ^ key.charAt(i % key.length())));
		}
		return sb.toString().getBytes();
	}

	@Override public void update (float delta) {
		stage.act();
	}

	@Override public void draw () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		stage.draw();
		console.draw();

	}

	private void log (String msg) {
		Gdx.app.log(TAG, msg);
		console.log(msg);
	}

	@Override public void resize (int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height);
		console.refresh();
	}

	@Override public void show () {
		Gdx.input.setInputProcessor(multiplexer);
		console.setDisabled(false);
	}

	@Override public void hide () {
		console.setDisabled(true);
		Gdx.input.setInputProcessor(null);
	}
}
