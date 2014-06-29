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
import show_components.episode.Episode;
import show_components.episode.EpisodeBuilder;
import show_components.episode.EpisodeTvcomInfo;
import show_components.season.Season;
import show_components.season.SeasonBuilder;
import show_components.season.SeasonTvcomInfo;
import show_components.show.Show;
import show_components.show.ShowBuilder;
import show_components.show.ShowTvcomInfo;
import user_exceptions.DataNotAssignedException;
import user_exceptions.DebugError;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
class ShowOnlineEngineer {

    private String title;
    private Show show;

    public void assignTitle(String title) {
	this.title = title;
    }

    public void constructShow() throws DataNotAssignedException, WrongUrlException {
	if (title == null) {
	    throw new DataNotAssignedException("title");
	}
	//creating search list link
	String searchListUrl = Settings.getInstance().SEARCH_TVCOM_URL + title;
	TvcomSearchList searchList = new TvcomSearchList(searchListUrl);

//TODO its wrong	//getting first result
	Result firstResult = searchList.getFirstResult();
	//make sure its right
	//getting homepage
	String homepageUrl = firstResult.getShowHomepageUrl();
	TvcomShowHomepage homepage = new TvcomShowHomepage(homepageUrl);

	//getting first season guide
	TvcomSeasonGuide currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(homepageUrl, 1));

	//getting seasons number
	int seasonsNumber = currentSeasonGuide.getSeasonsNumber();

	//creating show
	ShowBuilder showBuilder = new ShowBuilder();
	ShowTvcomInfo showInfoProvider = new ShowTvcomInfo(firstResult, homepage, currentSeasonGuide);
	Show show = showBuilder.getShow(showInfoProvider);

	//listing through all seasons and episodes
	for (int currentSeasonOrdinal = 1; currentSeasonOrdinal <= seasonsNumber; currentSeasonOrdinal++) {

	    currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(homepageUrl, currentSeasonOrdinal));

	    //creating season
	    SeasonBuilder seasonBuilder = new SeasonBuilder();
	    SeasonTvcomInfo seasonInfoProvider = new SeasonTvcomInfo(currentSeasonGuide);
	    Season season = seasonBuilder.getSeason(seasonInfoProvider);

	    //assigning season
	    show.edit().addSeason(season);

	    for (int ordinal = 1; ordinal <= currentSeasonGuide.getEpisodesNumber(); ordinal++) {
		//creating episode
		EpisodeBuilder episodeBuilder = new EpisodeBuilder();
		EpisodeTvcomInfo episodeIinfoProvider = new EpisodeTvcomInfo(currentSeasonGuide, ordinal);
		Episode episode = episodeBuilder.getEpisode(episodeIinfoProvider);

		//assigning episode
		season.edit().addEpisode(episode);
	    }

	}

	this.show = show;
    }

    public Show getShow() {
	if (show == null) {
	    throw new DebugError("Create show first");
	}
	return show;
    }
}
