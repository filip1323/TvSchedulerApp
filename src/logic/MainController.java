/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.alee.extended.label.WebHotkeyLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.label.WebLabel;
import com.alee.managers.notification.NotificationManager;
import javax.swing.ImageIcon;
import javax.swing.JWindow;
import local_data.Properties;
import local_data.Resources;
import misc.Utils;
import show_components.ShowController;
import show_components.ShowOnlineEngineer;
import show_components.episode.Episode;
import show_components.show.Show;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class MainController {

    private ShowController showController;

    private UserInterface userInterface;

    public void assignUserInterface(UserInterface ui) {
	this.userInterface = ui;
    }

    public void assignShowController(ShowController showController) {
	this.showController = showController;
    }

    public void start() {

	update();

	userInterface.initComponents();

	initialNotification();

	if (showController.getStoredShows().size() == 0) {
	    WebLabel header = new WebLabel("Dodaj swoje ulubione seriale.");
	    // logo.setSize(new Dimension(32, 32));
	    GroupPanel tipOne = new GroupPanel(new WebLabel("Aby dodać nowy serial kliknij "), new WebHotkeyLabel("PPM"), new WebLabel(" na ikonie programu"));
	    GroupPanel tipTwo = new GroupPanel(new WebLabel("Następnie "), new WebHotkeyLabel("Zarządzaj serialami"), new WebLabel(" i "), new WebHotkeyLabel("Dodaj serial"));
	    GroupPanel content = new GroupPanel(5, false, header, tipOne, tipTwo);
	    ImageIcon logo = Resources.getImageIcon("logo.png");
	    NotificationManager.showNotification((JWindow) userInterface.getWindow(), content, logo);
	    NotificationManager.setLocation(NotificationManager.SOUTH_WEST);
	}

	reloadScheduler();
    }

    public void shutdown() {
	for (Show show : showController.getStoredShows()) {
	    showController.updateShow(show);
	}
	System.exit(0);
    }

    private void update() {

	for (Show show : showController.getStoredShows()) {
	    if (show.getUpdateDayTime() != Utils.DateManager.getCurrentDayInMilis()) {
		//update
		ShowOnlineEngineer engie = new ShowOnlineEngineer();
		Show showToCompare = engie.getBasicInfo(show);
		if (showToCompare.isNextEpisodeAnnouncement()) {
		    if (show.getNextEpisodeSeasonOrdinal() != showToCompare.getNextEpisodeSeasonOrdinal()) {
			//new season announced
			if (Properties.getInstance().NOTIFICATION_NEXT_SEASON_ANNOUNCEMENT.getValue()) {
			    userInterface.showNotification("Sezon " + showToCompare.getNextEpisodeSeasonOrdinal() + " serialu " + showToCompare.getTitle() + " zapowiedziany.", "aperture.png");
			}
			show.edit().setNextEpisodeAnnouncementAvailableState(true);
			show.edit().setNextEpisodeOrdinal(showToCompare.getNextEpisodeOrdinal());
			show.edit().setNextEpisodeSeasonOrdinal(showToCompare.getNextEpisodeSeasonOrdinal());
			show.edit().setUpdateDayTime(Utils.DateManager.getCurrentDayInMilis());
			showController.updateShow(show);
		    } else if (show.getNextEpisodeOrdinal() != showToCompare.getNextEpisodeOrdinal()) {
			//new episode announced
			if (Properties.getInstance().NOTIFICATION_NEXT_EP_ANNOUNCEMENT.getValue()) {
			    userInterface.showNotification("Odcinek " + showToCompare.getNextEpisodeOrdinal() + " serialu " + showToCompare.getTitle() + " zapowiedziany.", "aperture.png");
			}
			show.edit().setNextEpisodeAnnouncementAvailableState(true);
			show.edit().setNextEpisodeOrdinal(showToCompare.getNextEpisodeOrdinal());
			show.edit().setNextEpisodeSeasonOrdinal(showToCompare.getNextEpisodeSeasonOrdinal());
			show.edit().setUpdateDayTime(Utils.DateManager.getCurrentDayInMilis());
			showController.updateShow(show);
		    }

		    engie.loadLastSeason(showToCompare);

		    if (showToCompare.getLastSeason().getOrdinal() != show.getLastSeason().getOrdinal()) {
			//new season added
			show.edit().addSeason(showToCompare.getLastSeason());
			showToCompare.getLastSeason().edit().setShow(show);
		    } else if (showToCompare.getLastEpisode().getOrdinal() != show.getLastEpisode().getOrdinal()) {
			//new episodes
			for (int ordinal = show.getLastSeason().getEpisodesNumber() + 1; ordinal < showToCompare.getLastSeason().getEpisodesNumber(); ordinal++) {
			    //userInterface.showNotification("Zaktualizowano " + ordinal + ". odcinek serialu " + showToCompare.getTitle() + ".", "aperture.png");
			    Episode episodeToCompare = showToCompare.getLastSeason().getEpisode(ordinal);
			    show.getLastSeason().edit().addEpisode(episodeToCompare);
			    episodeToCompare.edit().setSeason(show.getLastSeason());
			}
		    }

		}

	    }
	}
    }

    private void initialNotification() {

	for (Show show : showController.getStoredShows()) {
	    if (Properties.getInstance().NOTIFICATION_NEXT_EP_RELEASED_TODAY.getValue()) {
		for (int ordinal = 1; ordinal < show.getLastSeason().getEpisodesNumber(); ordinal++) {
		    Episode episode = show.getLastSeason().getEpisode(ordinal);
		    if (episode.getReleaseDate() == Utils.DateManager.getCurrentDayInMilis()) {
			userInterface.showNotification("Odcinek " + episode.getSummary() + " serialu " + show.getTitle() + " ma dziś swoją premierę.", "aperture.png");
		    }
		}
	    }
	}
    }

    public void reloadScheduler() {
	userInterface.reloadScheduler();
	for (Show show : showController.getStoredShows()) {
	    userInterface.addShowTabToScheduler(show);
	}
    }

}
