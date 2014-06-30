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
import javax.swing.JPanel;
import javax.swing.JWindow;
import local_data.Properties;

/**
 *
 * @author Filip
 */
public class TransparentWindow extends JWindow {

    public TransparentWindow() {
//	//getting windows size
	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();

//	//getting windows size
//	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
//	int width = (int) scrnSize.getWidth();
//	int height = (int) scrnSize.getHeight();
//
//	//getting taksbar size
//	Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
//	int taskBarHeight = scrnSize.height - winSize.height;
	//creating transparent jwindow
	setAlwaysOnTop(Properties.getInstance().OPTION_ALWAYS_ON_TOP.getValue());
	setSize(scrnSize);
//	setSize(width, height - taskBarHeight);
	setBackground(new Color(0, 0, 0, 0));
	setLayout(null);

//	JPanel absoluteContainer = new JPanel();
//	absoluteContainer.setSize(scrnSize);
//	absoluteContainer.setLayout(null);
//	setContentPane(absoluteContainer);
    }

    public void addPanel(JPanel panel) {

	//getting windows size
	Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) scrnSize.getWidth();
	int height = (int) scrnSize.getHeight();

	//getting taksbar size
	Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	int taskBarHeight = scrnSize.height - winSize.height;

	//setting ui location on screen
	int xLocation = width - (int) panel.getPreferredSize().getWidth();
	int yLocation = height - (int) panel.getPreferredSize().getHeight() - taskBarHeight;
	panel.setBounds(new Rectangle(panel.getPreferredSize()));
	panel.setLocation(xLocation, yLocation);
	add(panel);

    }
}
