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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.state.GameState;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyCE;
import com.mygdx.game.state.GameStateSerializer;
import com.strongjoshua.console.Console;

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
	private GameStateSerializer serializer;

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
				log(TAG, "welp!");
			}
		});

		Table root = new Table();
		root.setFillParent(true);
		root.add(button);
		stage.addActor(root);

		prefs = Gdx.app.getPreferences(PREFS);

		serializer = new GameStateSerializer(this);

		GameState state = new GameState();
		state.update();
		state.addGold(52344432243L);

		log(TAG, "State: " + state.toString());
		String gsJson = serializer.toJson(state);
		GameState gameState = serializer.fromJson(gsJson);
		log(TAG, "State deserialized: " + gameState.toString());
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

	public void log (String tag, String msg) {
		Gdx.app.log(tag, msg);
		// not great
		console.log(tag + ":" + msg);
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
