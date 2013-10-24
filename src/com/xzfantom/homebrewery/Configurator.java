package com.xzfantom.homebrewery;

import java.util.HashMap;
import java.util.Map;

public class Configurator {
	private Map<String, String> settings = new HashMap<String, String>();
	
	public Configurator () {
		settings.put("COMPort", "COM10");
	}
	
	public String getProperty (String S) {
		return settings.get(S);
	}
	
	public void setProperty (String Key, String Property) {
		settings.put(Key, Property);
	}
}
