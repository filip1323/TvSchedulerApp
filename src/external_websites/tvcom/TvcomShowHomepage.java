/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.tvcom;

import external_websites.WebsiteRepository;
import local_data.Settings;
import misc.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import user_exceptions.DebugError;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class TvcomShowHomepage { //e.g `http://www.tv.com/shows/lost/`

    private final String url;
    private final Document document;

    public TvcomShowHomepage(String url) throws WrongUrlException {
	url = Utils.Web.normalizeUrl(url);
	if (!isUrlCorrect(url)) {
	    throw new WrongUrlException("`" + url + "` is not matching Settings.SHOW_TVCOM_URL: `" + Settings.SHOW_TVCOM_URL + "`");
	}
	this.url = url;
	this.document = WebsiteRepository.getInstance().getDocument(url);
    }

    public String getTitle() {
	return document.select("div.m.show_head h1").first().text();
    }

    public String getUrl() {
	return url;
    }

    private boolean isUrlCorrect(String url) {
	return url.contains(Settings.SHOW_TVCOM_URL) && !url.equals(Settings.SHOW_TVCOM_URL);
    }

    public boolean isNextEpisodeAnnouncementAvailable() {
	int announcementDivsNumber = document.select("div.next_episode").size();
	return (announcementDivsNumber != 0);
    }

    public int getNextEpisodeSeasonOrdinal() {
	if (!isNextEpisodeAnnouncementAvailable()) {
	    throw new DebugError("Check if next episode announcement is on screen first.");
	}
	Element annElement = document.select("div.next_episode").first();
	String label = annElement.select("div.highlight_info > p.highlight_season").text();
	return Utils.Readers.getSeasonOrdinal(label);
    }

    public int getNextEpisodeOrdinal() {
	if (!isNextEpisodeAnnouncementAvailable()) {
	    throw new DebugError("Check if next episode announcement is on screen first.");
	}
	Element annElement = document.select("div.next_episode").first();
	String label = annElement.select("div.highlight_info > p.highlight_season").text();
	return Utils.Readers.getEpisodeOrdinal(label);
    }

    public String getShowDescription() {
	Element descElement = document.select("div.description p").first();
	return descElement.text();
    }

    @Override
    public String toString() {
	String result = "`" + url + "`\t"
		+ "\tNext ep announcement: " + isNextEpisodeAnnouncementAvailable()
		+ "\tshowDescription: " + getShowDescription();

	if (isNextEpisodeAnnouncementAvailable()) {
	    result += "\tnextEpisodeSeasonOrdinal: " + getNextEpisodeSeasonOrdinal()
		    + "\tnextEpisodeOrdinal: " + getNextEpisodeOrdinal();
	}
	return result;
    }

}
