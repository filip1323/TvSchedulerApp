/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.ekino;

import external_websites.ExternalSource;
import external_websites.WebsiteRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import show_components.ShowController;
import show_components.show.Show;
import user_exceptions.DebugError;
import user_exceptions.EmptyDocumentException;
import user_exceptions.WrongUrlException;
import user_interface.ShowEkinoManager;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class Ekino implements ExternalSource {

    private Show show;

    private static UserInterface userInterface;
    private static ShowController showController;
//TODO encapsulate userInterface and showCOntroller

    public static void assignShowController(ShowController showController) {
	Ekino.showController = showController;
    }

    public static void assignUserInterface(UserInterface userInterface) {
	Ekino.userInterface = userInterface;
    }

    public void assignShow(Show show) {
	this.show = show;
    }

    @Override
    public String getEpisodeLink(int seasonOrdinal, int episodeOrdinal) {

	Document showPage = null;
	try {
	    showPage = WebsiteRepository.getInstance().getDocument(getHomepageShowLink());
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	}

	Element list = showPage.select("ul.seasons").first();

	Elements sections = list.select(">li");

	Element seasonSection = null;

	for (Element section : sections) {

	    if (section.select("div.h").first().text().contains("" + seasonOrdinal)) {
		seasonSection = section;
		break;
	    }

	}

	Elements episodes = seasonSection.select("ul.episodes > li  ");

	Element episodeSection = null;

	for (Element episode : episodes) {

	    if (Integer.parseInt(episode.select("span.number").first().text()) == (episodeOrdinal)) {

		episodeSection = episode;
		break;
	    }

	}

	return "http://www.ekino.tv/" + episodeSection.select(">a").first().attr("href");
    }

    @Override
    public String getHomepageShowLink() {
	if (show.getEkinoUrl() == null) {
	    return tryToFindEkinoUrl();
	} else {
	    return show.getEkinoUrl();
	}

    }

    private String tryToFindEkinoUrl() {
	String title = show.getTitle();

	String searchListUrl = "http://www.ekino.tv/szukaj-wszystko," + title.replace(" ", "+") + ",seriale,0.html";

	Document listPage = null;
	try {
	    listPage = WebsiteRepository.getInstance().getDocument(searchListUrl);
	} catch (WrongUrlException ex) {
	    throw new DebugError("fix that");
	}

	Element list = listPage.select(".serialsList ul.list").first();

	if (list.select("li").size() != 1) {
//	    try {
//		manualInputDialog(searchListUrl);
//	    } catch (WrongUrlException ex) {
//		ex.printStackTrace();
//	    }
	    ShowEkinoManager manager = new ShowEkinoManager();
	    manager.assignShowController(showController);
	    manager.initComponents(show);
	    manager.setVisible(true);
	} else {
	    return "http://www.ekino.tv/" + list.select("li").first().select(".title h2 a").attr("href");
	}

	return null;

    }

    private boolean flag = false;

    private void checkAndUpdateUrl(String ekinoUrl) {

	if (ekinoUrl == null || ekinoUrl.trim().equals("")) {
	    //cant be empty
	    return;
	}

	if (!WebsiteRepository.getInstance().isUrlEkino(ekinoUrl)) {
	    //invalid url
	    return;
	}
	try {
	    if (!WebsiteRepository.getInstance().addDocument(ekinoUrl)) {
		//invalid url
		return;
	    }
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	} catch (EmptyDocumentException ex) {
	    ex.printStackTrace();
	}

	show.edit().setEkinoUrl(ekinoUrl);
	showController.updateShow(show);

    }

    @Override
    public String getSeasonLink(int seasonOrdinal) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
