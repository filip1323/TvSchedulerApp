/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import misc.Utils;
import show_components.episode.Episode;
import show_components.season.Season;
import show_components.show.Show;

/**
 *
 * @author Filip
 */
public class ShowLocalDataHUB {

    static public String getPathForShow(String title) {
	return "shows/" + title.replace(" ", "-") + ".ser";
    }

    static public String getPathForThumb(String title) {
	return "thumbs/" + title.replace(" ", "-").toLowerCase() + "_thumb.jpg";
    }

    public void save(Show show) {
	try {

	    //saving show
	    String path = getPathForShow(show.getTitle());

	    File file = new File(path);
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    FileOutputStream fileOut
		    = new FileOutputStream(path);

	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(show);
	    out.close();
	    fileOut.close();

	    //saving thumb
	    String thumbPath = getPathForThumb(show.getTitle());
	    saveImage(show.getThumbUrl(), thumbPath);

	} catch (IOException i) {
	    i.printStackTrace();
	}
    }

    public void update(Show show) {
	try {

	    //saving show
	    String path = getPathForShow(show.getTitle());

	    File file = new File(path);
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    FileOutputStream fileOut
		    = new FileOutputStream(path);

	    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	    out.writeObject(show);
	    out.close();
	    fileOut.close();
	} catch (IOException i) {
	    i.printStackTrace();
	}
    }

    public Show load(String title) {
	String path = getPathForShow(title);
	Show show = null;
	try {
	    FileInputStream fileIn = new FileInputStream(path);
	    ObjectInputStream in = new ObjectInputStream(fileIn);
	    show = (Show) in.readObject();
	    in.close();
	    fileIn.close();
	} catch (IOException i) {
	    i.printStackTrace();
	} catch (ClassNotFoundException c) {
	    c.printStackTrace();
	}

	for (int seasonOrdinal = 1; seasonOrdinal <= show.getSeasonsNumber(); seasonOrdinal++) {
	    Season season = show.getSeason(seasonOrdinal);
	    season.edit().setShow(show);
	    for (int episodeOrdinal = 1; episodeOrdinal <= season.getEpisodesNumber(); episodeOrdinal++) {
		Episode episode = season.getEpisode(episodeOrdinal);
		episode.edit().setSeason(season);
	    }
	}

	return show;
    }

    public ArrayList<String> getLoadedTitles() {
	ArrayList<String> titles = Utils.Files.getDirectoryListingRelative("shows");
	for (int i = 0; i < titles.size(); i++) {
	    String title = titles.get(i);
	    title = title.replace(".ser", "").replace("shows", "").replace("\\", "");
	    titles.set(i, title);
	}
	if (titles == null) {
	    return new ArrayList<>();
	}
	return titles;
    }

    public void remove(String title) {
	File file = new File(getPathForShow(title));
	File thumb = new File(getPathForThumb(title));
	file.delete();
	thumb.delete();
    }

    private static void saveImage(String imageUrl, String destinationFile) {
	try {
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);

	    byte[] b = new byte[2048];
	    int length;

	    while ((length = is.read(b)) != -1) {
		os.write(b, 0, length);
	    }

	    is.close();
	    os.close();
	} catch (MalformedURLException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
}
