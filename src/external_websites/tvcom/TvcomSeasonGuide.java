/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.tvcom;

import external_websites.WebsiteRepository;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class TvcomSeasonGuide {

    private final String url;
    private final Document document;

    public TvcomSeasonGuide(String url) throws WrongUrlException {
	url = Utils.Web.normalizeUrl(url);
	if (!isUrlCorrect(url)) {
	    throw new WrongUrlException("`" + url + "` is not matching episode guide or season guide pattern");
	}
	this.url = url;
	this.document = WebsiteRepository.getInstance().getDocument(url);
	if (getOrdinal() > getSeasonsNumber()) {
	    throw new DebugError("Season ordinal cant be higher than seasons number");
	} else if (getOrdinal() < 1) {
	    throw new DebugError("Season ordinal cant be lower than 1");
	}
    }

    private boolean isUrlCorrect(String url) {
	return url.contains(Settings.SHOW_TVCOM_URL) && !url.equals(Settings.SHOW_TVCOM_URL) && (url.endsWith("/episodes/") || url.endsWith("/episodes") || url.contains("/season-"));
    }

    public int getSeasonsNumber() {
	Elements seasonsElements = document.select("._filter_navigation [data-season]");
	int seasonsNumber = 0;
	Pattern pattern = Pattern.compile("Season ([0-9]+)");
	for (Element element : seasonsElements) {
	    Matcher matcher = pattern.matcher(element.text());
	    if (matcher.find()) {
		seasonsNumber++;
	    }
	}
	return seasonsNumber;
    }

    public int getEpisodesNumber() {
	Elements epElements = document.select("ul.episodes._toggle_list > li.episode");
	Elements labelElements = epElements.select("div.ep_info");
	int maxEpOrdinalFound = 0;
	for (Element labelElement : labelElements) {
	    int accOrdinalFound = Utils.Readers.getEpisodeOrdinalOrNull(labelElement.text());
	    if (accOrdinalFound > maxEpOrdinalFound) {
		maxEpOrdinalFound = accOrdinalFound;
	    }
	}
	return maxEpOrdinalFound;
    }

    public int getOrdinal() {
	String url = this.url.replace("-", " "); //magic, not clean but working
	return Utils.Readers.getSeasonOrdinalOrNull(url);

    }

    public HashMap<String, String> getEpisodeInfo(final int ordinal) { //<season ordinal, episode ordinal, title, date, url>
	if (getEpisodesNumber() < ordinal) {
	    throw new DebugError("Ordinal cant be higher than episodes number");
	}
	Element selectedEpisodeElement = null;
	Elements episodeElements = document.select("ul.episodes._toggle_list > li.episode");
	for (int currentOrdinal = 0; currentOrdinal < episodeElements.size(); currentOrdinal++) {
	    Element labelElement = episodeElements.get(currentOrdinal).select("div.ep_info").first();
	    if (!labelElement.hasText()) { //guard
		continue;
	    }
	    if (Utils.Readers.getEpisodeOrdinalOrNull(labelElement.text()) == ordinal) {
		selectedEpisodeElement = episodeElements.get(currentOrdinal);
		break;
	    }
	}

	HashMap<String, String> episodeInfo = new HashMap<>();

	int seasonOrdinal = getOrdinal();

	int episodeOrdinal = ordinal;

	String dateString = selectedEpisodeElement.select("div.date").first().text();

	long date = Utils.DateManager.convertAmericanDateIntoLong(dateString);

	Element titleElement = selectedEpisodeElement.select("a.title").first();

	String title = titleElement.text();

	String url = Utils.Web.normalizeUrl(Settings.BASIC_TVCOM_URL + titleElement.attr("href"));

	episodeInfo.put("seasonOrdinal", Integer.toString(seasonOrdinal));
	episodeInfo.put("ordinal", Integer.toString(episodeOrdinal));
	episodeInfo.put("title", title);
	episodeInfo.put("releaseDate", Long.toString(date));
	episodeInfo.put("tvcomUrl", url);
	return episodeInfo;

	//return selectedEpElement
    }

    @Override
    public String toString() {
	String result = "`" + url + "`\t"
		+ "\tSeasons number: " + getSeasonsNumber()
		+ "\tSeason ordinal: " + getOrdinal()
		+ "\tEpisodes number: " + getEpisodesNumber();

	return result;
    }

}
