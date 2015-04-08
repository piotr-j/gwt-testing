package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class SplashScreen extends BaseScreen {
	private final static String TAG = SplashScreen.class.getSimpleName();

	private static final float MIN_SPLASH_TIME = 2.0f;
	private float splashTime;
	private final Texture img;

	public SplashScreen (MyGdxGame game) {
		super(game);
		// TODO actual splash imge
		img = new Texture("badlogic.jpg");
	}

	@Override public void update (float delta) {
		splashTime += delta;
		if (assets.update() && splashTime > MIN_SPLASH_TIME) {
			game.setScreen(MyGdxGame.ScreenType.GAME);
		}
	}

	@Override public void draw () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
