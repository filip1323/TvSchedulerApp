/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class MainController {

    private UserInterface userInterface;

    public void shutdown() {
	System.exit(0);
    }

    public void assignUserInterface(UserInterface ui) {
	this.userInterface = ui;
    }

    public void start() {
	userInterface.initComponents();;
    }

}
