package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CompatLayer;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new MyGdxGame(new DesktopCompatLayer()), config);
	}

	private static class DesktopCompatLayer implements CompatLayer {

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
			return "Use console to import state";
		}

		@Override public String getExportText () {
			return "Use console to export state";
		}
	}
}
