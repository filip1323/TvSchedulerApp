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
public class Properties implements Serializable {

    public Property OPTION_AUTOSTART = new Property();
    public Property OPTION_AUTOREFRESH = new Property();
    public Property OPTION_DEBUG = new Property();
    public Property OPTION_MENU_LAST_EP = new Property();
    public Property OPTION_MENU_MARKED_EPISODE = new Property();
    public Property OPTION_CONNECT_PIRATEBAY = new Property();
    public Property OPTION_CONNECT_EKINO = new Property();
    public Property OPTION_INFO_NEXT_EP_COUNTER = new Property();
    public Property NOTIFICATION_NEXT_EP_ANNOUNCEMENT = new Property();
    public Property NOTIFICATION_NEXT_EP_RELEASED_TODAY = new Property();
    public Property NOTIFICATION_NEXT_SEASON_ANNOUNCEMENT = new Property();
    public Property NOTIFICATION_UPDATE = new Property();

    {
	OPTION_AUTOSTART.setText("Włączaj przy starcie systemu windows.");
	OPTION_AUTOREFRESH.setText("Automatycznie aktualizuj informacje o serialach.");
	OPTION_DEBUG.setText("Pomóż rozwijać aplikację, przez wysyłanie danych o błędach.");
	OPTION_MENU_LAST_EP.setText("Pokaż ostatnio wydany odcinek.");
	OPTION_MENU_MARKED_EPISODE.setText("Pokaż wyróżniony odcinek.");
	OPTION_CONNECT_PIRATEBAY.setText("Połącz z thepiratebay.");
	OPTION_CONNECT_EKINO.setText("Połącz z ekinem.tv");
	OPTION_INFO_NEXT_EP_COUNTER.setText("Pokazuj ile dni zostało do następnego epizodu.");
	NOTIFICATION_NEXT_EP_ANNOUNCEMENT.setText("Powiadamiaj o zapowiedzi nowego odcinka.");
	NOTIFICATION_NEXT_EP_RELEASED_TODAY.setText("Powiadamiaj o premierze nowego odcinka.");
	NOTIFICATION_NEXT_SEASON_ANNOUNCEMENT.setText("Powiadamiaj o zapowiedzi nowego sezonu.");
	NOTIFICATION_UPDATE.setText("Powiadamiaj o dostępności nowej wersji programu.");
    }

    private transient static Properties instance = null;

    public static Properties getInstance() {
	if (instance == null) {
	    instance = new Properties();
	    Properties loaded = instance.loadMe();
	    if (loaded != null) {
		instance = loaded;
	    }
	}
	return instance;
    }

    private Properties() {
    }

    public void saveMe() {
	String path = "properties";
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

    private Properties loadMe() {
	String path = "properties";
	Properties properties = null;
	try {
	    FileInputStream fileIn = new FileInputStream(path);
	    ObjectInputStream in = new ObjectInputStream(fileIn);
	    properties = (Properties) in.readObject();
	    in.close();
	    fileIn.close();
	} catch (IOException i) {
	    return null;
	} catch (ClassNotFoundException c) {
	    c.printStackTrace();
	}

	return properties;
    }

}
