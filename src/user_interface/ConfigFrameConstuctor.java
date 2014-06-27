/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import local_data.Settings;

/**
 *
 * @author Filip
 */
public class ConfigFrameConstuctor {

    private ConfigFrame frame;
    private UserActionResponder userActionResponder;

    public void assignUserActionResponder(UserActionResponder userActionResponder) {
	this.userActionResponder = userActionResponder;
    }

    public ConfigFrame getConfigFrame() {
	if (frame == null) {
	    prepareConfigFrame();
	}

	return frame;
    }

    private void prepareConfigFrame() {
	frame = new ConfigFrame();
	frame.assignUserActionResponder(userActionResponder);
	frame.initComponents();

	checkBoxes();
    }

    private void checkBoxes() {
	frame.OPTION_AUTOREFRESH_CHECKBOX.setSelected(Settings.getInstance().OPTION_AUTOREFRESH);
	frame.OPTION_AUTOSTART_CHECKBOX.setSelected(Settings.getInstance().OPTION_AUTOSTART);
	frame.OPTION_DEBUG_CHECKBOX.setSelected(Settings.getInstance().OPTION_DEBUG);

	frame.OPTION_MENU_LAST_EP_CHECKBOX.setSelected(Settings.getInstance().OPTION_MENU_LAST_EP);
	frame.OPTION_MENU_NEXT_EP_FOR_ME.setSelected(Settings.getInstance().OPTION_MENU_NEXT_EP_FOR_ME);

	frame.OPTION_CONNECT_EKINO.setSelected(Settings.getInstance().OPTION_CONNECT_EKINO);
	frame.OPTION_CONNECT_PIRATEBAY.setSelected(Settings.getInstance().OPTION_CONNECT_PIRATEBAY);

	frame.NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_EP_ANNOUNCEMENT);
	frame.NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_EP_COUNTER);
	frame.NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_EP_RELEASED_TODAY);
	frame.NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_SEAS_RELEASED_TODAY);
	frame.NOTIFICATION_UPDATE_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_UPDATE);
    }

}
