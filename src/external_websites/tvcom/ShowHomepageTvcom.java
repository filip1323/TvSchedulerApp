/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.tvcom;

/**
 *
 * @author Filip
 */
public class ShowHomepageTvcom { //e.g `http://www.tv.com/shows/lost/`

    private final String url;

    public ShowHomepageTvcom(String url) {
	this.url = url;
    }

    public boolean isNextEpisodeAnnouncementAvailable() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getNextEpisodeSeasonOrdinal() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getNextEpisodeOrdinal() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getShowDescription() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
