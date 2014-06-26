/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components;

import java.util.ArrayList;
import show_components.show.Show;

/**
 *
 * @author Filip
 */
public interface ControllerInterface {

    //load them on start
    public ArrayList<Show> getStoredShows();

    public Show getShowWithTitle(String title);

    public void saveShow(Show show);

    public void removeShow(Show show);

    public void updateShow(Show show);

    public boolean showStored(Show show);
}
