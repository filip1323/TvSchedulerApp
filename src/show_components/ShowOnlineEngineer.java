/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import external_websites.tvcom.TvcomSearchList;
import external_websites.tvcom.TvcomSearchList.Result;
import external_websites.tvcom.TvcomSeasonGuide;
import external_websites.tvcom.TvcomShowHomepage;
import external_websites.tvcom.TvcomUtils;
import local_data.Settings;
import user_exceptions.DataNotAssignedException;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class ShowOnlineEngineer {

    private String title;

    public void assignTitle(String title) {
	this.title = title;
    }

    public void constructShow() throws DataNotAssignedException, WrongUrlException {
	if (title == null) {
	    throw new DataNotAssignedException("title");
	}
	//creating search list link
	String searchListUrl = Settings.SEARCH_TVCOM_URL + title;
	TvcomSearchList searchList = new TvcomSearchList(searchListUrl);

	//getting first result
	Result firstResult = searchList.getFirstResult();

	//getting homepage
	String homepageUrl = firstResult.getShowHomepageUrl();
	TvcomShowHomepage homepage = new TvcomShowHomepage(homepageUrl);

	//getting first season guide
	TvcomSeasonGuide currentSeason = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(homepageUrl, 1));

	int seasonsNumber = currentSeason.getSeasonsNumber();

	//listing through all seasons and episodes
	for (int currentSeasonOrdinal = 1; currentSeasonOrdinal <= seasonsNumber; currentSeasonOrdinal++) {

	    currentSeason = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(homepageUrl, currentSeasonOrdinal));
	    System.out.println(currentSeason + "\n");
	    for (int ordinal = 1; ordinal <= currentSeason.getEpisodesNumber(); ordinal++) {
		System.out.println(currentSeason.getEpisodeInfo(ordinal));
	    }

	}

	//System.out.println(season_1);
    }
}
