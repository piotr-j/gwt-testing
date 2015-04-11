package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.mygdx.game.screens.GameScreen;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class GameCE extends CommandExecutor {
	protected Console console;
	GameScreen game;
	public GameCE (GameScreen gameScreen) {
		game = gameScreen;
	}

	@Override protected void setConsole (Console c) {
		super.setConsole(c);
		console = c;
	}

	public void addGold(int gold) {
		game.getState().addGold(gold);
	}

	public void multGold(int gold) {
		game.getState().multGold(gold);
	}

	public void importGame(String data) {
		game.importGame(data);
	}

	public void exportGame() {
		game.showExportDialog();
	}

	public void test(String string) {
		console.log("test with string param: " +string);
		console.printLogToFile(Gdx.files.external("welp.txt"));
	}

	public void test(int integer) {
		console.log("test with int param: " +integer);
	}

	public void clear() {
		console.clear();
		console.log("test with no params");
	}
}
