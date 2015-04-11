package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.mygdx.game.CompatLayer;
import com.mygdx.game.MyGdxGame;

public class HtmlLauncher extends GwtApplication {
	HTMLCompatLayer actionListener;

	@Override public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(1280, 720);
	}

	@Override public ApplicationListener getApplicationListener () {
		actionListener = new HTMLCompatLayer();
		return new MyGdxGame(actionListener);
	}

	@Override public void onModuleLoad () {
		super.onModuleLoad();
		actionListener.onLoad();
	}

	private class HTMLCompatLayer implements CompatLayer {
		TextArea ta;

		public void onLoad () {
			ta = new TextArea();
			ta.setCharacterWidth(180);
			ta.setVisibleLines(10);
			ta.addStyleName("ta-hidden");

			// Add them to the root panel.
			FlowPanel panel = new FlowPanel();
			panel.add(ta);
			panel.addStyleName("ta-center");
			RootPanel.get().add(panel);
		}

		@Override public void showExport (String text) {
			ta.setText(text);
			ta.removeStyleName("ta-hidden");
		}

		@Override public void hideExport () {
			ta.addStyleName("ta-hidden");
		}

		@Override public void showImport () {
			ta.setText("");
			ta.removeStyleName("ta-hidden");
		}

		@Override public String hideImport () {
			ta.addStyleName("ta-hidden");
			return ta.getText();
		}

		@Override public String getImportText () {
			return "Enter text below and press OK to import.";
		}

		@Override public String getExportText () {
			return "Copy text below and press OK to close the text area.";
		}
	}
}
