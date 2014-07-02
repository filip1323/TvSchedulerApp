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

    public Episode getEmptyEpisode(int seasonOrdinal, int episodeOrdinal) {
	episodeFrame = new EpisodeFrame();
	episodeFrame.setOrdinal(episodeOrdinal);
	episodeFrame.setReleaseDate(0);
	episodeFrame.setSeasonOrdinal(seasonOrdinal);
	episodeFrame.setTitle(null);
	episodeFrame.setTvcomUrl(null);
	return (Episode) episodeFrame;
    }

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
