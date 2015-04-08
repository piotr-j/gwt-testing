package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;

/**
 * Created by EvilEntity on 08/04/2015.
 */
public class MyCE extends CommandExecutor {
	protected Console console;
	@Override protected void setConsole (Console c) {
		super.setConsole(c);
		console = c;
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
