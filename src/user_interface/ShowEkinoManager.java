/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextField;
import external_websites.WebsiteRepository;
import external_websites.ekino.EkinoResult;
import external_websites.ekino.EkinoSearchList;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import local_data.Resources;
import misc.Utils;
import show_components.ShowController;
import show_components.show.Show;
import user_exceptions.EmptyDocumentException;
import user_exceptions.WrongUrlException;

/**
 *
 * @author Filip
 */
public class ShowEkinoManager extends WebFrame {

    private GroupPanel contentPane;
    private ShowEkinoManager frame;
    private ShowController showController;
    private Show show;

    public void assignShowController(ShowController showController) {
	this.showController = showController;
    }

    public void initComponents(Show show) {
	this.show = show;
	frame = this;
	contentPane = new GroupPanel(0, false);
	contentPane.setMargin(5);
	setTitle("Manager Seriali");
	setResizable(false);

	setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
	setRound(10);
	setShadeWidth(0);
	ComponentMoveAdapter.install(this);
	setShowMinimizeButton(false);

	add(contentPane);

	setContent(new selectShowContent());
    }

    void manualInput() {
	//creating dialog
	WebLookAndFeel.setDecorateFrames(true);
	final WebFrame ekinoDialog = new WebFrame();
	ekinoDialog.setTitle("ekino.tv");
	ekinoDialog.setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
	ekinoDialog.setResizable(false);

	//content
	WebLabel header = new WebLabel("Wprowadź adres internetowy ");
	WebLabel header2 = new WebLabel("strony głównej serialu na ");
	WebLinkLabel link = new WebLinkLabel();
	link.setLink("http://www.ekino.tv/seriale-online.html");
	link.setText("www.ekino.tv");
	link.setIcon(null);
	final WebTextField field = new WebTextField(30);
	final WebButton button = new WebButton("Ok");

	//content itselfs
	GroupPanel content = new GroupPanel(GroupingType.none, 5, false, header, new GroupPanel(header2, link), field, button);
	content.setMargin(10);

	//final dialog
	ekinoDialog.setAlwaysOnTop(true);
	ekinoDialog.add(content);
	ekinoDialog.pack();
	ekinoDialog.setVisible(true);

	//location
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	ekinoDialog.setLocation(dim.width / 2 - ekinoDialog.getSize().width / 2, dim.height / 2 - ekinoDialog.getSize().height / 2);

	button.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		checkAndUpdateUrl(field.getText());
		ekinoDialog.dispose();
	    }
	});

	field.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		button.doClick();
	    }
	}
	);

    }

    private void checkAndUpdateUrl(String ekinoUrl) {

	if (ekinoUrl == null || ekinoUrl.trim().equals("")) {
	    //cant be empty
	    return;
	}

	if (!WebsiteRepository.getInstance().isUrlEkino(ekinoUrl)) {
	    //invalid url
	    return;
	}
	try {
	    if (!WebsiteRepository.getInstance().addDocument(ekinoUrl)) {
		//invalid url
		return;
	    }
	} catch (WrongUrlException ex) {
	    ex.printStackTrace();
	} catch (EmptyDocumentException ex) {
	    ex.printStackTrace();
	}

	show.edit().setEkinoUrl(ekinoUrl);
	showController.updateShow(show);

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

    private class selectShowContent extends GroupPanel {

	public selectShowContent() {
	    setOrientation(SwingConstants.VERTICAL);

	    // Simple indetrminate progress bar
	    final WebProgressBar progressBar = new WebProgressBar();
	    progressBar.setIndeterminate(true);
	    progressBar.setStringPainted(true);
	    progressBar.setPreferredWidth(400);
	    progressBar.setPreferredHeight(40);
	    progressBar.setString("Wyszukiwanie pasujących pozycji. Proszę czekać... ");

	    add(progressBar);

	    final GroupPanel innerContent = new GroupPanel(5, false);

	    new Thread(new Runnable() {

		@Override
		public void run() {
		    EkinoSearchList searchList = null;
		    try {
			searchList = new EkinoSearchList(show.getTitle());
		    } catch (WrongUrlException ex) {
			ex.printStackTrace();
		    }
		    ArrayList<EkinoResult> results = searchList.getResults();
		    if (results.size() == 0) {
			manualInput();
		    }

		    innerContent.add(toList(results));
		    remove(progressBar);
		    frame.autoResize();
		    add(innerContent);
		    if (getPreferredSize().height > 400) {
			remove(innerContent);
			WebScrollPane scroll = new WebScrollPane(innerContent);
			scroll.setPreferredHeight(350);
			scroll.setPreferredWidth(400);
			innerContent.setMargin(5);
			add(scroll);
		    }
		    frame.autoResize();
		}

	    }).start();

	}

	private ArrayList<GroupPanel> toList(ArrayList<EkinoResult> results) {
	    ArrayList<GroupPanel> list = new ArrayList<>();

	    for (final EkinoResult result : results) {
		//title
		WebLabel titleLabel = new WebLabel(result.getTitle());
		titleLabel.setFontSizeAndStyle(20, true, false);

//		    //thumb
//		    System.out.println(result.getImageUrl());
//		    URL url = new URL(result.getImageUrl());
//		    Image image = ImageIO.read(url);
//		    WebDecoratedImage thumb = new WebDecoratedImage(image);
//		    thumb.setRound(5);
		//ekinoLink
		WebLinkLabel tvcomLink = new WebLinkLabel("tv.com");
		tvcomLink.setLink(result.getShowHomepageUrl());

		//nextButton
		WebButton nextButton = new WebButton();
		nextButton.setText("Dalej");
		nextButton.setHorizontalAlignment(SwingConstants.CENTER);
		nextButton.setIcon(Resources.getImageIcon("caret-right.png"));
		nextButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			show.edit().setEkinoUrl(result.getShowHomepageUrl());
			showController.saveShow(show);
			Utils.Web.openWebpage(result.getShowHomepageUrl());
			frame.dispose();
		    }
		});

		//infogroup
		GroupPanel infoGroup = new GroupPanel(5, false, titleLabel, tvcomLink, new GroupPanel(GroupingType.fillFirst, new WebSeparator(), nextButton));
		infoGroup.setPreferredWidth(300);

		//panel
		GroupPanel panel = new GroupPanel(5, true, infoGroup);
		panel.setMargin(5, 0, 0, 0);
		list.add(panel);

	    }

	    return list;
	}

    }

}
