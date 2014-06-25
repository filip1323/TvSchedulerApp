/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

import show_components.episode.Episode;
import show_components.season.Season;

/**
 *
 * @author Filip
 */
public interface Readable {

    //solid data
    public String getTitle();

    public String getEkinoUrl();

    public String getTvcomUrl();

    public int getSeasonsNumber();

    public long getUpdateDayTime();

    //complex getters
    public Season getSeason();

    public Season getLastSeason();

    public Episode getLastEpisode();

    public Episode getNextEpisodeForMe();
}
