/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.season;

import java.io.Serializable;
import java.util.HashMap;
import show_components.episode.Episode;
import show_components.show.Show;
import user_exceptions.DebugError;

/**
 *
 * @author Filip
 */
public class Season implements Readable, Serializable {

    protected HashMap<Integer, Episode> episodes;

    protected int episodesNumber;
    protected int ordinal;
    protected boolean viewedState;
    protected transient Show show;

    @Override
    public Episode getEpisode(int ordinal) {
	return episodes.get(ordinal);
    }

    @Override
    public int getEpisodesNumber() {
	return episodesNumber;
    }

    @Override
    public Episode getFirstEpisode() {
	return episodes.get(1);
    }

    @Override
    public Episode getLastEpisode() {
	return episodes.get(episodesNumber);
    }

    @Override
    public Season getNextSeason() {
	if (ordinal == getShow().getSeasonsNumber()) {
	    throw new DebugError("Season too high");
	}
	return getShow().getSeason(ordinal + 1);
    }

    @Override
    public Season getPreviousSeason() {
	if (ordinal == 1) {
	    throw new DebugError("Season cant be less than 1");
	}
	return getShow().getSeason(ordinal - 1);
    }

    @Override
    public int getOrdinal() {
	return ordinal;
    }

    @Override
    public Show getShow() {
	return show;
    }

    @Override
    public boolean isViewed() {
	return viewedState;
    }

    @Override
    public String toString() {
	return this.hashCode() + "\n"
		+ "Ordinal " + ordinal + "\n"
		+ "Episodes number " + episodesNumber + "\n"
		+ "Viewed state " + viewedState;
    }

    public SeasonFrame edit() {
	return (SeasonFrame) this;
    }

}
