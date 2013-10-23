package com.xzfantom.homebrewery;

import javax.swing.SwingUtilities;

public class Homebrewery {

	private static String COMport;
	
	public static void main(String[] args) {
		COMTalker comTalker = new COMTalker();
		
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	CoreWindow ex = new CoreWindow();
            	
                ex.setVisible(true);
            }
        }); //грустный плачущий смайлик с бородой
		
		
	}

}
