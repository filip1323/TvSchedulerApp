/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites;

import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextField;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import misc.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import show_components.ShowController;
import show_components.show.Show;
import user_exceptions.DebugError;
import user_exceptions.EmptyDocumentException;
import user_exceptions.WrongUrlException;
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
	    try {
		manualInputDialog(searchListUrl);
	    } catch (WrongUrlException ex) {
		ex.printStackTrace();
	    }
	} else {
	    return "http://www.ekino.tv/" + list.select("li").first().select(".title h2 a").attr("href");
	}

	return null;

    }

    private boolean flag = false;

    private void manualInputDialog(String url) throws WrongUrlException {
	flag = false;
	Utils.Web.openWebpage(url);
	//creating dialog
	WebLookAndFeel.setDecorateFrames(true);
	final WebFrame ekinoDialog = new WebFrame();
	ekinoDialog.setTitle("ekino.tv");
	ekinoDialog.setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
	ekinoDialog.setResizable(false);
	//content
	WebLabel header = new WebLabel("Wprowadź adres internetowy ");
	WebLabel header2 = new WebLabel("strony głównej serialu na ");
	WebLinkLabel link = new WebLinkLabel();
	link.setLink("http://www.ekino.tv/seriale-online.html");
	link.setText("www.ekino.tv");
	link.setIcon(null);
	final WebTextField field = new WebTextField(30);
	field.setInputPrompt("np. http://www.ekino.tv/serial,house-m-d-dr-house.html");
	final WebButton button = new WebButton("Ok");

	//content itselfs
	GroupPanel content = new GroupPanel(GroupingType.none, 5, false, header, new GroupPanel(header2, link), field, button);
	content.setMargin(10);

	//final dialog
	ekinoDialog.setAlwaysOnTop(true);
	ekinoDialog.add(content);
	ekinoDialog.pack();
	ekinoDialog.setVisible(true);

	//location
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	ekinoDialog.setLocation(dim.width / 2 - ekinoDialog.getSize().width / 2, dim.height / 2 - ekinoDialog.getSize().height / 2);

	button.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		checkAndUpdateUrl(field.getText());
		ekinoDialog.dispose();
	    }
	});

	field.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		button.doClick();
	    }
	}
	);

    }

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
