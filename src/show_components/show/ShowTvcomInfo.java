/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

import external_websites.tvcom.Result;
import external_websites.tvcom.TvcomSeasonGuide;
import external_websites.tvcom.TvcomShowHomepage;

/**
 *
 * @author Filip
 */
public class ShowTvcomInfo implements InfoProvider {

    private final Result tvcomResult;
    private final TvcomShowHomepage tvcomShowHomepage;
    private final TvcomSeasonGuide tvcomSeasonGuide;

    public ShowTvcomInfo(Result tvcomResult, TvcomShowHomepage tvcomHomepage, TvcomSeasonGuide tvcomGuide) {
	this.tvcomResult = tvcomResult;
	this.tvcomSeasonGuide = tvcomGuide;
	this.tvcomShowHomepage = tvcomHomepage;
    }

    @Override
    public String getImgUrl() {
	return tvcomResult.getImageUrl();
    }

    @Override
    public int getNextEpisodeOrdinal() {
	return tvcomShowHomepage.getNextEpisodeOrdinal();
    }

    @Override
    public int getNextEpisodeSeasonOrdinal() {
	return tvcomShowHomepage.getNextEpisodeSeasonOrdinal();
    }

    @Override
    public int getSeasonsNumber() {
	return tvcomSeasonGuide.getSeasonsNumber();
    }

    @Override
    public String getTitle() {
	return tvcomShowHomepage.getTitle();
    }

    @Override
    public String getHomepageUrl() {
	return tvcomShowHomepage.getUrl();
    }

    @Override
    public boolean isNextEpisodeAnnounced() {
	return tvcomShowHomepage.isNextEpisodeAnnouncementAvailable();
    }

}
