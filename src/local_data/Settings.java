/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local_data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Filip
 */
public class Settings implements Serializable {

    public final String BASIC_TVCOM_URL = "http://www.tv.com/";
    public final String SEARCH_TVCOM_URL = "http://www.tv.com/search?q=";
    public final String SHOW_TVCOM_URL = "http://www.tv.com/shows/";

    private transient static Settings instance = null;

    public static Settings getInstance() {
	if (instance == null) {
	    instance = new Settings();
	    Settings loaded = instance.loadMe();
	    if (loaded != null) {
		instance = loaded;
	    }
	}
	return instance;
    }

    private Settings() {
    }

    public void saveMe() {
	String path = "settings";
	try {
	    File file = new File(path);
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    FileOutputStream fileOut
		    = new FileOutputStream(path);

	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(this);
	    out.close();
	    fileOut.close();
	} catch (IOException i) {
	    i.printStackTrace();
	}
    }

    private Settings loadMe() {
	String path = "settings";
	Settings settings = null;
	try {
	    FileInputStream fileIn = new FileInputStream(path);
	    ObjectInputStream in = new ObjectInputStream(fileIn);
	    settings = (Settings) in.readObject();
	    in.close();
	    fileIn.close();
	} catch (IOException i) {
	    return null;
	} catch (ClassNotFoundException c) {
	    c.printStackTrace();
	}

	return settings;
    }
}
