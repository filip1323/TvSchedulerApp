/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_responders;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import local_data.Messages;
import local_data.Settings;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class ConfigActionResponder implements ActionListener {

    private UserInterface userInterface;

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

		case Messages.OPTION_MENU_LAST_EP:
		    Settings.getInstance().OPTION_MENU_LAST_EP = !Settings.getInstance().OPTION_MENU_LAST_EP;
		    break;
		case Messages.OPTION_MENU_NEXT_EP_FOR_ME:
		    Settings.getInstance().OPTION_MENU_NEXT_EP_FOR_ME = !Settings.getInstance().OPTION_MENU_NEXT_EP_FOR_ME;
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

	if (e.getSource().getClass() == WebButton.class) {
	    switch (e.getActionCommand().toString()) {
		case Messages.MANAGER_ADD_SHOW:
		    break;
	    }
	}
    }
}
