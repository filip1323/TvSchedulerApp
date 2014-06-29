/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

import java.util.HashMap;
import show_components.season.Season;

/**
 *
 * @author Filip
 */
public class ShowFrame extends Show implements Editable {

    {
	seasons = new HashMap<>();
    }

    @Override
    public void addSeason(Season season) {
	seasons.put(season.getOrdinal(), season);
    }

    @Override
    public void setEkinoUrl(String url) {
	ekinoUrl = url;
    }

    @Override
    public void setNextEpisodeAnnouncementAvailableState(boolean state) {
	nextEpisodeAnnouncementAvailableState = state;
    }

    @Override
    public void setNextEpisodeOrdinal(int ordinal) {
	nextEpisodeOrdinal = ordinal;
    }

    @Override
    public void setNextEpisodeSeasonOrdinal(int ordinal) {
	nextEpisodeSeasonOrdinal = ordinal;
    }

    @Override
    public void setSeasonsNumber(int number) {
	seasonsNumber = number;
    }

    @Override
    public void setThumbUrl(String url) {
	this.thumbUrl = url;
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
    public void setUpdateDayTime(long date) {
	updateTime = date;
    }

    public Show readOnly() {
	return (Show) this;
    }

}
