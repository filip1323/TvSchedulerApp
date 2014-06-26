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
public interface InfoProvider {

    public int getSeasonOrdinal();

    public int getOrdinal();

    public String getTitle();

    public String getTvcomUrl();

    public long getReleaseDate();

    public boolean getViewedState();
}
