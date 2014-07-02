/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.auth;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.label.WebLabel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import local_data.Resources;

/**
 *
 * @author Filip
 */
public class AuthFrame extends WebFrame {

    private GroupPanel contentPane;
    private AuthFrame mainFrame;

    public void noConnection() {
	setContent(new NoConnectionContent());
    }

    public void noAuthorization() {
	setContent(new Decline());
    }

    public void connected() {
	setContent(new Wait());
    }

    public void accesGranted() {
	setContent(new Accept());
    }

    public void initComponents() {

	mainFrame = this;
	contentPane = new GroupPanel(0, false);
	contentPane.setMargin(5);
	setTitle("Autoryzacja");
	setResizable(false);

	setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
	setRound(10);
	setShadeWidth(0);
	ComponentMoveAdapter.install(this);
	setShowMinimizeButton(false);

	add(contentPane);

	setContent(new ConnectionContent());
    }

    private void setContent(GroupPanel panel) {
	contentPane.removeAll();
	contentPane.add(panel);
	autoResize();

    }

    void autoResize() {
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

	setVisible(false);
	setBounds(new Rectangle(getPreferredSize()));

	setLocation(xLocation, yLocation);
	setVisible(true);
    }

    class ConnectionContent extends GroupPanel {

	public ConnectionContent() {
	    final WebProgressBar progressBar = new WebProgressBar();
	    progressBar.setIndeterminate(true);
	    progressBar.setStringPainted(true);
	    progressBar.setPreferredWidth(400);
	    progressBar.setPreferredHeight(100);
	    progressBar.setString("Trwa łączenie z serwerem, proszę czekać... ");
	    add(progressBar);

	}

    }

    class NoConnectionContent extends GroupPanel {

	public NoConnectionContent() {
	    WebLabel header = new WebLabel();
	    header.setIcon(Resources.getImageIcon("ban.png"));
	    header.setText("Brak połączenia z serwerem, spróbuj później.");
	    header.setFontSize(20);
	    setMargin(10);
	    add(header);
	}

    }

    class Decline extends GroupPanel {

	public Decline() {
	    WebLabel header = new WebLabel();
	    header.setIcon(Resources.getImageIcon("ban.png"));
	    header.setText("Brak autoryzacji.");
	    header.setFontSize(20);
	    add(header);
	    setMargin(10);
	}
    }

    class Accept extends GroupPanel {

	public Accept() {
	    WebLabel header = new WebLabel();
	    header.setIcon(Resources.getImageIcon("circle-check.png"));
	    header.setText("Proces autoryzacji zakończony sukcesem.");
	    header.setFontSize(20);
	    add(header);
	    setMargin(10);
	}
    }

    class Wait extends GroupPanel {

	public Wait() {
	    final WebProgressBar progressBar = new WebProgressBar();
	    progressBar.setIndeterminate(true);
	    progressBar.setStringPainted(true);
	    progressBar.setPreferredWidth(400);
	    progressBar.setPreferredHeight(100);
	    progressBar.setString("Nawiązano połączenie z serwerem, proszę czekać... ");
	    add(progressBar);

	}
    }

}
