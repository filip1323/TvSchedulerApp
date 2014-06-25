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
public interface Editable {

    public void setOrdinal(int ordinal);

    public void setAbsoluteOrdinal(int ordinal);

    public void setTitle(String title);

    public void setTvcomUrl(String title);

    public void setReleaseDate(long date);

    public void setViewedState(boolean state);

    public void setSeason(Season season);

}
