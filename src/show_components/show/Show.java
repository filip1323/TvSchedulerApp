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

    protected String thumbUrl;
    protected String title;
    protected String tvcomUrl;
    protected String ekinoUrl;
    protected long updateTime;
    protected int seasonsNumber;
    protected boolean nextEpisodeAnnouncementAvailableState;
    protected int nextEpisodeOrdinal;
    protected int nextEpisodeSeasonOrdinal;
    protected Episode nextEpisodeToWatch;

    protected HashMap<Integer, Season> seasons;

    @Override
    public String getEkinoUrl() {
	return ekinoUrl;
    }

    @Override
    public Episode getLastEpisode() {
	if (this.isNextEpisodeAnnouncement()) {
	    return getSeason(getNextEpisodeSeasonOrdinal()).getEpisode(getNextEpisodeOrdinal());
	} else {
	    return getLastSeason().getLastEpisode();
	}
    }

    @Override
    public Season getLastSeason() {
	return getSeason(getSeasonsNumber());
    }

    @Override
    public String getThumbUrl() {
	return thumbUrl;
    }

    @Override
    public boolean isNextEpisodeAnnouncement() {
	return nextEpisodeAnnouncementAvailableState;
    }

    @Override
    public Episode getNextEpisodeToWatch() {
	return nextEpisodeToWatch;
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
		+ "Next ep announcement " + isNextEpisodeAnnouncement() + "\n"
		+ "Next ep summary " + getNextEpisodeSeasonOrdinal() + "x" + getNextEpisodeOrdinal();
    }

    public ShowFrame edit() {
	return (ShowFrame) this;
    }

}
