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
public class SearchListTvcom { //e.g `http://www.tv.com/search?q=lost`

    private final String url;

    public SearchListTvcom(String url) {
	this.url = url;
    }

    public Result getResult(int ordinal) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Result getFirstResult() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class Result {

	public String getTitle() {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public String getImageUrl() {
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
    }

}
