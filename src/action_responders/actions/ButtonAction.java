/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_responders.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import local_data.Properties;
import local_data.Property;
import show_components.ShowController;
import user_exceptions.DebugError;

/**
 *
 * @author Filip
 */
public class ButtonAction extends AbstractAction {

    public enum Type {

	OPEN_TVCOM_HOMEPAGE,
	OPEN_EKINO_HOMEPAGE,
	OPEN_PIRATEBAY_EPISODE,
	OPEN_EKINO_EPISODE,
	OPEN_TVCOM_EPISODE,
	CHANGE_SETTINGS
    }
    private Object arg;
    private Type type;
    static private ShowController showController;
//TODO

    static public void assign(ShowController showController) {
	ButtonAction.showController = showController;
    }

    static public ButtonAction getInstance() {
	if (showController == null) {
	    throw new DebugError("show controller not initialized");
	}
	return new ButtonAction();
    }

    private ButtonAction() {

    }

    private ButtonAction(Type type) {
	this.type = type;
    }

    public ButtonAction(Type type, Object arg) {
	this.type = type;
	this.arg = arg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	switch (type) {
	    case OPEN_TVCOM_HOMEPAGE:
		//TODO
		break;
	    case OPEN_EKINO_HOMEPAGE:
		break;
	    case OPEN_PIRATEBAY_EPISODE:
		break;
	    case OPEN_EKINO_EPISODE:
		break;
	    case CHANGE_SETTINGS:
		//System.out.println(arg);
		Property prop = ((Property) arg);
		prop.toggle();
		Properties.getInstance().saveMe();
		break;
	}
    }

}
