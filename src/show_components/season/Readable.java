/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.season;

import show_components.episode.Episode;
import show_components.show.Show;

/**
 *
 * @author Filip
 */
public interface Readable {

    //solid data
    public int getOrdinal();

    public int getEpisodesNumber();

    public boolean isViewed();

    //complex getters
    public Show getShow();

    public Episode getEpisode(int ordinal);

    public Episode getFirstEpisode(int ordinal);

    public Episode getLastEpisode(int ordinal);
}
