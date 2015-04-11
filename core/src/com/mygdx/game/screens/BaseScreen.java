package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Assets;
import com.mygdx.game.MyGdxGame;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class BaseScreen implements Screen {
	public final static int VP_WIDTH = 1280;
	public final static int VP_HEIGHT = 720;
	protected final MyGdxGame game;
	protected final Assets assets;
	protected final Batch batch;
	protected final ExtendViewport viewport;

	public BaseScreen (MyGdxGame game) {
		this.game = game;
		this.assets = game.getAssets();
		this.batch = game.getBatch();
		viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT);

	}

	@Override public void show () {

	}

	@Override public void render (float delta) {
		batch.setProjectionMatrix(viewport.getCamera().combined);
		update(delta);
		draw();
	}

	public void update(float delta) {

	}

	public void draw () {

	}

	@Override public void resize (int width, int height) {
		viewport.update(width, height, true);
	}

	@Override public void pause () {

	}

	@Override public void resume () {

	}

	@Override public void hide () {

	}

	@Override public void dispose () {

	}
}
