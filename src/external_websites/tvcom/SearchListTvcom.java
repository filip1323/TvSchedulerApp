/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.tvcom;

import external_websites.WebsiteRepository;
import local_data.Settings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class SearchListTvcom { //e.g `http://www.tv.com/search?q=lost`

    private final String url;
    private final Document document;
    private final Elements resultElements;

    public SearchListTvcom(String url) throws WrongUrlException {
	if (!isUrlCorrect(url)) {
	    throw new WrongUrlException("`" + url + "` is not matching Settings.SEARCH_TVCOM_URL: `" + Settings.SEARCH_TVCOM_URL + "`");
	}
	this.url = url;
	this.document = WebsiteRepository.getInstance().getDocument(url);
	this.resultElements = document.select("ul.results._standard_list > li.result.show");
    }

    public int getResultsNumber() {
	return resultElements.size();
    }

    public Result getResult(int ordinal) {

	if (ordinal < 1) { //guard
	    ordinal = 1;
	}

	if (ordinal > resultElements.size()) {
	    return null;
	}

	String title, showUrl, imageUrl;

	Element resultElement = resultElements.get(ordinal - 1);

	title = resultElement.select("div.info h4").first().text();

	showUrl = Settings.BASIC_TVCOM_URL + resultElement.select("div.info h4 a").attr("href");

	imageUrl = resultElement.select(">a._image_container img").first().attr("src");

	return new Result(title, showUrl, imageUrl);

    }

    private boolean isUrlCorrect(String url) {
	return url.contains(Settings.SEARCH_TVCOM_URL) && !url.equals(Settings.SHOW_TVCOM_URL);
    }

    public Result getFirstResult() {
	return getResult(1);
    }

    class Result {

	private final String imageUrl;
	private final String showUrl;
	private final String showTitle;

	public Result(String title, String showUrl, String imageUrl) {
	    this.showTitle = title;
	    this.imageUrl = imageUrl;
	    this.showUrl = showUrl;
	}

	public String getTitle() {
	    return showTitle;
	}

	public String getImageUrl() {
	    return imageUrl;
	}

	public String getShowHomepageUrl() {
	    return showUrl;
	}

	@Override
	public String toString() {
	    return "Title: " + showTitle + "\tImage URL: " + imageUrl + "\tShow URL: " + showUrl;
	}
    }

}
