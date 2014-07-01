/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import external_websites.Thepiratebay;
import external_websites.ekino.Ekino;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import local_data.Properties;
import local_data.Property;
import misc.Utils;
import show_components.ShowController;
import show_components.episode.Episode;
import show_components.season.Season;
import show_components.show.Show;
import user_exceptions.DebugError;
import user_exceptions.TorrentNotFoundException;

/**
 *
 * @author Filip
 */
public class ButtonAction extends AbstractAction {

    public enum Type {

	OPEN_TVCOM_HOMEPAGE,
	OPEN_EKINO_HOMEPAGE,
	OPEN_PIRATEBAY_EPISODE,
	OPEN_EKINO_EPISODE,
	OPEN_TVCOM_EPISODE,
	CHANGE_SETTINGS,
	OPEN_PIRATEBAY_SEASON
    }
    private Object arg;
    private Type type;
    static private ShowController showController;

    static public void assign(ShowController showController) {
	ButtonAction.showController = showController;
    }

    static public ButtonAction getInstance() {
	if (showController == null) {
	    throw new DebugError("show controller not initialized");
	}
	return new ButtonAction();
    }

    private ButtonAction() {

    }

    private ButtonAction(Type type) {
	this.type = type;
    }

    public ButtonAction(Type type, Object arg) {
	this.type = type;
	this.arg = arg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	Episode episode;
	Show show;
	String url;
	Ekino ekino;
	Season season;
	Thepiratebay piratebay;
	switch (type) {
	    case OPEN_TVCOM_EPISODE:
		episode = (Episode) arg;
		Utils.Web.openWebpage(episode.getTvcomUrl());
		break;
	    case OPEN_TVCOM_HOMEPAGE:
		Utils.Web.openWebpage(((Show) arg).getTvcomUrl());
		break;
	    case OPEN_PIRATEBAY_EPISODE:
		episode = (Episode) arg;
		piratebay = new Thepiratebay(episode.getSeason().getShow().getTitle());
		try {
		    Utils.Web.openWebpage(piratebay.getEpisodeTorrentLink(episode.getSeasonOrdinal(), episode.getOrdinal()));
		} catch (TorrentNotFoundException ex) {
		    Utils.Web.openWebpage(piratebay.getEpisodeListLink(episode.getSeasonOrdinal(), episode.getOrdinal()));
		}
		break;
	    case OPEN_PIRATEBAY_SEASON:
		season = (Season) arg;
		piratebay = new Thepiratebay(season.getShow().getTitle());
		Utils.Web.openWebpage(piratebay.getSeasonListLink(season.getOrdinal()));

	    case OPEN_EKINO_HOMEPAGE:
		show = (Show) arg;
		ekino = new Ekino();
		ekino.assignShow(show);
		url = ekino.getHomepageShowLink();
		if (url != null) {
		    Utils.Web.openWebpage(url);
		    break;
		}

		break;
	    case OPEN_EKINO_EPISODE:
		episode = (Episode) arg;
		ekino = new Ekino();
		ekino.assignShow(episode.getSeason().getShow());
		url = ekino.getHomepageShowLink();
		if (url != null) {
		    url = ekino.getEpisodeLink(episode.getSeasonOrdinal(), episode.getOrdinal());
		    Utils.Web.openWebpage(url);
		}

		break;
	    case CHANGE_SETTINGS:
		Property prop = ((Property) arg);
		prop.toggle();
		Properties.getInstance().saveMe();
		break;
	}
    }

}
