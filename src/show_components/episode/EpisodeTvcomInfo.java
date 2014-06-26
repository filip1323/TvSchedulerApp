/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.episode;

import external_websites.tvcom.TvcomSeasonGuide;
import java.util.HashMap;

/**
 *
 * @author Filip
 */
public class EpisodeTvcomInfo implements InfoProvider {

    private TvcomSeasonGuide tvcomSeasonGuide;
    HashMap<String, String> episodeInfo;

    public EpisodeTvcomInfo(TvcomSeasonGuide tvcomSeasonGuide, int ordinal) {
	this.tvcomSeasonGuide = tvcomSeasonGuide;
	episodeInfo = tvcomSeasonGuide.getEpisodeInfo(ordinal);
    }

    @Override
    public int getOrdinal() {

	return Integer.parseInt(episodeInfo.get("ordinal"));
    }

    @Override
    public long getReleaseDate() {
	return Long.parseLong(episodeInfo.get("releaseDate"));
    }

    @Override
    public int getSeasonOrdinal() {
	return Integer.parseInt(episodeInfo.get("seasonOrdinal"));
    }

    @Override
    public String getTitle() {
	return episodeInfo.get("title");
    }

    @Override
    public String getTvcomUrl() {
	return episodeInfo.get("tvcomUrl");
    }

    @Override
    public boolean getViewedState() {
	return false;
    }
}
