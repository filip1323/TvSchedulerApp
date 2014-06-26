/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.season;

import java.util.HashMap;
import show_components.episode.Episode;
import show_components.show.Show;

/**
 *
 * @author Filip
 */
public class SeasonFrame extends Season implements Editable {

    {
	episodes = new HashMap<>();
    }

    @Override
    public void addEpisode(Episode episode) {
	episodes.put(episode.getOrdinal(), episode);
    }

    @Override
    public void setEpisodesNumber(int number) {
	episodesNumber = number;
    }

    @Override
    public void setOrdinal(int ordinal) {
	this.ordinal = ordinal;
    }

    @Override
    public void setShow(Show show) {
	this.show = show;
    }

    @Override
    public void setViewedState(boolean state) {
	viewedState = state;
    }

    public Season readOnly() {
	return (Season) this;
    }

}
