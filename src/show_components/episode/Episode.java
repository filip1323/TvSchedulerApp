/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.episode;

import java.io.Serializable;
import misc.Utils;
import show_components.season.Season;

/**
 *
 * @author Filip
 */
public class Episode implements Readable, Serializable {

    protected int absoluteOrdinal;
    protected int seasonOrdinal;
    protected int ordinal;
    protected long releaseDate;
    protected String title;
    protected String tvcomUrl;
    protected transient Season season;

    @Override
    public int getAbsoluteOrdinal() {
	return absoluteOrdinal;
    }

    @Override
    public Episode getNextEpisode() {
	if (getSeason().getLastEpisode() == this) {
	    return getSeason().getNextSeason().getFirstEpisode();
	} else {
	    return getSeason().getEpisode(ordinal + 1);
	}
    }

    @Override
    public Episode getPreviousEpisode() {
	if (getSeason().getFirstEpisode() == this) {
	    return getSeason().getPreviousSeason().getLastEpisode();
	} else {
	    return getSeason().getEpisode(ordinal - 1);
	}
    }

    @Override
    public int getOrdinal() {
	return ordinal;
    }

    @Override
    public long getReleaseDate() {
	return releaseDate;
    }

    @Override
    public Season getSeason() {
	return season;
    }

    @Override
    public int getSeasonOrdinal() {
	return seasonOrdinal;
    }

    @Override
    public String getSummary() {
	return Utils.Others.prepareStandardSummary(seasonOrdinal, ordinal);
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
    public boolean isNextToBeWatched() {
	return (getSeason()
		.getShow()
		.getNextEpisodeToWatch()
		== this);
    }

    @Override
    public String toString() {
	return this.hashCode() + "\n"
		+ "Summary " + getSummary() + "\n"
		+ "Title " + getTitle() + "\n"
		+ "Release date " + getReleaseDate() + "\n"
		+ "TvcomUrl " + getTvcomUrl() + "\n";
    }

    public EpisodeFrame edit() {
	return (EpisodeFrame) this;
    }

}
