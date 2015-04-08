package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class Assets {
	public final static String SKIN = "default_skin/uiskin.json";
	private AssetManager manager;
	private boolean isDone;
	private Skin skin;

	public Assets() {
		manager = new AssetManager();
		manager.load(SKIN, Skin.class);
	}

	public boolean update() {
		isDone = manager.update();
		if (isDone) {
			finalizeLoading();
		}
		return isDone;
	}

	private void finalizeLoading () {
		skin = manager.get(SKIN, Skin.class);

	}

	public TextureAtlas.AtlasRegion getRegion(String name) {
		return null;
	}

	public boolean isFinalized () {
		return isDone;
	}

	public Skin getSkin () {
		return skin;
	}
}
