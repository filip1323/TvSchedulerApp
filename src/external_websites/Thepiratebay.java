/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites;

import misc.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import user_exceptions.DebugError;
import user_exceptions.TorrentNotFoundException;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class Thepiratebay {

    private final String title;

    public Thepiratebay(String title) {
	this.title = title;
    }

    public String getEpisodeTorrentLink(int seasonOrdinal, int episodeOrdinal) throws TorrentNotFoundException {

	String linkList = getEpisodeListLink(seasonOrdinal, episodeOrdinal);

	Document document;
	try {
	    document = WebsiteRepository.getInstance().getDocument(linkList);
	} catch (WrongUrlException ex) {
	    throw new DebugError("FIx that");
	}

	Elements table = document.select("#searchResult");

	if (table.select("tr").isEmpty()) {
	    throw new TorrentNotFoundException(title + " " + seasonOrdinal + "x" + episodeOrdinal);
	}
	Element torrent = table.select("tbody tr").first();

	String linkTorrent = "http://thepiratebay.se" + torrent.select(".detName > a").first().attr("href");

	return linkTorrent;
    }

    public String getEpisodeListLink(int seasonOrdinal, int episodeOrdinal) {

	String summary = title + "%20" + Utils.Others.prepareStandardSummary(seasonOrdinal, episodeOrdinal);
	String linkList = "http://thepiratebay.se/search/" + summary + "/0/7/0";
	return linkList;
    }

    public String getSeasonListLink(int seasonOrdinal) {
	String summary = title + "%20season%20" + seasonOrdinal;
	String linkList = "http://thepiratebay.se/search/" + summary + "/0/7/0";
	return linkList;
    }
}
