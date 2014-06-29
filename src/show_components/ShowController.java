/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import java.util.ArrayList;
import show_components.show.Show;
import user_exceptions.DebugError;

/**
 *
 * @author Filip
 */
public class ShowController {

    private final ArrayList<Show> storedShows;
    private ArrayList<String> storedShowsTitle;
    private ShowLocalDataHUB showLocalDataHUB;

    public ShowController() {
	storedShows = new ArrayList<>();
//	storedShowsTitle = showLocalDataHUB.getLoadedTitles();
//	for (String title : storedShowsTitle) {
//	    storedShows.add(showLocalDataHUB.load(title));
//	}
    }

    public void assignShowLocalDataHUB(ShowLocalDataHUB showLocalDataHUB) {
	this.showLocalDataHUB = showLocalDataHUB;
    }

    public Show getShowWithTitle(String title) {
	if (!storedShowsTitle.contains(title)) {
	    throw new DebugError(title + " is not stored");
	}
	for (Show show : storedShows) {
	    if (show.getTitle().equals(title)) {
		return show;
	    }
	}
	return null;
    }

    public ArrayList<Show> getStoredShows() {
	return storedShows;
    }

    public void removeShow(Show show) {
	showLocalDataHUB.remove(show.getTitle());
    }

    public void saveShow(Show show) {
	showLocalDataHUB.save(show);
    }

    public boolean showStored(Show show) {
	return (storedShowsTitle.contains(show.getTitle()));
    }

    public void updateShow(Show show) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
