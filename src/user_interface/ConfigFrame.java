/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import action_responders.ConfigActionResponder;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.TitledBorderPainter;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
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
import local_data.Settings;

/**
 *
 * @author Filip
 */
public class ConfigFrame extends WebFrame {
//TODO show x near episode panel

    private GroupPanel tabGroup;
    private WebTabbedPane tabGroupPane;
    private MainOptionsTab mainOptionsTab;
    private NotificationsOptionTab notificationsOptionTab;
    private ConfigActionResponder responder;

    public void assignResponder(ConfigActionResponder responder) {
	this.responder = responder;
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

	    WebLabel label1 = new WebLabel("Główne");
	    this.add(label1);

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

	    WebLabel label2 = new WebLabel("W informacjach o serialu");
	    this.add(label2);

	    //autorefresh option
	    OPTION_MENU_LAST_EP_CHECKBOX = new WebCheckBox(Messages.OPTION_MENU_LAST_EP);
	    this.add(OPTION_MENU_LAST_EP_CHECKBOX);

	    //debug option
	    OPTION_MENU_NEXT_EP_FOR_ME = new WebCheckBox(Messages.OPTION_MENU_NEXT_EP_FOR_ME);
	    this.add(OPTION_MENU_NEXT_EP_FOR_ME);

	    this.add(new WebSeparator());

	    WebLabel label3 = new WebLabel("Zewnętrzne serwisy");
	    this.add(label3);

	    //thepiratebay
	    OPTION_CONNECT_PIRATEBAY = new WebCheckBox(Messages.OPTION_CONNECT_PIRATEBAY);
	    this.add(OPTION_CONNECT_PIRATEBAY);

	    //ekino
	    OPTION_CONNECT_EKINO = new WebCheckBox(Messages.OPTION_CONNECT_EKINO);
	    this.add(OPTION_CONNECT_EKINO);

	    //add responder
	    OPTION_AUTOSTART_CHECKBOX.addActionListener(responder);
	    OPTION_AUTOREFRESH_CHECKBOX.addActionListener(responder);
	    OPTION_DEBUG_CHECKBOX.addActionListener(responder);

	    OPTION_MENU_LAST_EP_CHECKBOX.addActionListener(responder);
	    OPTION_MENU_NEXT_EP_FOR_ME.addActionListener(responder);

	    OPTION_CONNECT_PIRATEBAY.addActionListener(responder);
	    OPTION_CONNECT_EKINO.addActionListener(responder);

	    //settings checkboxes
	    OPTION_AUTOREFRESH_CHECKBOX.setSelected(Settings.getInstance().OPTION_AUTOREFRESH);
	    OPTION_AUTOSTART_CHECKBOX.setSelected(Settings.getInstance().OPTION_AUTOSTART);
	    OPTION_DEBUG_CHECKBOX.setSelected(Settings.getInstance().OPTION_DEBUG);

	    OPTION_MENU_LAST_EP_CHECKBOX.setSelected(Settings.getInstance().OPTION_MENU_LAST_EP);
	    OPTION_MENU_NEXT_EP_FOR_ME.setSelected(Settings.getInstance().OPTION_MENU_NEXT_EP_FOR_ME);

	    OPTION_CONNECT_EKINO.setSelected(Settings.getInstance().OPTION_CONNECT_EKINO);
	    OPTION_CONNECT_PIRATEBAY.setSelected(Settings.getInstance().OPTION_CONNECT_PIRATEBAY);

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

	    WebLabel label1 = new WebLabel("W informacjach o serialu");
	    this.add(label1);

	    //counter option
	    NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_EP_COUNTER);
	    this.add(NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX);

	    this.add(new WebSeparator());
	    WebLabel label2 = new WebLabel("O serialu");
	    this.add(label2);

	    //annouce notification
	    NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_EP_ANNOUNCEMENT);
	    this.add(NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX);

	    //premiere notification
	    NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_EP_RELEASED_TODAY);
	    this.add(NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX);

	    //season premiere notification
	    NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_NEXT_SEAS_RELEASED_TODAY);
	    this.add(NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX);

	    this.add(new WebSeparator());
	    WebLabel label3 = new WebLabel("Pozostałe");
	    this.add(label3);

	    //new client version notification
	    NOTIFICATION_UPDATE_CHECKBOX = new WebCheckBox(Messages.NOTIFICATION_UPDATE);
	    this.add(NOTIFICATION_UPDATE_CHECKBOX);

	    //responder
	    NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX.addActionListener(responder);
	    NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX.addActionListener(responder);
	    NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX.addActionListener(responder);
	    NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX.addActionListener(responder);
	    NOTIFICATION_UPDATE_CHECKBOX.addActionListener(responder);

	    //checkboxes
	    NOTIFICATION_NEXT_EP_ANNOUNCEMENT_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_EP_ANNOUNCEMENT);
	    NOTIFICATION_NEXT_EP_COUNTER_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_EP_COUNTER);
	    NOTIFICATION_NEXT_EP_RELEASED_TODAY_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_EP_RELEASED_TODAY);
	    NOTIFICATION_NEXT_SEAS_RELEASED_TODAY_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_NEXT_SEAS_RELEASED_TODAY);
	    NOTIFICATION_UPDATE_CHECKBOX.setSelected(Settings.getInstance().NOTIFICATION_UPDATE);

	}
    }
}
