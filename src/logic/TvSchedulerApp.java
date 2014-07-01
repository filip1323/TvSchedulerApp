/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.alee.laf.WebLookAndFeel;
import external_websites.Ekino;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import portal.ClientController;
import show_components.ShowController;
import show_components.ShowLocalDataHUB;
import user_exceptions.DataNotAssignedException;
import user_exceptions.TorrentNotFoundException;
import user_exceptions.WrongUrlException;
import user_interface.ConfigFrame;
import user_interface.TrayMenu;
import user_interface.UserInterface;
import user_interface.manager.ShowManagerFrame;
import user_interface.scheduler.ShowPanelCreator;

/**
 *
 * @author Filip
 */
public class TvSchedulerApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws WrongUrlException, TorrentNotFoundException, DataNotAssignedException, InterruptedException {

	//setting look and feel
	try {
	    UIManager.setLookAndFeel(new WebLookAndFeel());
	    WebLookAndFeel.setDecorateFrames(true);
	} catch (UnsupportedLookAndFeelException ex) {
	    ex.printStackTrace();
	}

	//creating ui
	UserInterface ui = new UserInterface();

	//creating show controller
	ShowController showController = new ShowController();

	//creating main controller
	MainController mainController = new MainController();

	//creating show panel creator
	ShowPanelCreator showPanelCreator = new ShowPanelCreator();

	//config frame
	ConfigFrame configFrame = new ConfigFrame();

	//show manager
	ShowManagerFrame showManager = new ShowManagerFrame();

	//traymenu
	TrayMenu trayMenu = new TrayMenu();

	//creating hub
	ShowLocalDataHUB showLocalDataHUB = new ShowLocalDataHUB();

	//portal
	ClientController portal = new ClientController();

	//passing arguments------------------------------------------
	mainController.assignUserInterface(ui);
	mainController.assignShowController(showController);

	showController.assignShowLocalDataHUB(showLocalDataHUB);
	showController.assignUserInterface(ui);
	showController.assignMainController(mainController);

	ui.assignConfigFrame(configFrame);
	ui.assignShowManagerFrame(showManager);
	ui.assignTrayMenu(trayMenu);

	trayMenu.assignController(mainController);
	trayMenu.assignUserInterface(ui);

	showManager.assignShowController(showController);
	showManager.assignUserInterface(ui);

	Ekino.assignUserInterface(ui);
	Ekino.assignShowController(showController);

	ExceptionReceiver.assignPortal(portal);

	portal.assignUserInterface(ui);

	//showing shieeet
	mainController.start();
    }

}
