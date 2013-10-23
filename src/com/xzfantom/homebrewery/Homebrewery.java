package com.xzfantom.homebrewery;

import javax.swing.SwingUtilities;

public class Homebrewery {

	private static CoreWindow coreWindow;
	private static DataStorage dataStorage;
	private static COMTalker comTalker;
	private static String COMport;
	
	public static void main(String[] args) {		
		COMport = "COM10";
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	CoreWindow coreWindow = new CoreWindow();
                coreWindow.setVisible(true);
                DataStorage dataStorage = new DataStorage(coreWindow);
        		COMTalker comTalker = new COMTalker(COMport, dataStorage);
            }
        }); //грустный плачущий смайлик с бородой
		
		
		
	}

}
