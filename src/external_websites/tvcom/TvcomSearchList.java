/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.tvcom;

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
public class TvcomSearchList { //e.g `http://www.tv.com/search?q=lost`

    private final String url;
    private final Document document;
    private final Elements resultElements;

    public TvcomSearchList(String url) throws WrongUrlException {
	url = Utils.Web.normalizeUrl(url);
	if (!isUrlCorrect(url)) {
	    throw new WrongUrlException("`" + url + "` is not matching Settings.SEARCH_TVCOM_URL: `" + Settings.SEARCH_TVCOM_URL + "`");
	}
	this.url = url;
	this.document = WebsiteRepository.getInstance().getDocument(url);
	this.resultElements = document.select("li.result.show");
    }

    public int getResultsNumber() {
	return resultElements.size();
    }

    public Result getResult(int ordinal) {

	if (ordinal < 1) { //guard
	    ordinal = 1;
	}

	if (ordinal > getResultsNumber()) {
	    throw new DebugError("Too high ordinal " + ordinal + "/" + getResultsNumber());
	}

	String title, showUrl, imageUrl;

	Element resultElement = resultElements.get(ordinal - 1);

	title = resultElement.select("div.info h4").first().text();

	showUrl = Settings.BASIC_TVCOM_URL.substring(0, Settings.BASIC_TVCOM_URL.length() - 1) + resultElement.select("div.info h4 a").attr("href");

	imageUrl = resultElement.select(">a._image_container img").first().attr("src");

	return new Result(title, showUrl, imageUrl);

    }

    public ArrayList<Result> getResults() {
	ArrayList<Result> results = new ArrayList<>();
	for (int i = 1; i <= getResultsNumber(); i++) {
	    results.add(getResult(i));
	}
	return results;
    }

    private boolean isUrlCorrect(String url) {
	return url.contains(Settings.SEARCH_TVCOM_URL) && !url.equals(Settings.SEARCH_TVCOM_URL);
    }

    public Result getFirstResult() {
	return getResult(1);
    }

    @Override
    public String toString() {
	return "`" + url + "` (" + getResultsNumber() + ")";
    }

}
