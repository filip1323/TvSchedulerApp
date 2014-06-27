/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.TitledBorderPainter;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import local_data.Messages;

/**
 *
 * @author Filip
 */
public class ConfigFrame extends WebFrame {

    private GroupPanel tabGroup;
    private WebTabbedPane tabGroupPane;
    private MainOptionsTab mainOptionsTab;
    private ShowsManagerTab showsManagerTab;
    private NotificationsOptionTab notificationsOptionTab;
    private UserActionResponder userActionResponder;

    public void assignUserActionResponder(UserActionResponder userActionResponder) {
	this.userActionResponder = userActionResponder;
    }

    public void initComponents() {
	//setting frame
	setTitle("Opcje");
	setResizable(false);

	setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
	setRound(10);
	setShadeWidth(0);
	ComponentMoveAdapter.install(this);
	setShowMinimizeButton(false);

	//creating tab pane
	tabGroupPane = new WebTabbedPane();
	tabGroupPane.setTabPlacement(WebTabbedPane.TOP);
	tabGroupPane.setTabbedPaneStyle(TabbedPaneStyle.standalone);
	tabGroupPane.setRound(10);
	tabGroupPane.setFocusable(false);

	//content border
	final TitledBorderPainter titledBorderPainter = new TitledBorderPainter();
	titledBorderPainter.setTitleOffset(10);
	titledBorderPainter.setRound(Math.max(0, 8));
	titledBorderPainter.setMargin(10);
	titledBorderPainter.setWidth(0);
	    //this.setPainter(titledBorderPainter);

	//adding main options tab
	mainOptionsTab = new MainOptionsTab();
	mainOptionsTab.setPainter(titledBorderPainter);
	tabGroupPane.add(mainOptionsTab);

	//adding notification options tab
	notificationsOptionTab = new NotificationsOptionTab();
	notificationsOptionTab.setPainter(titledBorderPainter);
	tabGroupPane.add(notificationsOptionTab);

	//adding show manager tab
	showsManagerTab = new ShowsManagerTab();
	showsManagerTab.setPainter(titledBorderPainter);
	tabGroupPane.add(showsManagerTab);

	add(tabGroupPane);

	//inside padding
	tabGroupPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
    public WebCheckBox OPTION_AUTOSTART_CHECKBOX;
    public WebCheckBox OPTION_AUTOREFRESH_CHECKBOX;
    public WebCheckBox OPTION_DEBUG_CHECKBOX;

    public WebCheckBox OPTION_MENU_LAST_EP_CHECKBOX;
    public WebCheckBox OPTION_MENU_NEXT_EP_FOR_ME;

    public WebCheckBox OPTION_CONNECT_PIRATEBAY;
    public WebCheckBox OPTION_CONNECT_EKINO;

    class MainOptionsTab extends GroupPanel {

	public MainOptionsTab() {
	    setName("Ustawienia");
	    setContent();
	}

	public void setContent() {
	    //setting layout
	    this.setLayout(new VerticalFlowLayout(10, 10));

	    //autostart option
	    OPTION_AUTOSTART_CHECKBOX = new WebCheckBox(Messages.OPTION_AUTOSTART);
	    this.add(OPTION_AUTOSTART_CHECKBOX);

	    //autorefresh option
	    OPTION_AUTOREFRESH_CHECKBOX = new WebCheckBox(Messages.OPTION_AUTOREFRESH);
	    this.add(OPTION_AUTOREFRESH_CHECKBOX);

	    //debug option
	    OPTION_DEBUG_CHECKBOX = new WebCheckBox(Messages.OPTION_DEBUG);
	    this.add(OPTION_DEBUG_CHECKBOX);

	    this.add(new WebSeparator());

	    //autorefresh option
	    OPTION_MENU_LAST_EP_CHECKBOX = new WebCheckBox(Messages.OPTION_MENU_LAST_EP);
	    this.add(OPTION_MENU_LAST_EP_CHECKBOX);

	    //debug option
	    OPTION_MENU_NEXT_EP_FOR_ME = new WebCheckBox(Messages.OPTION_MENU_NEXT_EP_FOR_ME);
	    this.add(OPTION_MENU_NEXT_EP_FOR_ME);

	    this.add(new WebSeparator());

	    //thepiratebay
	    OPTION_CONNECT_PIRATEBAY = new WebCheckBox(Messages.OPTION_CONNECT_PIRATEBAY);
	    this.add(OPTION_CONNECT_PIRATEBAY);

	    //ekino
	    OPTION_CONNECT_EKINO = new WebCheckBox(Messages.OPTION_CONNECT_EKINO);
	    this.add(OPTION_CONNECT_EKINO);

	    OPTION_AUTOSTART_CHECKBOX.addActionListener(userActionResponder);
	    OPTION_AUTOREFRESH_CHECKBOX.addActionListener(userActionResponder);
	    OPTION_DEBUG_CHECKBOX.addActionListener(userActionResponder);

	    OPTION_MENU_LAST_EP_CHECKBOX.addActionListener(userActionResponder);
	    OPTION_MENU_NEXT_EP_FOR_ME.addActionListener(userActionResponder);

	    OPTION_CONNECT_PIRATEBAY.addActionListener(userActionResponder);
	    OPTION_CONNECT_EKINO.addActionListener(userActionResponder);

	}

    }

    class ShowsManagerTab extends GroupPanel {

	public ShowsManagerTab() {
	    setName("Zarządzaj serialami");
	    setContent();
	}

	public void setContent() {
	    //setting layout
	    this.setLayout(new VerticalFlowLayout(10, 10));

	}
    }

    public WebCheckBox NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX;
    public WebCheckBox NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX;
    public WebCheckBox NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX;
    public WebCheckBox NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX;
    public WebCheckBox NOTIFICATION_UPDATE_CHECKBOX;

    class NotificationsOptionTab extends GroupPanel {

	public NotificationsOptionTab() {
	    setName("Powiadomienia");
	    setContent();
	}

	public void setContent() {
	    //setting layout
	    this.setLayout(new VerticalFlowLayout(10, 10));

	    //counter option
	    NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_EP_COUNTER);
	    this.add(NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX);

	    //annouce notification
	    NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_EP_ANNOUNCEMENT);
	    this.add(NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX);

	    //premiere notification
	    NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_EP_RELEASED_TODAY);
	    this.add(NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX);

	    //season premiere notification
	    NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_SEAS_RELEASED_TODAY);
	    this.add(NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX);

	    //new client version notification
	    NOTIFICATION_UPDATE_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_UPDATE);
	    this.add(NOTIFICATION_UPDATE_CHECKBOX);

	    NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX.addActionListener(userActionResponder);
	    NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX.addActionListener(userActionResponder);
	    NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX.addActionListener(userActionResponder);
	    NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX.addActionListener(userActionResponder);
	    NOTIFICATION_UPDATE_CHECKBOX.addActionListener(userActionResponder);

	}
    }
}
