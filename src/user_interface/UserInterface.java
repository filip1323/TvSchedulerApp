/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import action_responders.ConfigActionResponder;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import local_data.Resources;
import show_components.show.Show;
import user_interface.manager.ShowManagerFrame;
import user_interface.scheduler.SchedulerPanel;
import user_interface.scheduler.ShowPanelCreator;

/**
 *
 * @author Filip
 */
public class UserInterface {

    private ConfigActionResponder responder;

    private TrayMenu trayMenu;
    private TransparentWindow window;
    private SchedulerPanel schedulerPanel;
    private ConfigFrame configFrame;
    private ShowManagerFrame showManagementFrame;
    private ShowPanelCreator showPanelCreator;

    private TrayIcon trayIcon;

    private boolean schedulerVisibleState = false;

    public void assignShowPanelCreator(ShowPanelCreator showPanelCreator) {
	this.showPanelCreator = showPanelCreator;
    }

    public void assignResponder(ConfigActionResponder responder) {
	this.responder = responder;
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
	schedulerPanel.assignResponder(responder);
	schedulerPanel.initComponents();

	//creating configFrame
	configFrame = new ConfigFrame();
	configFrame.assignResponder(responder);
	configFrame.initComponents();

	//creating show manager
	showManagementFrame = new ShowManagerFrame();
	showManagementFrame.initComponents();
	showManagementFrame.setVisible(false);

	//creating menu
	trayMenu = new TrayMenu();
	trayMenu.assignUserInterface(this);
	trayMenu.initComponents();

	//creating tray handler
	createTrayHandler();
	trayIcon.addMouseListener(new MouseListener() {

	    @Override
	    public void mousePressed(MouseEvent e) {
		if (e.getSource().getClass().equals(TrayIcon.class)) { //tray icon clicked
		    if (e.getButton() == MouseEvent.BUTTON1) {//left button
			toggleScheduler();
		    }
		}
	    }
	    //<editor-fold defaultstate="collapsed" desc="useless">

	    @Override
	    public void mouseClicked(MouseEvent e) {
		//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }

	    @Override
	    public void mouseReleased(MouseEvent e
	    ) {
		//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }

//</editor-fold>
	});
	trayIcon.setPopupMenu(trayMenu);
//	window.addPanel(menuPanel);
//	window.addPanel(schedulerPanel);
	window.setVisible(true);
    }

    private void createTrayHandler() {
	//img
	trayIcon = new java.awt.TrayIcon(new ImageIcon(Resources.getUrl("tray-icon.png")).getImage());
	//tray instance
	SystemTray tray = SystemTray.getSystemTray();
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

    public void showManager() {
	showManagementFrame.setVisible(true);
    }

    public SchedulerPanel getSchedulerPanel() {
	return schedulerPanel;
    }

    public ConfigFrame getConfigFrame() {
	return configFrame;
    }

    public ShowManagerFrame getShowManagementFrame() {
	return showManagementFrame;
    }

}
