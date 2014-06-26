/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import local_data.Resources;
import logic.Controller;
import show_components.ShowController;
import show_components.show.Show;
import user_interface.scheduler.SchedulerPanel;
import user_interface.scheduler.ShowPanelCreator;

/**
 *
 * @author Filip
 */
public class UserInterface {

    private ShowController showController;
    private Controller controller;
    private UserActionResponder userActionResponder;

    private TransparentWindow window;
    private SchedulerPanel schedulerPanel;
    private ConfigFrame configFrame;
    private MenuPanel menuPanel;
    private ShowPanelCreator showPanelCreator;

    public UserInterface() {
	showPanelCreator = new ShowPanelCreator();
    }

    public void assignShowController(ShowController showController) {
	this.showController = showController;
    }

    public void assignController(Controller controller) {
	this.controller = controller;
    }

    public void assignUserActionResponder(UserActionResponder userActionResponder) {
	this.userActionResponder = userActionResponder;
    }

    public void initComponents() {

	//setting look and feel
	try {
	    UIManager.setLookAndFeel(new WebLookAndFeel());
	} catch (UnsupportedLookAndFeelException ex) {
	    ex.printStackTrace();
	}
	//better frame decoration
	WebLookAndFeel.setDecorateFrames(true);

	//main transparent container
	window = new TransparentWindow();

	//creating scheduler panel(raw)
	schedulerPanel = new SchedulerPanel();
	schedulerPanel.assignUserActionResponder(userActionResponder);
	schedulerPanel.initComponents();

	//creating configFrame(raw)
	ConfigFrameConstuctor configFrameController = new ConfigFrameConstuctor();
	configFrameController.assignUserActionResponder(userActionResponder);
	configFrame = configFrameController.getConfigFrame();

	//creating menu
	menuPanel = new MenuPanel();
	menuPanel.assignUserInterface(this);
	menuPanel.assignController(controller);
	menuPanel.initComponents();

	//creating tray handler
	createTrayHandler();
	trayIcon.addMouseListener(userActionResponder);
	trayIcon.addActionListener(userActionResponder);
	trayIcon.setPopupMenu(menuPanel);
//	window.addPanel(menuPanel);
//	window.addPanel(schedulerPanel);
	window.setVisible(true);
    }

    private SystemTray tray;
    private TrayIcon trayIcon;
    private Image trayIconImage;

    private void createTrayHandler() {
	//img
	trayIcon = new java.awt.TrayIcon(new ImageIcon(Resources.getUrl("tray-icon.png")).getImage());
	//tray instance
	tray = SystemTray.getSystemTray();
	//auto size for img
	trayIcon.setImageAutoSize(true);
	//adding tray handle to tray bar
	try {
	    tray.add(trayIcon);
	} catch (AWTException ex) {
	    ex.printStackTrace();
	}
    }

    public void showConfig() {
	configFrame.setVisible(true);
    }

    public void hideConfig() {
	configFrame.setVisible(false);
    }

    private boolean schedulerVisibleState = false;

    public void showScheduler() {
	window.setVisible(false);
	window.addPanel(schedulerPanel);
	window.repaint();
	window.setVisible(true);
	schedulerVisibleState = true;
    }

    public void hideScheduler() {
	window.remove(schedulerPanel);
	window.repaint();
	schedulerVisibleState = false;
    }

    public void addShowTabToScheduler(Show show) {
	GroupPanel panel = showPanelCreator.getShowPanel(show);
	schedulerPanel.addShowPanel(show.getTitle(), panel);
    }

    public void toggleScheduler() {
	if (schedulerVisibleState) {
	    hideScheduler();
	} else {
	    showScheduler();
	}
    }

}
