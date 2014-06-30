/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.episode;

/**
 *
 * @author Filip
 */
public class EpisodeBuilder {

    private EpisodeFrame episodeFrame;

    public Episode getEpisode(InfoProvider provider) {
	buildEpisode(provider);
	return (Episode) episodeFrame;
    }

    private void buildEpisode(InfoProvider provider) {
	episodeFrame = new EpisodeFrame();
	episodeFrame.setOrdinal(provider.getOrdinal());
	episodeFrame.setReleaseDate(provider.getReleaseDate());
	episodeFrame.setSeasonOrdinal(provider.getSeasonOrdinal());
	episodeFrame.setTitle(provider.getTitle());
	episodeFrame.setTvcomUrl(provider.getTvcomUrl());
    }
}
