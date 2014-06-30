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
interface Readable {

    //solid data
    public String getTitle();

    public String getEkinoUrl();

    public String getTvcomUrl();

    public String getThumbUrl();

    public int getSeasonsNumber();

    public long getUpdateDayTime();

    public boolean isNextEpisodeAnnouncement();

    public int getNextEpisodeOrdinal();

    public int getNextEpisodeSeasonOrdinal();

    //complex getters
    public Season getSeason(int ordinal);

    public Season getLastSeason();

    public Episode getLastEpisode();

    public Episode getNextEpisodeToWatch();

    public Episode getAnnouncenedEpisode();
}
