/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.season;

/**
 *
 * @author Filip
 */
public class SeasonBuilder {

    private SeasonFrame seasonFrame;

    public Season getSeason(InfoProvider provider) {
	buildSeason(provider);
	return (Season) seasonFrame;
    }

    private void buildSeason(InfoProvider provider) {
	seasonFrame = new SeasonFrame();
	seasonFrame.setEpisodesNumber(provider.getEpisodesNumber());
	seasonFrame.setOrdinal(provider.getOrdinal());
	seasonFrame.setViewedState(provider.getViewedState());
    }
}
