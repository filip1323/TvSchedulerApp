/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.Controller;

/**
 *
 * @author Filip
 */
public class MenuPanel extends PopupMenu {

    private UserInterface userInterface;
    private Controller controller;
    private WebButton exitButton;
    private GroupPanel contentGroupPanel;

    public void assignUserInterface(UserInterface userInterface) {
	this.userInterface = userInterface;
    }

    public void assignController(Controller controller) {
	this.controller = controller;
    }

    void initComponents() {
	MenuItem exitButton = new MenuItem("Wyjście");
	MenuItem settingsButton = new MenuItem("Ustawienia");
	exitButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		controller.shutdown();
	    }
	});

	settingsButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		userInterface.showConfig();
	    }
	});

	this.add(settingsButton);
	this.add(exitButton);
    }

}
