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
public interface Readable {

    //solid data
    public int getOrdinal();

    public int getAbsoluteOrdinal();

    public String getTitle();

    public String getTvcomUrl();

    public long getReleaseDate();

    public boolean isViewed();

    //medicomplex data
    public String getSummary();

    //complex getters
    public Season getSeason();
}
