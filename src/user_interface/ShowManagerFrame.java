/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import action_responders.ConfigActionResponder;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebFrame;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import local_data.Messages;
import local_data.Resources;

/**
 *
 * @author Filip
 */
public class ShowManagerFrame extends WebFrame {

    private ConfigActionResponder userActionResponder;

    void assignUserActionResponder(ConfigActionResponder userActionResponder) {
	this.userActionResponder = userActionResponder;
    }
    GroupPanel content;
    GroupPanel ordinaryContent;
    GroupPanel addManagerContent;
    ShowManagerFrame owner;

    void initComponents() {

	owner = this;
	content = new GroupPanel(0, false);
	content.setMargin(5);
	ordinaryContent = new GroupPanel(0, false);
	setTitle("Manager Seriali");
	setResizable(false);

	setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
	setRound(10);
	setShadeWidth(0);
	ComponentMoveAdapter.install(this);
	setShowMinimizeButton(false);

	WebButton addShowButton = new WebButton();
	addShowButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		content.remove(ordinaryContent);
		owner.initAddShowComponents();
	    }
	});
	addShowButton.setText(Messages.MANAGER_ADD_SHOW);
	addShowButton.setFontSizeAndStyle(20, Font.BOLD);
	addShowButton.setHorizontalAlignment(SwingConstants.LEFT);
	addShowButton.setIcon(Resources.getImageIcon("plus.png"));
	ordinaryContent.add(addShowButton);

	WebButton removeShowButton = new WebButton();
	removeShowButton.addActionListener(userActionResponder);
	removeShowButton.setText(Messages.MANAGER_REMOVE_SHOW);
	removeShowButton.setFontSizeAndStyle(20, Font.BOLD);
	removeShowButton.setHorizontalAlignment(SwingConstants.LEFT);
	removeShowButton.setIcon(Resources.getImageIcon("delete.png"));
	ordinaryContent.add(removeShowButton);

	content.add(ordinaryContent);

	add(content);

	//-------------------------------FINAL-------------------------------//
	setBounds(new Rectangle(getPreferredSize()));

	//getting windows size
	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) scrnSize.getWidth();
	int height = (int) scrnSize.getHeight();

	//getting taksbar size
	Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	int taskBarHeight = scrnSize.height - winSize.height;

	//setting ui location on screen
	int xLocation = (width - (int) getPreferredSize().getWidth()) / 2;
	int yLocation = (height - (int) getPreferredSize().getHeight() - taskBarHeight) / 2;
	setLocation(xLocation, yLocation);
    }

    private void initAddShowComponents() {
	addManagerContent = new GroupPanel(5, false);

	content.add(0, addManagerContent);
    }

}
