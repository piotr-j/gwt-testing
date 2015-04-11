package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.CompatLayer;
import com.mygdx.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(new AndroidCompatLayer()), config);
	}

	private class AndroidCompatLayer implements CompatLayer {

		@Override public void showExport (String text) {

		}

		@Override public void hideExport () {

		}

		@Override public void showImport () {

		}

		@Override public String hideImport () {
			return null;
		}

		@Override public String getImportText () {
			return "";
		}

		@Override public String getExportText () {
			return "";
		}
	}
}
