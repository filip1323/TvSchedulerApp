/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_components.show;

/**
 *
 * @author Filip
 */
public interface InfoProvider {

    public String getTitle();

    public String getImgUrl();

    public String getHomepageUrl();

    public boolean isNextEpisodeAnnounced();

    public int getNextEpisodeOrdinal();

    public int getNextEpisodeSeasonOrdinal();

    public int getSeasonsNumber();

}
