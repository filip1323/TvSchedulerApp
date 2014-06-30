/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.alee.extended.label.WebHotkeyLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.label.WebLabel;
import com.alee.managers.notification.NotificationManager;
import javax.swing.ImageIcon;
import javax.swing.JWindow;
import local_data.Resources;
import show_components.ShowController;
import show_components.show.Show;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class MainController {

    private ShowController showController;

    private UserInterface userInterface;

    public void assignUserInterface(UserInterface ui) {
	this.userInterface = ui;
    }

    void assignShowController(ShowController showController) {
	this.showController = showController;
    }

    public void shutdown() {
	System.exit(0);
    }

    public void start() {
	userInterface.initComponents();

	if (showController.getStoredShows().size() == 0) {
	    WebLabel header = new WebLabel("Dodaj swoje ulubione seriale.");
	    // logo.setSize(new Dimension(32, 32));
	    GroupPanel tipOne = new GroupPanel(new WebLabel("Aby dodać nowy serial kliknij "), new WebHotkeyLabel("PPM"), new WebLabel(" na ikonie programu"));
	    GroupPanel tipTwo = new GroupPanel(new WebLabel("Następnie "), new WebHotkeyLabel("Zarządzaj serialami"), new WebLabel(" i "), new WebHotkeyLabel("Dodaj serial"));
	    GroupPanel content = new GroupPanel(5, false, header, tipOne, tipTwo);
	    ImageIcon logo = Resources.getImageIcon("logo.png");
	    NotificationManager.showNotification((JWindow) userInterface.getWindow(), content, logo);
	    NotificationManager.setLocation(NotificationManager.SOUTH_WEST);

	}

	loadScheduler();
    }

    public void loadScheduler() {
	userInterface.reloadScheduler();
	for (Show show : showController.getStoredShows()) {
	    userInterface.addShowTabToScheduler(show);
	}
    }

}
