/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.ArrayList;
import logic.MainController;
import show_components.show.Show;
import user_exceptions.DebugError;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class ShowController {

    private MainController mainController;

    private ArrayList<Show> storedShows;
    private ArrayList<String> storedShowsTitle;
    private ShowLocalDataHUB showLocalDataHUB;
    private UserInterface userInterface;

    public void assignMainController(MainController mainController) {
	this.mainController = mainController;
    }

    public void assignShowLocalDataHUB(ShowLocalDataHUB showLocalDataHUB) {
	this.showLocalDataHUB = showLocalDataHUB;
    }

    public void assignUserInterface(UserInterface userInterface) {
	this.userInterface = userInterface;
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
	if (storedShows == null) {
	    storedShows = new ArrayList<>();
	    storedShowsTitle = showLocalDataHUB.getLoadedTitles();
	    for (String title : storedShowsTitle) {
		try {
		    storedShows.add(showLocalDataHUB.load(title));
		} catch (InvalidClassException ex) {
		    showLocalDataHUB.remove(title);
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	    }
	}
	return storedShows;
    }

    public void removeShow(Show show) {
	storedShows.remove(show);
	mainController.reloadScheduler();
	showLocalDataHUB.remove(show.getTitle());
    }

    public void saveShow(Show show) {
	showLocalDataHUB.save(show);
	storedShows.add(show);
	userInterface.addShowTabToScheduler(show);
    }

    public boolean isShowStored(Show show) {
	return (storedShowsTitle.contains(show.getTitle()));
    }

    public void updateShow(Show show) {
	showLocalDataHUB.update(show);
    }
}
