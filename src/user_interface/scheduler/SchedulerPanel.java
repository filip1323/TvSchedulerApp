/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.scheduler;

import action_responders.ConfigActionResponder;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebAccordion;
import com.alee.extended.panel.WebAccordionStyle;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;

/**
 *
 * @author Filip
 */
public class SchedulerPanel extends WebPanel {

    private GroupPanel contentGroupPanel;
    private WebButton exitButton;
    private ConfigActionResponder responder;
    private WebAccordion accordion;

    public void assignResponder(ConfigActionResponder responder) {
	this.responder = responder;
    }

    public void initComponents() {
	accordion = new WebAccordion(WebAccordionStyle.accordionStyle);
	accordion.setMultiplySelectionAllowed(false);

	contentGroupPanel = new GroupPanel(4, accordion);
	accordion.setOrientation(0);
	add(contentGroupPanel);
	//setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
    }

    public void addShowPanel(String title, GroupPanel content) {
	accordion.addPane(title, content);
	//content.setMargin(5);
    }
}
