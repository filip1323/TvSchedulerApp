/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.episode;

import show_components.season.Season;

/**
 *
 * @author Filip
 */
public class EpisodeFrame extends Episode implements Editable {

    @Override
    public void setAbsoluteOrdinal(int ordinal) {
	absoluteOrdinal = ordinal;
    }

    @Override
    public void setOrdinal(int ordinal) {
	this.ordinal = ordinal;
    }

    @Override
    public void setReleaseDate(long date) {
	releaseDate = date;
    }

    @Override
    public void setSeason(Season season) {
	this.season = season;
    }

    @Override
    public void setSeasonOrdinal(int ordinal) {
	seasonOrdinal = ordinal;
    }

    @Override
    public void setTitle(String title) {
	this.title = title;
    }

    @Override
    public void setTvcomUrl(String url) {
	tvcomUrl = url;
    }

    @Override
    public void setViewedState(boolean state) {
	viewedState = state;
    }

    public Episode readOnly() {
	return (Episode) this;
    }

}
