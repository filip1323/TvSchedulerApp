/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import misc.Bundles;

/**
 *
 * @author Filip
 */
public class Auth implements Serializable {

    static private Auth instance;

    public static Auth getInstance() {

	if (instance == null) {
	    instance = new Auth();
	}

	return instance;

    }

    private Auth() {

    }

    private String key;

    public void setKey(String key) {
	this.key = key;
	saveMe();

    }

    private String createChecksum(String string) throws Exception {

	byte[] buffer = new byte[1024];
	MessageDigest complete = MessageDigest.getInstance("MD5");
	int numRead;

	complete.update(string.getBytes(Charset.forName("UTF-8")), 0, string.length());

	byte[] b = complete.digest();
	String result = "";
	for (int i = 0; i < b.length; i++) {
	    result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
	}

	return result;
    }

    public String getKey() {
	Auth auth = loadMe();
	if (auth == null) {
	    return null;
	} else {
	    return auth.key;
	}
    }

    private void saveMe() {
	String path = "auth";
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

    private Auth loadMe() {
	String path = "auth";
	Auth auth = null;
	try {
	    FileInputStream fileIn = new FileInputStream(path);
	    ObjectInputStream in = new ObjectInputStream(fileIn);
	    auth = (Auth) in.readObject();
	    in.close();
	    fileIn.close();
	} catch (IOException i) {
	    return null;
	} catch (ClassNotFoundException c) {
	    c.printStackTrace();
	}

	return auth;
    }

    public void authorize() {
	try {
	    setKey(getHashUID());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private String getHashUID() throws Exception {
	String uid = Bundles.getMacAddress() + "-auth";
	String hashUID = (createChecksum(uid));
	return hashUID;

    }

    public boolean isAuthorized() {
	if (getKey() == null) {
	    return false;
	}
	try {
	    return (getKey().equals(getHashUID()));
	} catch (Exception ex) {
	    return false;
	}

    }
}
