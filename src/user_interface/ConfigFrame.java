/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import action_responders.ConfigActionResponder;
import action_responders.actions.ButtonAction;
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
import local_data.Properties;
import local_data.Property;

/**
 *
 * @author Filip
 */
public class ConfigFrame extends WebFrame {

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
	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_AUTOSTART));
	    this.getLastComponent().setEnabled(false);
	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_AUTOREFRESH));
	    this.getLastComponent().setEnabled(false);
	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_DEBUG));
	    this.getLastComponent().setEnabled(false);

	    this.add(new WebSeparator());

	    WebLabel label2 = new WebLabel("W informacjach o serialu");
	    this.add(label2);

	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_MENU_LAST_EP));
	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_MENU_MARKED_EPISODE));

	    this.add(new WebSeparator());

	    WebLabel label3 = new WebLabel("Zewnętrzne serwisy");
	    this.add(label3);
	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_CONNECT_EKINO));
	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_CONNECT_PIRATEBAY));

	}

    }

    WebCheckBox getPropertyCheckbox(Property prop) {
	WebCheckBox option = new WebCheckBox();
	option.setAction(new ButtonAction(ButtonAction.Type.CHANGE_SETTINGS, prop));
	option.setText(prop.getText());
	option.setSelected(prop.getValue());
	return option;
    }

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

	    this.add(getPropertyCheckbox(Properties.getInstance().OPTION_INFO_NEXT_EP_COUNTER));

	    this.add(new WebSeparator());
	    WebLabel label2 = new WebLabel("O serialu");
	    this.add(label2);

	    this.add(getPropertyCheckbox(Properties.getInstance().NOTIFICATION_NEXT_EP_ANNOUNCEMENT));
	    this.add(getPropertyCheckbox(Properties.getInstance().NOTIFICATION_NEXT_EP_RELEASED_TODAY));
	    this.add(getPropertyCheckbox(Properties.getInstance().NOTIFICATION_NEXT_SEASON_ANNOUNCEMENT));

	    this.add(new WebSeparator());
	    WebLabel label3 = new WebLabel("Pozostałe");
	    this.add(label3);
	    this.add(getPropertyCheckbox(Properties.getInstance().NOTIFICATION_UPDATE));

	}
    }
}
