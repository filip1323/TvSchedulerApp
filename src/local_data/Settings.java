/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local_data;

import java.util.HashMap;
import user_exceptions.DebugError;

/**
 *
 * @author Filip
 */
public class Settings {

    private HashMap<String, String> configCollection = null;

    private static Settings instance = null;

    public static Settings getInstance() {
	if (instance == null) {
	    instance = new Settings();
	}
	return instance;
    }

    private Settings() {
	configCollection = new HashMap<>();
	HashMap<String, String> results = Database.getInstance().getConfigProperties();
	for (String key : results.keySet()) {
	    configCollection.put(key, results.get(key));
	}
    }

    public String getPropertyString(String key) {
	if (!configCollection.containsKey(key)) {
	    throw new DebugError(key);
	}
	return configCollection.get(key);
    }

    public void setProperty(String key, String value) {
	Database.getInstance().setConfigProperty(key, value);
	configCollection.remove(key);
	configCollection.put(key, value);
    }
}
