package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.screens.BaseScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.OptionsScreen;
import com.mygdx.game.screens.SplashScreen;
import com.strongjoshua.console.Console;

import java.math.BigInteger;

public class MyGdxGame extends Game {
	private final static String TAG = MyGdxGame.class.getSimpleName();

	public enum ScreenType {SPLASH, GAME, OPTIONS}
	private SpriteBatch batch;
	private Assets assets;

	private ObjectMap<ScreenType, BaseScreen> screens;

	@Override
	public void create () {
		assets = new Assets();
		batch = new SpriteBatch();
		screens = new ObjectMap<>();
		setScreen(ScreenType.SPLASH);
	}

	public void setScreen(ScreenType screenType) {
		// TODO not convinced about that
		BaseScreen screen = screens.get(screenType);
		if (screen == null) {
			switch (screenType) {
			case SPLASH:
				screen = new SplashScreen(this);
				break;
			case GAME:
				screen = new GameScreen(this);
				break;
			case OPTIONS:
				screen = new OptionsScreen(this);
				break;
			}
			screens.put(screenType, screen);
		}
		setScreen(screen);
	}

	@Override public void dispose () {
		for (BaseScreen screen:screens.values()) {
			screen.dispose();
		}
		batch.dispose();
	}

	public Assets getAssets () {
		return assets;
	}

	public SpriteBatch getBatch () {
		return batch;
	}

}
