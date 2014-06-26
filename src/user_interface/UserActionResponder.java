/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.laf.checkbox.WebCheckBox;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import local_data.Messages;
import local_data.Settings;
import show_components.ShowController;

/**
 *
 * @author Filip
 */
public class UserActionResponder implements ActionListener, MouseListener {

    private UserInterface userInterface;

    private ShowController showController;

    public void assignShowController(ShowController showController) {
	this.showController = showController;
    }

    public void assignUserInterface(UserInterface ui) {
	userInterface = ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource().getClass() == WebCheckBox.class) {
	    System.out.println(e.getActionCommand());
	    switch (e.getActionCommand().toString()) {
		case Messages.OPTION_AUTOSTART:
		    Settings.getInstance().OPTION_AUTOSTART = !Settings.getInstance().OPTION_AUTOSTART;
		    break;
		case Messages.OPTION_AUTOREFRESH:
		    Settings.getInstance().OPTION_AUTOREFRESH = !Settings.getInstance().OPTION_AUTOREFRESH;
		    break;
		case Messages.OPTION_DEBUG:
		    Settings.getInstance().OPTION_DEBUG = !Settings.getInstance().OPTION_DEBUG;
		    break;

		case Messages.OPTION_CONNECT_PIRATEBAY:
		    Settings.getInstance().OPTION_CONNECT_PIRATEBAY = !Settings.getInstance().OPTION_CONNECT_PIRATEBAY;
		    break;
		case Messages.OPTION_CONNECT_EKINO:
		    Settings.getInstance().OPTION_CONNECT_EKINO = !Settings.getInstance().OPTION_CONNECT_EKINO;
		    break;

		case Messages.NOTIFICATION_NEXT_EP_ANNOUNCEMENT:
		    Settings.getInstance().NOTIFICATION_NEXT_EP_ANNOUNCEMENT = !Settings.getInstance().NOTIFICATION_NEXT_EP_ANNOUNCEMENT;
		    break;
		case Messages.NOTIFICATION_NEXT_EP_COUNTER:
		    Settings.getInstance().NOTIFICATION_NEXT_EP_COUNTER = !Settings.getInstance().NOTIFICATION_NEXT_EP_COUNTER;
		    break;
		case Messages.NOTIFICATION_NEXT_EP_RELEASED_TODAY:
		    Settings.getInstance().NOTIFICATION_NEXT_EP_RELEASED_TODAY = !Settings.getInstance().NOTIFICATION_NEXT_EP_RELEASED_TODAY;
		    break;
		case Messages.NOTIFICATION_NEXT_SEAS_RELEASED_TODAY:
		    Settings.getInstance().NOTIFICATION_NEXT_SEAS_RELEASED_TODAY = !Settings.getInstance().NOTIFICATION_NEXT_SEAS_RELEASED_TODAY;
		    break;
		case Messages.NOTIFICATION_UPDATE:
		    Settings.getInstance().NOTIFICATION_UPDATE = !Settings.getInstance().NOTIFICATION_UPDATE;
		    break;
	    }
	    Settings.getInstance().saveMe();
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (e.getSource().getClass().equals(TrayIcon.class)) { //tray icon clicked
	    if (e.getButton() == MouseEvent.BUTTON1) {//left button
		userInterface.toggleScheduler();
	    }
	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
