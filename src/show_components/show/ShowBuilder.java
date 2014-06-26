/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

import misc.Utils;

/**
 *
 * @author Filip
 */
public class ShowBuilder {

    private ShowFrame showFrame;

    public Show getShow(InfoProvider provider) {
	buildShow(provider);
	return (Show) showFrame;
    }

    private void buildShow(InfoProvider provider) {
	showFrame = new ShowFrame();
	showFrame.setTitle(provider.getTitle());
	showFrame.setTvcomUrl(provider.getHomepageUrl());
	showFrame.setEkinoUrl(null);
	showFrame.setSeasonsNumber(provider.getSeasonsNumber());
	showFrame.setNextEpisodeAnnouncementAvailableState(provider.isNextEpisodeAnnounced());
	if (provider.isNextEpisodeAnnounced()) {
	    showFrame.setNextEpisodeOrdinal(provider.getNextEpisodeOrdinal());
	    showFrame.setNextEpisodeSeasonOrdinal(provider.getNextEpisodeSeasonOrdinal());
	}
	showFrame.setUpdateDayTime(Utils.DateManager.getCurrentDayInMilis());
    }
}
