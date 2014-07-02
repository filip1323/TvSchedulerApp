/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.scheduler;

import com.alee.extended.panel.AccordionListener;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebAccordion;
import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class SchedulerPanel extends WebPanel {

    private GroupPanel contentGroupPanel;
    private WebButton exitButton;
    private WebAccordion accordion;
    private UserInterface userInterface;

    public void assignUserInterface(UserInterface userInterface) {
	this.userInterface = userInterface;
    }

    public void initComponents() {
	accordion = new WebAccordion();
	accordion.setMultiplySelectionAllowed(false);
	contentGroupPanel = new GroupPanel(4, accordion);
	accordion.setOrientation(0);
	add(contentGroupPanel);
	accordion.addAccordionListener(new AccordionListener() {

	    @Override
	    public void selectionChanged() {
		userInterface.hidePopOvers();
	    }
	});
    }

    public void addShowPanel(String title, GroupPanel content) {
	accordion.addPane(title, content);
	//content.setMargin(5);
    }

    public void removeShowPanel(String title) {
	int index = 0;
	for (WebCollapsiblePane pane : accordion.getPanes()) {
	    if (pane.getTitle().equals(title)) {
		accordion.removePane(index);
	    }
	    index++;
	}
	accordion.repaint();
	repaint();
    }
}
