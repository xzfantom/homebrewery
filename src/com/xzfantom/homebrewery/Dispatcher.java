package com.xzfantom.homebrewery;

import javax.swing.SwingUtilities;

public class Dispatcher {

	private static CoreWindow coreWindow = new CoreWindow();
	private static DataStorage dataStorage = new DataStorage();
	private static COMTalker comTalker = new COMTalker();
	private static Configurator configurator = new Configurator();

	public static void main(String[] args) {

		// Configurator configurator = new Configurator();
		
		Dispatcher HB = new Dispatcher();
		HB.init();
	}
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				coreWindow.setVisible(true);				
			}
		}); // грустный плачущий смайлик с бородой

		coreWindow.setDispatcher(this);
		comTalker.setSPN(configurator.getProperty("COMPort"));
		comTalker.setDispatcher(this);
	}
	
	public void processCOMMessage (String data) {
		dataStorage.getData(data);
	}

	public void sendCOMMessage(String S) {
		coreWindow.getData(S + "\n");
		if (!comTalker.SendMessage(S)) {
			coreWindow.getData("Message not sent!");
		}
	}

}
