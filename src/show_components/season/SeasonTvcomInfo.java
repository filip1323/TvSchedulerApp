/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.season;

import external_websites.tvcom.TvcomSeasonGuide;

/**
 *
 * @author Filip
 */
public class SeasonTvcomInfo implements InfoProvider {

    private TvcomSeasonGuide tvcomSeasonGuide;

    public SeasonTvcomInfo(TvcomSeasonGuide tvcomSeasonGuide) {
	this.tvcomSeasonGuide = tvcomSeasonGuide;
    }

    @Override
    public int getEpisodesNumber() {
	return tvcomSeasonGuide.getEpisodesNumber();
    }

    @Override
    public int getOrdinal() {
	return tvcomSeasonGuide.getOrdinal();
    }

    @Override
    public boolean getViewedState() {
	return false;
    }

}
