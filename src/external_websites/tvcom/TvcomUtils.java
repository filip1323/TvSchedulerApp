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
public class TvcomUtils {

    public static String getSeasonGuideLinkFromHomepageLink(String url, int seasonOrdinal) {
	String link = url + "/season-" + seasonOrdinal + "/";
	return link;
    }
}
