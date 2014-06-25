/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites;

import java.io.IOException;
import java.util.Hashtable;
import misc.Bundles;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import user_exceptions.EmptyDocumentException;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class WebsiteRepository {

    private final String CHECK_LINK = "http://www.tv.com/";

    private final Hashtable<String, Document> documents;

    private static WebsiteRepository instance;

    public static WebsiteRepository getInstance() {
	if (instance == null) {
	    instance = new WebsiteRepository();
	}
	return instance;
    }

    private WebsiteRepository() {
	documents = new Hashtable<>();
    }

    public Document getDocument(String url) throws WrongUrlException {
	url = Bundles.prepareUrl(url);
	if (!documents.containsKey(url)) {
	    addDocument(url);
	}
	return documents.get(url);
    }

    public boolean addDocument(String url) throws WrongUrlException, EmptyDocumentException {
	try {
	    if (!isUrlValid(url)) {
		throw new WrongUrlException(url + " is not valid url");
	    }
	    Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").timeout(10000).get();
	    if (doc.body().hasText()) { //succes
		//add document to repo
		documents.put(url, doc);
		return true;
	    } else { //fail
		throw new EmptyDocumentException(url);
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    return false;
	}

    }

    public void checkInternetConnection(boolean reconnectMode) {
	try {
	    boolean connectionAvailable = addDocument(CHECK_LINK);
	    if (connectionAvailable) { //succes
		if (reconnectMode) {
		    //tell about retrievied connection
		}
	    } else { //fail
		if (!reconnectMode) {
		    //tell about unable to connect
		}
		//try to reconnect
		try {
		    Thread.sleep(10000);
		    checkInternetConnection(true);
		} catch (InterruptedException ex) {
		    ex.printStackTrace();
		}
	    }
	} catch (WrongUrlException ex) {
	    //impossibru, ignore
	    ex.printStackTrace();
	}
    }

    public boolean isUrlEkino(String url) {
	return url.contains("http://www.ekino.tv/serial") && !url.equals("http://www.ekino.tv/");
    }

    public boolean isUrlTvcom(String url) {
	return url.contains("http://www.tv.com/shows/") && !url.equals("http://www.tv.com/shows/");
    }

    public boolean isUrlValid(String url) {
	return url.contains("http://");
    }

}
