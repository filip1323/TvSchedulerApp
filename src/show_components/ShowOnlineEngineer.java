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
import java.util.ArrayList;
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
import user_exceptions.NoMoreEpisodesException;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class ShowOnlineEngineer {

    private String title;
    private Show show;

    public ArrayList<Result> getResults(String title) throws DataNotAssignedException {
	if (title == null) {
	    throw new DataNotAssignedException("title");
	}
	String searchListUrl = Settings.getInstance().SEARCH_TVCOM_URL + title;
	try {
	    TvcomSearchList searchList = new TvcomSearchList(searchListUrl);
	    return searchList.getResults();
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	}
	return null;

    }

    public Show getBasicInfo(Result result) {
	try {
	    String homepageUrl = result.getShowHomepageUrl();
	    TvcomShowHomepage homepage = new TvcomShowHomepage(homepageUrl);

	    //getting first season guide
	    currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(homepageUrl, 1));

	    //getting seasons number
	    int seasonsNumber = currentSeasonGuide.getSeasonsNumber();

	    //creating show
	    ShowBuilder showBuilder = new ShowBuilder();
	    ShowTvcomInfo showInfoProvider = new ShowTvcomInfo(result, homepage, currentSeasonGuide);
	    show = showBuilder.getShow(showInfoProvider);

	    return show;
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	    throw new DebugError("must be some deep error or miscoding");
	}

    }

    private int seasonOrdinal = 1;
    private int episodeOrdinal = 1;

    private TvcomSeasonGuide currentSeasonGuide;

    public boolean hasNextEpisode() {
	if (seasonOrdinal > show.getSeasonsNumber()) {
	    return false;
	}
	if (seasonOrdinal == show.getSeasonsNumber() && episodeOrdinal > 1 && episodeOrdinal > show.getSeason(seasonOrdinal).getEpisodesNumber()) {
	    return false;
	}
	return true;
    }

    public boolean saveNextEpisode() {
	try {
	    if (seasonOrdinal > currentSeasonGuide.getSeasonsNumber()) {
		throw new NoMoreEpisodesException();
	    }
	    Season season;
	    if (episodeOrdinal == 1) {

		currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(show.getTvcomUrl(), seasonOrdinal));

		//creating season
		SeasonBuilder seasonBuilder = new SeasonBuilder();
		SeasonTvcomInfo seasonInfoProvider = new SeasonTvcomInfo(currentSeasonGuide);
		season = seasonBuilder.getSeason(seasonInfoProvider);

		//assigning season
		show.edit().addSeason(season);
		season.edit().setShow(show);
	    } else {
		season = show.getSeason(seasonOrdinal);
	    }
	    //creating episode
	    EpisodeBuilder episodeBuilder = new EpisodeBuilder();
	    EpisodeTvcomInfo episodeIinfoProvider = new EpisodeTvcomInfo(currentSeasonGuide, episodeOrdinal);
	    Episode episode = episodeBuilder.getEpisode(episodeIinfoProvider);

	    //assigning episode
	    season.edit().addEpisode(episode);
	    episode.edit().setSeason(season);

	    if (++episodeOrdinal > currentSeasonGuide.getEpisodesNumber()) {
		seasonOrdinal += 1;
		episodeOrdinal = 1;

	    }
	    return false;
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	}
	return false;

    }
//
//    public void saveThisShow(Result result) {
//	try {
//	    TvcomShowHomepage homepage = new TvcomShowHomepage(result.getShowHomepageUrl());
//
//	    //getting first season guide
//	    currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(result.getShowHomepageUrl(), 1));
//
//	    ShowBuilder showBuilder = new ShowBuilder();
//	    ShowTvcomInfo showInfoProvider = new ShowTvcomInfo(result, homepage, currentSeasonGuide);
//	    show = showBuilder.getShow(showInfoProvider);
//
//	} catch (WrongUrlException ex) {
//	    ex.printStackTrace();
//	}
//
//    }
//<editor-fold defaultstate="collapsed" desc="comment">

    private void constructShow() throws DataNotAssignedException, WrongUrlException {
	if (title == null) {
	    throw new DataNotAssignedException("title");
	}
	//creating search list link
	String searchListUrl = Settings.getInstance().SEARCH_TVCOM_URL + title;
	TvcomSearchList searchList = new TvcomSearchList(searchListUrl);

	//getting first result
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
//</editor-fold>

    public Show getShow() {
	if (show == null) {
	    throw new DebugError("Create show first");
	}
	return show;
    }

    public int getCurrentSeasonOrdinal() {
	return seasonOrdinal;
    }

    public int getCurrentEpisodeOrdinal() {
	return episodeOrdinal;
    }

    public int getSeasonProgress() {
	return (int) (((double) episodeOrdinal / currentSeasonGuide.getEpisodesNumber()) * 100);
    }
}
