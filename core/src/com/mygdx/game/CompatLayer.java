package com.mygdx.game;

/**
 * Created by EvilEntity on 11/04/2015.
 */
public interface CompatLayer {
	void showExport(String text);
	void hideExport();
	void showImport();
	String hideImport();

	String getImportText ();
	String getExportText ();
}
