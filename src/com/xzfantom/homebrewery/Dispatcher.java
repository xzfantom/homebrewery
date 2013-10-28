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

		dataStorage.setDispatcher(this);

		comTalker.setSPN(configurator.getProperty("COMPort"));
		comTalker.setDispatcher(this);

		coreWindow.setDispatcher(this);
	}
	
	public void processCOMMessage (String data) {
		dataStorage.GetData(data);
	}

	public void sendCOMMessage(String S) {
		comTalker.SendMessage(S);
	}

}
