/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

import show_components.season.Season;

/**
 *
 * @author Filip
 */
public interface Editable {

    public void setTitle(String title);

    public void setEkinoUrl(String url);

    public void setTvcomUrl(String url);

    public void setSeasonsNumber(int number);

    public void setUpdateDayTime(long date);

    public void addSeason(Season season);
}
