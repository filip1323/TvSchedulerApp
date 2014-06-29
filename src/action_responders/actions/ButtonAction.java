/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_responders.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import show_components.ShowController;
import user_exceptions.DebugError;

/**
 *
 * @author Filip
 */
public class ButtonAction extends AbstractAction {

    public enum TYPE {

	OPEN_TVCOM_HOMEPAGE, OPEN_EKINO_HOMEPAGE, OPEN_PIRATEBAY_EPISODE, OPEN_EKINO_EPISODE, CHANGE_EPISODE_VIEWED_STATE, OPEN_TVCOM_EPISODE
    }
    private Object arg;
    private TYPE type;
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

    private ButtonAction(TYPE type) {
	this.type = type;
    }

    private ButtonAction(TYPE type, Object arg) {
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
	    case CHANGE_EPISODE_VIEWED_STATE:
		break;
	}
	System.out.println(type + ":" + arg);
    }

}
