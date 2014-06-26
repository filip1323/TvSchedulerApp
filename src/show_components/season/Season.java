/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.season;

import java.util.HashMap;
import show_components.episode.Episode;
import show_components.show.Show;

/**
 *
 * @author Filip
 */
public class Season implements Readable {

    protected HashMap<Integer, Episode> episodes;

    protected int episodesNumber;
    protected int ordinal;
    protected boolean viewedState;
    protected Show show;

    @Override
    public Episode getEpisode(int ordinal) {
	return episodes.get(ordinal);
    }

    @Override
    public int getEpisodesNumber() {
	return episodesNumber;
    }

    @Override
    public Episode getFirstEpisode(int ordinal) {
	return episodes.get(1);
    }

    @Override
    public Episode getLastEpisode(int ordinal) {
	return episodes.get(episodesNumber);
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
