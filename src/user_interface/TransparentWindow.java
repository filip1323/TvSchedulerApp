/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JWindow;

/**
 *
 * @author Filip
 */
public class TransparentWindow extends JWindow {

    public TransparentWindow() {
	//getting windows size
	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) scrnSize.getWidth();
	int height = (int) scrnSize.getHeight();

	//creating transparent jwindow
	setAlwaysOnTop(true);
	setSize(width, height);
	setBackground(new Color(0, 0, 0, 0));
	setLayout(null);

    }

    public void setContentPane(ContentPane contentPanel) {

	contentPanel.setBounds(new Rectangle(contentPanel.getPreferredSize()));

	//getting windows size
	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) scrnSize.getWidth();
	int height = (int) scrnSize.getHeight();

	//getting taksbar size
	Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	int taskBarHeight = scrnSize.height - winSize.height;

	//setting ui location on screen
	int xLocation = width - (int) contentPanel.getPreferredSize().getWidth();
	int yLocation = height - (int) contentPanel.getPreferredSize().getHeight() - taskBarHeight;
	contentPanel.setLocation(xLocation, yLocation);

	//adding ui to windowContainer
	add(contentPanel);

    }

    @Override
    public void show() {
	//showing ui
	setVisible(true);

    }

    @Override
    public void hide() {
	//hiding ui
	setVisible(false);

    }
}
