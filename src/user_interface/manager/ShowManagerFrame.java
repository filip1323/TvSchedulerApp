/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.manager;

import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbLabel;
import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.progress.WebStepProgress;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextField;
import com.alee.managers.notification.NotificationManager;
import external_websites.tvcom.Result;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import local_data.Resources;
import show_components.ShowController;
import show_components.ShowOnlineEngineer;
import show_components.show.Show;
import user_exceptions.DataNotAssignedException;
import user_exceptions.DebugError;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class ShowManagerFrame extends WebFrame {

    private GroupPanel contentPane;
    private ShowManagerFrame managerFrame;
    private UserInterface userInterface;
    ShowController showController;

    public void assignShowController(ShowController showController) {
	this.showController = showController;
    }

    public void assignUserInterface(UserInterface userInterface) {
	this.userInterface = userInterface;
    }

    public void initComponents() {

	managerFrame = this;
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

	setContent(new menuContent(managerFrame));
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

    class menuContent extends GroupPanel {

	public menuContent(final ShowManagerFrame managerFrame) {

	    WebButton addShowButton = new WebButton();
	    addShowButton.setText("Dodaj serial");
	    addShowButton.setFontSizeAndStyle(20, Font.BOLD);
	    addShowButton.setHorizontalAlignment(SwingConstants.LEFT);
	    addShowButton.setIcon(Resources.getImageIcon("plus.png"));

	    addShowButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    managerFrame.setContent(new ShowAddContent(managerFrame));
		}
	    });

	    WebButton removeShowButton = new WebButton();
	    removeShowButton.setText("Usuń serial");
	    removeShowButton.setFontSizeAndStyle(20, Font.BOLD);
	    removeShowButton.setHorizontalAlignment(SwingConstants.LEFT);
	    removeShowButton.setIcon(Resources.getImageIcon("delete.png"));

	    removeShowButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    managerFrame.setContent(new ShowRemoveContent());
		}
	    });

	    setOrientation(SwingConstants.VERTICAL);
	    add(addShowButton);
	    add(removeShowButton);

	}
    }

    class ShowRemoveContent extends GroupPanel {

	WebButton backButton;

	public ShowRemoveContent() {

	    setOrientation(SwingConstants.VERTICAL);

	    WebLabel headerLabel = new WebLabel("Lista seriali");
	    headerLabel.setFontSize(20);
	    add(headerLabel);

	    GroupPanel innerContent = new GroupPanel(GroupingType.fillAll, 5, false);
	    ShowOnlineEngineer engie = new ShowOnlineEngineer();

	    backButton = new WebButton();
	    backButton.setText("Cofnij");
	    backButton.setHorizontalAlignment(SwingConstants.CENTER);
	    backButton.setIcon(Resources.getImageIcon("caret-left.png"));
	    backButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

		    managerFrame.setContent(new menuContent(managerFrame));
		    backButton.removeActionListener(this);
		}
	    });

	    ArrayList<Show> shows = showController.getStoredShows();
	    if (shows.size() == 0) {
		EventQueue.invokeLater(new Runnable() {

		    @Override
		    public void run() {
			backButton.doClick();
		    }
		});
		NotificationManager.showNotification((JWindow) userInterface.getWindow(), "Baza seriali jest pusta.", Resources.getImageIcon("info.png"));

	    }
	    innerContent.add(toList(shows));
	    add(innerContent);
	    if (getPreferredSize().height > 300) {
		remove(innerContent);
		WebScrollPane scroll = new WebScrollPane(innerContent);
		scroll.setPreferredHeight(350);
		scroll.setPreferredWidth(500);
		innerContent.setMargin(5);
		add(scroll);
	    }
	    add(backButton);

	}

	private ArrayList<GroupPanel> toList(ArrayList<Show> shows) {
	    ArrayList<GroupPanel> list = new ArrayList<>();

	    try {
		for (final Show show : shows) {
		    //title
		    WebLabel titleLabel = new WebLabel(show.getTitle());
		    titleLabel.setFontSizeAndStyle(20, true, false);

		    //thumb
		    URL url = new URL(show.getThumbUrl());
		    Image image = ImageIO.read(url);
		    WebDecoratedImage thumb = new WebDecoratedImage(image);
		    thumb.setRound(5);

		    //tvcomLink
		    WebLinkLabel tvcomLink = new WebLinkLabel("tv.com");
		    tvcomLink.setLink(show.getTvcomUrl());

		    //removeButton
		    WebButton removeButton = new WebButton();
		    removeButton.setText("Usuń");
		    removeButton.setHorizontalAlignment(SwingConstants.CENTER);
		    removeButton.setIcon(Resources.getImageIcon("delete-small.png"));

		    //infogroup
		    GroupPanel infoGroup = new GroupPanel(5, false, titleLabel, tvcomLink, new GroupPanel(GroupingType.fillFirst, new WebSeparator(), removeButton));
		    infoGroup.setPreferredWidth(300);

		    //panel
		    GroupPanel panel = new GroupPanel(5, true, thumb, infoGroup);
		    panel.setMargin(5, 0, 0, 0);
		    list.add(panel);

		    //buttons settings
		    removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    int state = WebOptionPane.showConfirmDialog(managerFrame, "Jesteś pewien że chcesz usunąć " + show.getTitle() + "?", "Usuwanie " + show.getTitle(),
				    WebOptionPane.YES_NO_CANCEL_OPTION, WebOptionPane.QUESTION_MESSAGE);
			    if (state == WebOptionPane.YES_OPTION) {
				showController.removeShow(show);
				setContent(new ShowRemoveContent());
			    }
			}
		    });

		}
	    } catch (MalformedURLException ex) {
		ex.printStackTrace();
	    } catch (IOException ex) {
		ex.printStackTrace();
	    }
	    return list;
	}

    }

    class ShowAddContent extends GroupPanel {

	ShowManagerFrame managerFrame;
	WebBreadcrumb progressBreadcrumb;
	WebButton backButton;
	WebButton nextButton;
	GroupPanel contentPane;

	WebBreadcrumbLabel titleLabel;
	WebBreadcrumbLabel showLabel;
	WebBreadcrumbLabel confirmLabel;
	WebBreadcrumbLabel downloadLabel;
	WebBreadcrumbLabel finishLabel;

	public ShowAddContent(ShowManagerFrame managerFrame) {
	    this.managerFrame = managerFrame;
	    progressBreadcrumb = new WebBreadcrumb(true);

	    titleLabel = new WebBreadcrumbLabel("Wprowadź tytuł");
	    showLabel = new WebBreadcrumbLabel("Wybierz serial");
	    confirmLabel = new WebBreadcrumbLabel("Potwierdzenie");
	    downloadLabel = new WebBreadcrumbLabel("Zapisywanie");
	    finishLabel = new WebBreadcrumbLabel("Koniec");

	    progressBreadcrumb.add(titleLabel);
	    progressBreadcrumb.add(showLabel);
	    progressBreadcrumb.add(confirmLabel);
	    progressBreadcrumb.add(downloadLabel);
	    progressBreadcrumb.add(finishLabel);

	    nextButton = new WebButton();
	    nextButton.setText("Dalej");
	    nextButton.setHorizontalAlignment(SwingConstants.CENTER);
	    nextButton.setIcon(Resources.getImageIcon("caret-right.png"));

	    backButton = new WebButton();
	    backButton.setText("Cofnij");
	    backButton.setHorizontalAlignment(SwingConstants.CENTER);
	    backButton.setIcon(Resources.getImageIcon("caret-left.png"));

	    GroupPanel bottomGroup = new GroupPanel(GroupingType.fillMiddle, true, backButton, new WebSeparator(false, false), nextButton);

	    contentPane = new GroupPanel(GroupingType.fillAll);
	    contentPane.setMargin(10);

	    setContent(new enterTitleContent(this));

	    setOrientation(SwingConstants.VERTICAL);
	    add(progressBreadcrumb);
	    add(contentPane);
	    add(bottomGroup);
	}

	private void setContent(GroupPanel panel) {

	    contentPane.removeAll();
	    contentPane.add(panel);
	    managerFrame.autoResize();
	}

	private class enterTitleContent extends GroupPanel {

	    public enterTitleContent(final ShowAddContent owner) {
		//progress breadcrumb settings
		owner.titleLabel.setBoldFont();
		owner.showLabel.setFontStyle(Font.PLAIN);

		//label
		WebLabel titleLabel = new WebLabel("Tytuł serialu:");

		//field
		final WebTextField titleField = new WebTextField();
		titleField.setPreferredWidth(300);
		titleField.setInputPrompt("Tytuł...");
		titleField.setMargin(0, 0, 0, 2);
		titleField.setTrailingComponent(Resources.getWebImage("double-quote-sans-left.png"));
		titleField.requestFocusInWindow();
		titleField.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			nextButton.doClick();
		    }
		});
		add(new GroupPanel(GroupingType.fillLast, 5, titleLabel, titleField));

		//buttons settings
		nextButton.setVisible(true);
		for (ActionListener ac : backButton.getActionListeners()) {
		    backButton.removeActionListener(ac);
		}
		owner.backButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

			managerFrame.setContent(new menuContent(managerFrame));
			backButton.removeActionListener(this);
		    }
		});

		for (ActionListener ac : nextButton.getActionListeners()) {
		    nextButton.removeActionListener(ac);
		}
		owner.nextButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			if (titleField.getText().trim().equals("")) {
			    return;
			}
			setContent(new selectShowContent(owner, titleField.getText()));
			nextButton.removeActionListener(this);
		    }
		});
	    }
	}

	private class selectShowContent extends GroupPanel {

	    private final ShowAddContent owner;

	    public selectShowContent(final ShowAddContent owner, final String title) {
		this.owner = owner;
		setOrientation(SwingConstants.VERTICAL);

		//progress breadcrumb settings
		owner.showLabel.setBoldFont();
		owner.confirmLabel.setFontStyle(Font.PLAIN);

		// Simple indetrminate progress bar
		final WebProgressBar progressBar = new WebProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setPreferredWidth(owner.progressBreadcrumb.getWidth());
		progressBar.setPreferredHeight(40);
		progressBar.setString("Wyszukiwanie pasujących pozycji. Proszę czekać... ");

		add(progressBar);

		final GroupPanel innerContent = new GroupPanel(5, false);

		new Thread(new Runnable() {

		    @Override
		    public void run() {
			//engineer
			ShowOnlineEngineer engie = new ShowOnlineEngineer();
			try {
			    ArrayList<Result> results = (engie.getResults(title));
			    if (results.size() == 0) {
				backButton.doClick();
				NotificationManager.showNotification((JWindow) userInterface.getWindow(), "Brak wyników dla `" + title + "`. Spróbuj ponownie.", Resources.getImageIcon("info.png"));

			    }
			    innerContent.add(toList(results));
			    remove(progressBar);
			    owner.managerFrame.autoResize();
			} catch (DataNotAssignedException ex) {
			    ex.printStackTrace();
			    throw new DebugError("Maybe more user input filtering");
			}
			add(innerContent);
			if (getPreferredSize().height > 300) {
			    remove(innerContent);
			    WebScrollPane scroll = new WebScrollPane(innerContent);
			    scroll.setPreferredHeight(350);
			    scroll.setPreferredWidth(500);
			    innerContent.setMargin(5);
			    add(scroll);
			}
			owner.managerFrame.autoResize();
		    }

		}).start();

		//buttons settings
		for (ActionListener ac : backButton.getActionListeners()) {
		    backButton.removeActionListener(ac);
		}
		owner.backButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			setContent(new enterTitleContent(owner));
			backButton.removeActionListener(this);
		    }
		});

		owner.nextButton.setVisible(false);
	    }

	    private ArrayList<GroupPanel> toList(ArrayList<Result> results) {
		ArrayList<GroupPanel> list = new ArrayList<>();

		try {
		    for (final Result result : results) {
			//title
			WebLabel titleLabel = new WebLabel(result.getTitle());
			titleLabel.setFontSizeAndStyle(20, true, false);

			//thumb
			URL url = new URL(result.getImageUrl());
			Image image = ImageIO.read(url);
			WebDecoratedImage thumb = new WebDecoratedImage(image);
			thumb.setRound(5);

			//tvcomLink
			WebLinkLabel tvcomLink = new WebLinkLabel("tv.com");
			tvcomLink.setLink(result.getShowHomepageUrl());

			//nextButton
			WebButton nextButton = new WebButton();
			nextButton.setText("Dalej");
			nextButton.setHorizontalAlignment(SwingConstants.CENTER);
			nextButton.setIcon(Resources.getImageIcon("caret-right.png"));

			//infogroup
			GroupPanel infoGroup = new GroupPanel(5, false, titleLabel, tvcomLink, new GroupPanel(GroupingType.fillFirst, new WebSeparator(), nextButton));
			infoGroup.setPreferredWidth(300);

			//panel
			GroupPanel panel = new GroupPanel(5, true, thumb, infoGroup);
			panel.setMargin(5, 0, 0, 0);
			list.add(panel);

			//buttons settings
			for (ActionListener ac : owner.nextButton.getActionListeners()) {
			    owner.nextButton.removeActionListener(ac);
			}
			nextButton.addActionListener(new ActionListener() {

			    @Override
			    public void actionPerformed(ActionEvent e) {
				setContent(new confirmResultContent(owner, result));
				owner.nextButton.setVisible(true);
			    }
			});

		    }
		} catch (MalformedURLException ex) {
		    ex.printStackTrace();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		return list;
	    }

	}

	class confirmResultContent extends GroupPanel {

	    private confirmResultContent(final ShowAddContent owner, final Result result) {

		//progress breadcrumb settings
		owner.confirmLabel.setBoldFont();
		owner.downloadLabel.setFontStyle(Font.PLAIN);

		try {
		    setOrientation(SwingConstants.VERTICAL);
		    setGap(10);
		    ShowOnlineEngineer engie = new ShowOnlineEngineer();

		    //header
		    WebLabel headerLabel = new WebLabel("Jesteś pewien że to ten serial?");
		    headerLabel.setFontSize(20);
		    add(headerLabel);

		    //thumb
		    URL url = new URL(result.getImageUrl());
		    Image image = ImageIO.read(url);
		    WebDecoratedImage thumb = new WebDecoratedImage(image);
		    thumb.setHorizontalAlignment(SwingConstants.LEFT);
		    thumb.setRound(5);
		    add(thumb);

		    //title
		    WebLabel titleLabel = new WebLabel(result.getTitle());
		    titleLabel.setFontSizeAndStyle(16, false, true);
		    add(titleLabel);

		    //link
		    WebLinkLabel tvcomLink = new WebLinkLabel("tv.com");
		    tvcomLink.setLink(result.getShowHomepageUrl());
		    add(tvcomLink);

		} catch (MalformedURLException ex) {
		    ex.printStackTrace();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		//buttons settings
		for (ActionListener ac : backButton.getActionListeners()) {
		    backButton.removeActionListener(ac);
		}
		owner.backButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			setContent(new selectShowContent(owner, result.getTitle()));
			backButton.removeActionListener(this);
		    }
		});
		owner.nextButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			setContent(new saveShowContent(owner, result));
			nextButton.removeActionListener(this);
		    }
		});

		for (Show show : showController.getStoredShows()) {
		    if (show.getTvcomUrl().equals(result.getShowHomepageUrl())) {
			NotificationManager.showNotification((JWindow) userInterface.getWindow(), "Serial `" + show.getTitle() + "` został dodany wcześniej.", Resources.getImageIcon("info.png"));
			EventQueue.invokeLater(new Runnable() {
			    @Override
			    public void run() {
				backButton.doClick();
			    }
			});
			return;
		    }
		}

	    }

	}

	class saveShowContent extends GroupPanel {

	    private saveShowContent(final ShowAddContent owner, final Result result) {

		//progress breadcrumb settings
		owner.downloadLabel.setBoldFont();

		nextButton.setVisible(false);
		backButton.setVisible(false);

		// Simple indetrminate progress bar
		final WebProgressBar progressBar = new WebProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setPreferredWidth(owner.progressBreadcrumb.getWidth());
		progressBar.setPreferredHeight(100);
		progressBar.setString("Proszę czekać... ");
		add(progressBar);

		new Thread(new Runnable() {

		    @Override
		    public void run() {
			final ShowOnlineEngineer engie = new ShowOnlineEngineer();
			final Show show = engie.getBasicInfo(result);
			remove(progressBar);

			//creating fancy gui
			WebLabel headerLabel = new WebLabel("Pobieranie danych o serialu " + show.getTitle());
			headerLabel.setFontSize(16);

			final WebLabel endingLabel = new WebLabel("Zapisywanie danych o serialu " + show.getTitle());
			endingLabel.setFontSize(16);
			endingLabel.setVisible(false);

			final WebStepProgress mainStepProgress = new WebStepProgress(headerLabel);
			final ArrayList<WebProgressBar> progressBars = new ArrayList<>();
			for (int ordinal = 1; ordinal <= show.getSeasonsNumber(); ordinal++) {
			    WebProgressBar seasonBar = new WebProgressBar();
			    seasonBar.setStringPainted(true);
			    seasonBar.setPreferredWidth(300);
			    seasonBar.setPreferredHeight(30);

			    seasonBar.setString("Sezon " + ordinal);
			    mainStepProgress.addSteps(seasonBar);
			    progressBars.add(seasonBar);
			}
			mainStepProgress.addSteps(endingLabel);

			//mainStepProgress.setSelectedStep(0);
			mainStepProgress.setSelectionEnabled(false);
			mainStepProgress.setSpacing(50);
			//mainStepProgress.setLabelsPosition(WebStepProgress.LEADING);
			mainStepProgress.setOrientation(WebStepProgress.VERTICAL);
			mainStepProgress.setLabelsPosition(WebStepProgress.TRAILING);
			mainStepProgress.setStepControlRound(2);
			mainStepProgress.setStepControlFillRound(0);
			Dimension size = mainStepProgress.getPreferredSize();
			size.height = show.getSeasonsNumber() * 50;
			mainStepProgress.setPreferredSize(size);

			add(mainStepProgress);
			owner.managerFrame.autoResize();

			new Thread(new Runnable() {

			    @Override
			    public void run() {

				while (engie.hasNextEpisode()) {
				    int seasonOrdinal = engie.getCurrentSeasonOrdinal();
				    int progress = engie.getSeasonProgress();
				    int episodeOrdinal = engie.getCurrentEpisodeOrdinal();
				    if (seasonOrdinal <= progressBars.size() && episodeOrdinal != 1) {
					progressBars.get(seasonOrdinal - 1).setValue(progress);
				    }
				    mainStepProgress.setSelectedStep(seasonOrdinal - 1);
				    engie.saveNextEpisode();
				}
				mainStepProgress.setSelectedStep(engie.getCurrentSeasonOrdinal());
				showController.saveShow(engie.getShow());
				endingLabel.setVisible(true);
				mainStepProgress.setSelectedStep(engie.getCurrentSeasonOrdinal() + 1);
				mainStepProgress.setProgress(100);
				mainStepProgress.setTotalProgress(100);
				nextButton.setVisible(true);

			    }

			}).start();

		    }

		}).start();

		//buttons settings
		owner.nextButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			setContent(new finishContent(owner, result.getTitle()));
			nextButton.removeActionListener(this);
		    }
		});

	    }

	}

	class finishContent extends GroupPanel {

	    private finishContent(final ShowAddContent owner, String title) {

		//progress breadcrumb settings
		owner.finishLabel.setBoldFont();

		//header
		WebLabel headerLabel = new WebLabel("Serial " + title + " dodany!");
		headerLabel.setFontSize(20);
		add(headerLabel);

		//buttons settings
		nextButton.setVisible(false);
		backButton.setVisible(true);
		backButton.setText("Powrót");
		for (ActionListener ac : backButton.getActionListeners()) {
		    backButton.removeActionListener(ac);
		}
		owner.backButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			setContent(new enterTitleContent(owner));
			managerFrame.setContent(new menuContent(managerFrame));
			backButton.setText("Cofnij");
			backButton.removeActionListener(this);
		    }
		});

	    }

	}

    }

}
