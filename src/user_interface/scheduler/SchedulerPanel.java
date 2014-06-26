/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.scheduler;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebAccordion;
import com.alee.extended.panel.WebAccordionStyle;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import user_interface.UserActionResponder;

/**
 *
 * @author Filip
 */
public class SchedulerPanel extends WebPanel {

    private GroupPanel contentGroupPanel;
    private WebButton exitButton;
    private UserActionResponder userActionResponder;
    private WebAccordion accordion;

    public void assignUserActionResponder(UserActionResponder userActionResponder) {
	this.userActionResponder = userActionResponder;
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
