/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.tvcom;

import java.util.HashMap;

/**
 *
 * @author Filip
 */
public class SeasonGuideTvcom {

    private final String url;

    public SeasonGuideTvcom(String url) {
	this.url = url;
    }

    public int getSeasonsNumber() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getEpisodesNumber() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public HashMap<String, String> getEpisodeInfo(int ordinal) { //<season ordinal, episode ordinal, title, date>
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
