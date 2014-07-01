/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package external_websites.ekino;

/**
 *
 * @author Filip
 */
public class EkinoResult {

    private final String imageUrl;
    private final String showUrl;
    private final String showTitle;

    public EkinoResult(String title, String showUrl, String imageUrl) {
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
