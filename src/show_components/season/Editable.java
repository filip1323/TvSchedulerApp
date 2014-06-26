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
interface Editable {

    public void setOrdinal(int ordinal);

    public void setEpisodesNumber(int number);

    public void setViewedState(boolean state);

    public void setShow(Show show);

    public void addEpisode(Episode episode);
}
