package com.xzfantom.homebrewery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Dispatcher {

	private window coreWindow = null;
	private static DataStorage dataStorage = new DataStorage();
	private static COMTalker comTalker = new COMTalker();
	private static Configurator configurator = new Configurator();
	private Timer timer = null;

	public static void main(String[] args) {

		// Configurator configurator = new Configurator();
		
		Dispatcher HB = new Dispatcher();
		HB.init();
	}
	
	public void init() {		
		coreWindow = new window();
		comTalker.setSPN(configurator.getProperty("COMPort"));
		comTalker.setDispatcher(this);
		coreWindow.setDispatcher(this);
		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				comTalker.SendMessage("status;");				
			}
		};

		timer = new Timer(1000,taskPerformer);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				coreWindow.setVisible(true);
			}
		}); // грустный плачущий смайлик с бородой
	}
	
	public void processCOMMessage (String data) {
		dataStorage.getData(data);
		coreWindow.appendToConsole(data);
	}

	public void sendCOMMessage(String S) {
		coreWindow.getData(S);
		if (!comTalker.SendMessage(S)) {
			coreWindow.getData("Message not sent!");
		}
	}
	
	public boolean Connect() {
		if (comTalker.Connect()){
			timer.start();
			coreWindow.appendToConsole("Connected");
			return true;
		}
		timer.stop();
		coreWindow.appendToConsole("Not connected");
		return false;
	}

}
