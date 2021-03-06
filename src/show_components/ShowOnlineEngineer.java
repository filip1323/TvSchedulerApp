/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import external_websites.tvcom.TvcomResult;
import external_websites.tvcom.TvcomSearchList;
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
import user_exceptions.LackOfEpisodesContentException;
import user_exceptions.NoMoreEpisodesException;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class ShowOnlineEngineer {

    private String title;
    private Show show;

    public ArrayList<TvcomResult> getResults(String title) throws DataNotAssignedException {
	String searchListUrl = Settings.SEARCH_TVCOM_URL + title;
	try {
	    TvcomSearchList searchList = new TvcomSearchList(searchListUrl);
	    return searchList.getResults();
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	}
	return null;

    }

    public Show getBasicInfo(TvcomResult result) {
	try {
	    String homepageUrl = result.getShowHomepageUrl();
	    TvcomShowHomepage homepage = new TvcomShowHomepage(homepageUrl);

	    //getting first season guide
	    currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(homepageUrl, 1));

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

    public Show getBasicInfo(Show show) {
	try {
	    TvcomShowHomepage homepage = new TvcomShowHomepage(show.getTvcomUrl());

	    //getting first season guide
	    currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(show.getTvcomUrl(), 1));

	    //pseudo result
	    TvcomResult result = new TvcomResult(show.getTitle(), show.getTvcomUrl(), show.getThumbUrl());

	    //creating show
	    ShowBuilder showBuilder = new ShowBuilder();
	    ShowTvcomInfo showInfoProvider = new ShowTvcomInfo(result, homepage, currentSeasonGuide);
	    show = showBuilder.getShow(showInfoProvider);

	    return show;
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	}
	return null;
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

    public void loadLastSeason(Show show) {
	try {
	    TvcomSeasonGuide currentSeasonGuide = new TvcomSeasonGuide(TvcomUtils.getSeasonGuideLinkFromHomepageLink(show.getTvcomUrl(), show.getSeasonsNumber()));

	    //creating season
	    SeasonBuilder seasonBuilder = new SeasonBuilder();
	    SeasonTvcomInfo seasonInfoProvider = new SeasonTvcomInfo(currentSeasonGuide);
	    Season season = seasonBuilder.getSeason(seasonInfoProvider);

	    //assigning season
	    show.edit().addSeason(season);
	    season.edit().setShow(show);

	    for (int ordinal = 1; ordinal <= season.getEpisodesNumber(); ordinal++) {
		EpisodeBuilder episodeBuilder = new EpisodeBuilder();
		EpisodeTvcomInfo episodeIinfoProvider;

		Episode episode = new Episode();
		try {
		    episodeIinfoProvider = new EpisodeTvcomInfo(currentSeasonGuide, ordinal);
		    episode = episodeBuilder.getEpisode(episodeIinfoProvider);

		} catch (LackOfEpisodesContentException ex) {
		    episode = episodeBuilder.getEmptyEpisode(seasonOrdinal, episodeOrdinal);
		}
		//assigning episode
		season.edit().addEpisode(episode);
		episode.edit().setSeason(season);
	    }

	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	}
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
	    EpisodeTvcomInfo episodeIinfoProvider;
	    try {
		episodeIinfoProvider = new EpisodeTvcomInfo(currentSeasonGuide, episodeOrdinal);
		Episode episode = episodeBuilder.getEpisode(episodeIinfoProvider);
		//assigning episode
		season.edit().addEpisode(episode);
		episode.edit().setSeason(season);
	    } catch (LackOfEpisodesContentException ex) {
		ex.printStackTrace();
	    }

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
