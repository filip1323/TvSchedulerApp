/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites;

import show_components.show.Show;

/**
 *
 * @author Filip
 */
public class Ekino implements ExternalSource {

    private Show show;

    public void assignShow(Show show) {
	this.show = show;
    }

    @Override
    public String getEpisodeLink(int seasonOrdinal, int episodeOrdinal) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getHomepageShowLink() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSeasonLink(int seasonOrdinal) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
