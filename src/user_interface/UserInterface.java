/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.managers.notification.NotificationListener;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.NotificationOption;
import com.alee.managers.notification.WebNotificationPopup;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import local_data.Resources;
import logic.MainController;
import show_components.show.Show;
import user_interface.scheduler.SchedulerPanel;
import user_interface.scheduler.ShowPanelCreator;

/**
 *
 * @author Filip
 */
public class UserInterface {

    // private ConfigActionResponder responder;
    private TrayMenu trayMenu;
    private TransparentWindow window;
    private SchedulerPanel schedulerPanel;
    private ConfigFrame configFrame;
    private MainController mainController;

    public void assignMainController(MainController mainController) {
	this.mainController = mainController;
    }

    public void assignConfigFrame(ConfigFrame configFrame) {
	this.configFrame = configFrame;
    }
    private ShowManagerFrame showManagementFrame;

    private TrayIcon trayIcon;

    private boolean schedulerVisibleState = false;

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

	//creating configFrame
	configFrame.initComponents();

	//creating show manager
	showManagementFrame.initComponents();
	showManagementFrame.setVisible(false);

	//creating menu
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

    public void assignTrayMenu(TrayMenu trayMenu) {
	this.trayMenu = trayMenu;
    }

    public void assignShowManagerFrame(ShowManagerFrame showManagementFrame) {
	this.showManagementFrame = showManagementFrame;
    }

    public void reloadScheduler() {
	if (schedulerPanel != null) {
	    window.remove(schedulerPanel);
	}
	schedulerPanel = new SchedulerPanel();
	schedulerPanel.initComponents();
	window.addPanel(schedulerPanel);
    }
    WebNotificationPopup progressNotification;

    public void showUpdateProgressBar(String title) {
	if (progressNotification == null) {
	    progressNotification = new WebNotificationPopup();
	    progressNotification.setIcon(Resources.getImageIcon("logo.png"));
	    NotificationManager.setLocation(SwingConstants.SOUTH_WEST);
	    NotificationManager.showNotification(window, progressNotification);
	}
	WebProgressBar progressBar = new WebProgressBar();
	progressBar.setSize(100, 30);
	progressBar.setStringPainted(true);
	progressBar.setString(title);
	progressBar.setIndeterminate(true);
	progressNotification.setContent(new GroupPanel(5, false, new WebLabel("Aktualizacja..."), progressBar));
    }

    public void hideUpdateProgressBar() {
	if (progressNotification != null) {
	    progressNotification.acceptAndHide();
	}
    }

    public void suggestUpdate() {
	WebNotificationPopup notification = new WebNotificationPopup();
	notification.setContent("Chcesz zaktualizować program?");
	notification.setIcon(Resources.getImageIcon("logo.png"));
	notification.setOptions(NotificationOption.yes, NotificationOption.no);
	NotificationManager.setLocation(SwingConstants.SOUTH_WEST);
	NotificationManager.showNotification(window, notification);

	notification.addNotificationListener(new NotificationListener() {

	    @Override
	    public void accepted() {
		System.out.println("ACCEPT");
	    }

	    @Override
	    public void closed() {
		System.out.println("CLOSED");
	    }

	    @Override
	    public void optionSelected(NotificationOption option) {
		if (option.compareTo(option.yes) == 0) {
		    mainController.updateApplication();
		}
	    }
	});
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
	ShowPanelCreator showPanelCreator = new ShowPanelCreator();
	GroupPanel panel = showPanelCreator.getShowPanel(show);
	schedulerPanel.addShowPanel(show.getTitle(), panel);
	if (schedulerVisibleState) {
	    toggleScheduler();
	    toggleScheduler();
	}
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

    public TransparentWindow getWindow() {
	return window;
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

    public void showNotification(Component content, String icoName) {
	WebNotificationPopup notification = new WebNotificationPopup();
	notification.setContent(content);
	notification.setIcon(Resources.getImageIcon(icoName));
	NotificationManager.setLocation(SwingConstants.SOUTH_WEST);
	NotificationManager.showNotification(window, notification);

    }

    public void showNotification(String content, String icoName) {
	showNotification(new WebLabel(content), icoName);
    }

}
