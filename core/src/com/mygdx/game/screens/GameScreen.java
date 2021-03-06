package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.state.GameState;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.GameCE;
import com.mygdx.game.state.GameStateSerializer;
import com.strongjoshua.console.Console;

import java.math.BigDecimal;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class GameScreen extends BaseScreen {
	private final static String TAG = GameScreen.class.getSimpleName();
	private final static String PREFS = "ProjectIDLE";
	private final static String PREFS_STATE = "state";

	private final Console console;
	private final Texture img;
	private final Skin skin;
	private final Stage stage;
	private InputMultiplexer multiplexer;
	private Preferences prefs;
	private GameStateSerializer serializer;
	private GameState state;
	private TextButton button;

	public GameScreen (final MyGdxGame game) {
		super(game);
		skin = assets.getSkin();
		multiplexer = new InputMultiplexer();
		stage = new Stage(new ScreenViewport(), batch);
		multiplexer.addProcessor(stage);
		console = new Console(skin, false);
		console.setKeyID(Input.Keys.F2);
		console.setCommandExecutor(new GameCE(this));
		console.setSizePercent(100, 40);
		console.setPositionPercent(0, 60);
		multiplexer.addProcessor(console.getInputProcessor());

		img = new Texture("badlogic.jpg");

		button = new TextButton("Gold: 0", skin);
		button.addListener(new ClickListener() {
			@Override public void clicked (InputEvent event, float x, float y) {
				addGold(10);
			}
		});

		TextButton exportStateBtn = new TextButton("Export", skin);
		exportStateBtn.addListener(new ClickListener() {
			@Override public void clicked (InputEvent event, float x, float y) {
				exportStateDialog();
			}
		});

		TextButton importStateBtn = new TextButton("Import", skin);
		importStateBtn.addListener(new ClickListener() {
			@Override public void clicked (InputEvent event, float x, float y) {
				importStateDialog();
			}
		});

		Table root = new Table();
		root.setFillParent(true);
		root.add(button).pad(10).row();
		root.add(exportStateBtn).pad(10).row();
		root.add(importStateBtn).pad(10).row();
		stage.addActor(root);

		prefs = Gdx.app.getPreferences(PREFS);
		serializer = new GameStateSerializer(this);
		loadState();
	}

	private void multGold (long val) {
		state.multGold(val);
		state.update();
		updateUI();
	}

	private void addGold (long val) {
		state.addGold(val);
		state.update();
		updateUI();
	}

	private void loadState () {
		String stateData = prefs.getString(PREFS_STATE, null);
		GameState newState = loadState(stateData, true);
		if (newState == null) {
			newState = new GameState();
		}
		state = newState;
		updateUI();
	}

	private GameState loadState (String stateData, boolean updateTicks) {
		GameState state;
		if (stateData == null || stateData.length() == 0) {
			log(TAG, "State data is empty");
			return null;
		}

		state = serializer.fromJson(stateData);
		if (state != null) {
			if (updateTicks) {
				long ts = state.getTS();
				long now = System.currentTimeMillis();
				long diff = (long)(((now - ts) / 1000) / TICK_TIME);
				if (diff > 0) {
					tick(diff, state);
				}
			}
			log(TAG, "State loaded!");
		} else {
			log(TAG, "State loading failed!");
		}
		return state;
	}

	BigDecimal goldPerTick = BigDecimal.valueOf(1L);
	private void tick() {
		tick(1L, state);
	}

	private void tick (long count, GameState state) {
		state.addGold(goldPerTick.multiply(BigDecimal.valueOf(count)));
		state.update();
	}

	private void saveState() {
		String stateData = serializer.toJson(state);
		prefs.putString(PREFS_STATE, stateData);
		prefs.flush();
		log(TAG, "State saved!");
	}

	float AUTO_SAVE_DELAY = 60;
	float autoSaveTimer = 0;
	float TICK_TIME = 1f;
	float tickTimer = 0;
	@Override public void update (float delta) {
		stage.act();

		tickTimer += delta;
		if (tickTimer > TICK_TIME) {
			tickTimer = 0;
			tick();
			updateUI();
		}

		autoSaveTimer += delta;
		if (autoSaveTimer > AUTO_SAVE_DELAY) {
			autoSaveTimer = 0;
			saveState();
		}
	}

	private void updateUI() {
		button.setText("Gold: " + state.getGoldStr());
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
		console.log(tag + ": " + msg);
	}

	@Override public void resize (int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
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

	@Override public void pause () {
		saveState();
	}

	@Override public void resume () {

	}

	Input.TextInputListener exportListener = new Input.TextInputListener() {
		@Override public void input (String text) { }
		@Override public void canceled () { }
	};

	private void exportStateDialog () {
		String sData = serializer.toJson(state);
		Gdx.input.getTextInput(exportListener, "Export game state", sData, "");
	}

	Input.TextInputListener importListener = new Input.TextInputListener() {
		@Override public void input (String text) {
			GameState gameState = loadState(text, false);
			if (gameState == null) {
				importFailedDialog();
			} else {
				setState(gameState);
				updateUI();
			}
		}

		@Override public void canceled () {
			log(TAG, "Import cancelled");
		}
	};

	private void importStateDialog () {
		Gdx.input.getTextInput(importListener, "Import game state", "", "Paste save data here");
	}

	private void importFailedDialog () {
		final Dialog dialog = new Dialog("Import Failed", skin);
		dialog.text("Importing game state failed!");
		dialog.button("OK");
		dialog.show(stage);
	}

	public void setState (GameState state) {
		if (state != null)
			this.state = state;
	}

	public GameState getState () {
		return state;
	}
}
