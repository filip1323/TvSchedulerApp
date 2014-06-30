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
interface Editable {

    public void setAbsoluteOrdinal(int ordinal);

    public void setSeasonOrdinal(int ordinal);

    public void setOrdinal(int ordinal);

    public void setTitle(String title);

    public void setTvcomUrl(String url);

    public void setReleaseDate(long date);

    public void setAsNextToWatch();

    public void setSeason(Season season);

}
