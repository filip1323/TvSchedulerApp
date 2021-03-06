/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.episode;

import show_components.season.Season;

/**
 *
 * @author Filip
 */
interface Readable {

    //solid data
    public int getAbsoluteOrdinal();

    public int getSeasonOrdinal();

    public int getOrdinal();

    public String getTitle();

    public String getTvcomUrl();

    public long getReleaseDate();

    public boolean isNextToBeWatched();

    //medicomplex data
    public String getSummary();

    //complex getters
    public Season getSeason();

    public Episode getNextEpisode();

    public Episode getPreviousEpisode();
}
