/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import action_responders.ConfigActionResponder;
import show_components.ShowController;
import show_components.ShowLocalDataHUB;
import user_exceptions.DataNotAssignedException;
import user_exceptions.TorrentNotFoundException;
import user_exceptions.WrongUrlException;
import user_interface.UserInterface;
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
	//creating ui
	UserInterface ui = new UserInterface();

	//creating show controller
	ShowController showController = new ShowController();

	//creating main controller
	MainController mainController = new MainController();

	//creating show panel creator
	ShowPanelCreator showPanelCreator = new ShowPanelCreator();

	//creating action responder
	ConfigActionResponder responder = new ConfigActionResponder();

	//creating hub
	ShowLocalDataHUB showLocalDataHUB = new ShowLocalDataHUB();

	//passing arguments------------------------------------------
	mainController.assignUserInterface(ui);

	showController.assignShowLocalDataHUB(showLocalDataHUB);

	ui.assignResponder(responder);
	ui.assignShowPanelCreator(showPanelCreator);

	ShowPanelCreator.assignResponder(responder);

	//showing shieeet
	mainController.start();
    }

}
