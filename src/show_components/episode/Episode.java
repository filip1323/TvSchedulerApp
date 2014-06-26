/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.episode;

import misc.Utils;
import show_components.season.Season;

/**
 *
 * @author Filip
 */
public class Episode implements Readable {

    protected int absoluteOrdinal;
    protected int seasonOrdinal;
    protected int ordinal;
    protected long releaseDate;
    protected String title;
    protected String tvcomUrl;
    protected boolean viewedState;
    protected Season season;

    @Override
    public int getAbsoluteOrdinal() {
	return absoluteOrdinal;
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
    public boolean isViewed() {
	return viewedState;
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
