/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites;

/**
 *
 * @author Filip
 */
public interface ExternalSource {

    public void assignTitle(String title);

    public String getEpisodeLink(int seasonOrdinal, int episodeOrdinal);

    public String getSeasonLink(int seasonOrdinal);

    public String getHomepageShowLink();

}
