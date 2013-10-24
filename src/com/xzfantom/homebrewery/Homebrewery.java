package com.xzfantom.homebrewery;

import javax.swing.SwingUtilities;

public class Homebrewery {

	private static CoreWindow coreWindow = new CoreWindow();
	private static DataStorage dataStorage = new DataStorage();
	private static COMTalker comTalker = new COMTalker();
	private static Configurator configurator = new Configurator();

	public static void main(String[] args) {

		// Configurator configurator = new Configurator();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				coreWindow.setVisible(true);

				dataStorage.setCoreWindow(coreWindow);

				comTalker.setSPN(configurator.getProperty("COMPort"));
				comTalker.setDS(dataStorage);

				coreWindow.SetCOMTalker(comTalker);
			}
		}); // грустный плачущий смайлик с бородой

	}

}
