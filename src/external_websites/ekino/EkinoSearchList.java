/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.ekino;

import external_websites.WebsiteRepository;
import java.util.ArrayList;
import local_data.Settings;
import misc.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import user_exceptions.DebugError;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class EkinoSearchList {

    private String url;
    private final Document document;
    private final Elements resultElements;

    public EkinoSearchList(String title) throws WrongUrlException {
	url = Settings.SEARCH_EKINO_URL_START + title.replace(" ", "+") + Settings.SEARCH_EKINO_URL_END;
	url = Utils.Web.normalizeUrl(url);
	if (!isUrlCorrect(url)) {
	    throw new WrongUrlException("`" + url + "` is not matching Settings.SEARCH_TVCOM_URL: `" + Settings.SEARCH_TVCOM_URL + "`");
	}
	this.url = url;
	this.document = WebsiteRepository.getInstance().getDocument(url);
	this.resultElements = document.select("div.mostPopular.serials.hotMovies ul.list li");
    }

    public EkinoResult getResult(int ordinal) {

	if (ordinal < 1) { //guard
	    ordinal = 1;
	}

	if (ordinal > getResultsNumber()) {
	    throw new DebugError("Too high ordinal " + ordinal + "/" + getResultsNumber());
	}

	String title, showUrl, imageUrl;

	Element resultElement = resultElements.get(ordinal - 1);

	title = resultElement.select("div.title h2").first().text();

	showUrl = Settings.BASIC_EKINO_URL + resultElement.select("div.title h2 a").attr("href");

	imageUrl = Settings.BASIC_EKINO_URL.substring(0, Settings.BASIC_EKINO_URL.length() - 1) + resultElement.select("img.poster").first().attr("src");

	return new EkinoResult(title, showUrl, imageUrl);

    }

    public ArrayList<EkinoResult> getResults() {
	ArrayList<EkinoResult> results = new ArrayList<>();
	for (int i = 1; i <= getResultsNumber(); i++) {
	    results.add(getResult(i));
	}
	return results;
    }

    public int getResultsNumber() {
	return resultElements.size();
    }

    private boolean isUrlCorrect(String url) {
	return url.contains(Settings.SEARCH_EKINO_URL_START) && !url.equals(Settings.SEARCH_EKINO_URL_START);
    }

}
