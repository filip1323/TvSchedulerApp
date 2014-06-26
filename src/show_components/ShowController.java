/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import java.util.ArrayList;
import show_components.show.Show;
import user_exceptions.DebugError;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class ShowController implements ControllerInterface {

    private final ArrayList<Show> storedShows;
    private final ShowLocalDataHUB showLocalDataHUB;
    private final ArrayList<String> storedShowsTitle;
    private UserInterface userInterface;

    public ShowController() {
	storedShows = new ArrayList<>();
	showLocalDataHUB = new ShowLocalDataHUB();
	storedShowsTitle = showLocalDataHUB.getLoadedTitles();
	for (String title : storedShowsTitle) {
	    storedShows.add(showLocalDataHUB.load(title));
	}
    }

    public void assignUserInterface(UserInterface ui) {
	this.userInterface = ui;
    }

    @Override
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

    @Override
    public ArrayList<Show> getStoredShows() {
	return storedShows;
    }

    @Override
    public void removeShow(Show show) {
	showLocalDataHUB.remove(show.getTitle());
    }

    @Override
    public void saveShow(Show show) {
	showLocalDataHUB.save(show);
    }

    @Override
    public boolean showStored(Show show) {
	return (storedShowsTitle.contains(show.getTitle()));
    }

    @Override
    public void updateShow(Show show) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
