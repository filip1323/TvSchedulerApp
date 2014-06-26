/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

import java.io.Serializable;
import java.util.HashMap;
import show_components.episode.Episode;
import show_components.season.Season;

/**
 *
 * @author Filip
 */
public class Show implements Readable, Serializable {

    protected String title;
    protected String tvcomUrl;
    protected String ekinoUrl;
    protected long updateTime;
    protected int seasonsNumber;
    protected boolean nextEpisodeAnnouncementAvailableState;
    protected int nextEpisodeOrdinal;
    protected int nextEpisodeSeasonOrdinal;

    protected HashMap<Integer, Season> seasons;

    @Override
    public String getEkinoUrl() {
	return ekinoUrl;
    }

    @Override
    public Episode getLastEpisode() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Season getLastSeason() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getNextEpisodeAnnouncementAvailableState() {
	return nextEpisodeAnnouncementAvailableState;
    }

    @Override
    public Episode getNextEpisodeForMe() {
	for (int seasonOrdinal = 1; seasonOrdinal < seasonsNumber; seasonOrdinal++) {
	    Season season = getSeason(seasonOrdinal);
	    for (int episodeOrdinal = 1; episodeOrdinal < season.getEpisodesNumber(); episodeOrdinal++) {
		Episode episode = season.getEpisode(episodeOrdinal);
		if (episode.isViewed()) {
		    return episode;
		}
	    }
	}
	return null;
    }

    @Override
    public int getNextEpisodeOrdinal() {
	return nextEpisodeOrdinal;
    }

    @Override
    public int getNextEpisodeSeasonOrdinal() {
	return nextEpisodeSeasonOrdinal;
    }

    @Override
    public Season getSeason(int ordinal) {
	return seasons.get(ordinal);
    }

    @Override
    public int getSeasonsNumber() {
	return seasonsNumber;
    }

    @Override
    public String getTitle() {
	return title;
    }

    @Override
    public String getTvcomUrl() {
	return tvcomUrl;
    }

    @Override
    public long getUpdateDayTime() {
	return updateTime;

    }

    @Override
    public String toString() {
	return this.hashCode() + "\n"
		+ "Title " + getTitle() + "\n"
		+ "TvcomUrl " + getTvcomUrl() + "\n"
		+ "SeasonsNumber " + getSeasonsNumber() + "\n"
		+ "Next ep announcement " + getNextEpisodeAnnouncementAvailableState() + "\n"
		+ "Next ep summary " + getNextEpisodeSeasonOrdinal() + "x" + getNextEpisodeOrdinal();
    }

    public ShowFrame edit() {
	return (ShowFrame) this;
    }

}
