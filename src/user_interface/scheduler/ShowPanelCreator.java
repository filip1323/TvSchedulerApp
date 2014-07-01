/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.scheduler;

import actions.ButtonAction;
import actions.ButtonAction.Type;
import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.window.PopOverDirection;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.separator.WebSeparator;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import local_data.Properties;
import local_data.Resources;
import misc.Utils;
import show_components.ShowLocalDataHUB;
import show_components.episode.Episode;
import show_components.season.Season;
import show_components.show.Show;

/**
 *
 * @author Filip
 */
public class ShowPanelCreator {

    private Show show;

    private GroupPanel panel;

    private WebButton nextEpisodeButton;
    private int nextEpisodeButtonIndex;

    public GroupPanel getShowPanel(Show show) {
	this.show = show;
	//creating panel
	panel = new GroupPanel(GroupingType.fillLast);
	panel.setGap(0);
	panel.setOrientation(SwingConstants.VERTICAL);

	//creating thumb img
	String thumbPath = ShowLocalDataHUB.getPathForThumb(show.getTitle());
	WebDecoratedImage thumb = new WebDecoratedImage(new ImageIcon(thumbPath));
	panel.add(thumb);
	panel.add(new WebSeparator());

	//creating tvcomButton
	WebButton tvcomButton = new WebButton();
	tvcomButton.setDrawSides(true, false, true, false);
	tvcomButton.setHorizontalAlignment(SwingConstants.LEFT);
	tvcomButton.setAction(new ButtonAction(Type.OPEN_TVCOM_HOMEPAGE, show));
	tvcomButton.setText("tv.com");
	tvcomButton.setIcon(Resources.getImageIcon("external-link.png"));
	panel.add(tvcomButton);

	//ekino button
	if (Properties.getInstance().OPTION_CONNECT_EKINO.getValue()) {
	    WebButton ekinoButton = new WebButton();
	    ekinoButton.setDrawSides(false, false, true, false);
	    ekinoButton.setHorizontalAlignment(SwingConstants.LEFT);
	    ekinoButton.setAction(new ButtonAction(Type.OPEN_EKINO_HOMEPAGE, show));
	    ekinoButton.setText("ekino.tv");
	    ekinoButton.setIcon(Resources.getImageIcon("external-link.png"));
	    panel.add(ekinoButton);
	}

	panel.add(new WebSeparator());

	//next ep counter
	if (Properties.getInstance().OPTION_INFO_NEXT_EP_COUNTER.getValue() && show.isNextEpisodeAnnouncement()) {
	    Episode announcenedEpisode = show.getAnnouncenedEpisode();
	    long timeDiff = Utils.DateManager.getTimeDifferenceInDays(Utils.DateManager.getCurrentDayInMilis(), announcenedEpisode.getReleaseDate());
	    WebLabel annLabel = new WebLabel(timeDiff + " dni do premiery");
	    if (timeDiff == 0) {
		annLabel.setText("Dzień premiery");
	    }
	    if (timeDiff == 1) {
		annLabel.setText("Jutro premiera");
	    }
	    annLabel.setMargin(3);
	    annLabel.setIcon(Resources.getImageIcon("aperture-small.png"));
	    panel.add(annLabel);
	}

	//creating last ep button
	if (Properties.getInstance().OPTION_MENU_LAST_EP.getValue()) {
	    Episode lastEpisode = show.getLastEpisode();
	    WebButton lastEpButton = getEpisodeDetailed(lastEpisode.getSeasonOrdinal(), lastEpisode.getOrdinal());
	    lastEpButton.setIcon(Resources.getImageIcon("bookmark.png"));
	    lastEpButton.setMaximumSize(tvcomButton.getSize());
	    lastEpButton.setText("Najnowszy  " + lastEpisode.getSummary());
	    panel.add(lastEpButton);
	}

	//creating next ep button
	if (Properties.getInstance().OPTION_MENU_MARKED_EPISODE.getValue()) {
	    nextEpisodeButtonIndex = panel.getComponentCount();
	    reloadNextEpisodeButton();
	}

	//seasons list
	WebButton seasonsButton = getSeasonsList();
	panel.add(seasonsButton);

	return panel;

    }

    private void reloadNextEpisodeButton() {
	EventQueue.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		if (nextEpisodeButton != null) {
		    panel.remove(nextEpisodeButton);
		}

		Episode nextEpisode = show.getNextEpisodeToWatch();

		nextEpisodeButton = getEpisodeDetailed(nextEpisode.getSeasonOrdinal(), nextEpisode.getOrdinal());
		nextEpisodeButton.setIcon(Resources.getImageIcon("pin.png"));
		nextEpisodeButton.setDrawSides(false, false, true, false);
		nextEpisodeButton.setText("Wyróżniony " + nextEpisode.getSummary());

		panel.add(nextEpisodeButton, nextEpisodeButtonIndex);
		panel.repaint();
		panel.setVisible(false);
		panel.setVisible(true);
	    }
	});

    }

    private WebButton getSeasonsList() {
	final WebButton seasonsButton = new WebButton("Lista sezonów", Resources.getImageIcon("list.png"));
	seasonsButton.setHorizontalAlignment(SwingConstants.LEFT);
	seasonsButton.setDrawSides(false, false, false, false);

	seasonsButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(getSeasonsListContent());
		popOver.setShadeWidth(0);
		popOver.setMovable(false);

		popOver.show(panel, PopOverDirection.right);
	    }
	});

	return seasonsButton;
    }

    private GroupPanel getSeasonsListContent() {

	GroupPanel content = new GroupPanel(0, false);
	content.setShadeTransparency(100);
	WebLabel header = new WebLabel("Sezony");
	header.setHorizontalAlignment(SwingConstants.CENTER);
	header.setMargin(3);
	header.setFontSize(16);
	//content.add(header);

	for (int seasonOrdinal = 1; seasonOrdinal <= show.getSeasonsNumber(); seasonOrdinal++) {
	    WebButton episodesButton = getEpisodesList(seasonOrdinal);
	    content.add(episodesButton);
	}

	return content;

    }

    private WebButton getEpisodesList(final int seasonOrdinal) {
	final WebButton episodesButton = new WebButton(seasonOrdinal + ". Sezon");
	episodesButton.setDrawSides(false, false, true, false);
	episodesButton.setHorizontalAlignment(SwingConstants.LEFT);

	if (seasonOrdinal == 1) {
	    episodesButton.setDrawSides(true, false, true, false);
	}
	episodesButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(getEpisodesListContent(seasonOrdinal));
		popOver.setShadeWidth(0);
		popOver.setMovable(false);

		popOver.show(panel, PopOverDirection.right);
	    }
	});
	return episodesButton;
    }

    private GroupPanel getEpisodesListContent(int seasonOrdinal) {
	GroupPanel content = new GroupPanel(0, false);
	content.setShadeTransparency(100);
	content.setShadeWidth(0);

	WebLabel header = new WebLabel("Sezon " + seasonOrdinal);
	header.setHorizontalAlignment(SwingConstants.CENTER);
	header.setMargin(3);
	header.setFontSize(16);
	//content.add(header);

	Season season = show.getSeason(seasonOrdinal);

	for (int ordinal = 1; ordinal <= season.getEpisodesNumber(); ordinal++) {
	    WebButton episodeButton = getEpisodeDetailed(seasonOrdinal, ordinal);
	    content.add(episodeButton);
	    if (show.getLastEpisode().getSeasonOrdinal() == seasonOrdinal && show.getLastEpisode().getOrdinal() == ordinal) {
		break;
	    }
	}

	return content;

    }

    private WebButton getEpisodeDetailed(final int seasonOrdinal, final int ordinal) {
	Episode episode = show.getSeason(seasonOrdinal).getEpisode(ordinal);
	final WebButton episodeDetailedInfoButton = new WebButton(((ordinal < 10) ? "0" : "") + ordinal + ". " + episode.getTitle());
	episodeDetailedInfoButton.setHorizontalAlignment(SwingConstants.LEFT);
	episodeDetailedInfoButton.setDrawSides(false, false, true, false);
	if (ordinal == 1) {
	    episodeDetailedInfoButton.setDrawSides(true, false, true, false);
	}

	episodeDetailedInfoButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(getEpisodeDetailedContent(seasonOrdinal, ordinal));
		popOver.setShadeWidth(0);
		popOver.setMovable(false);
		Point location = panel.getLocationOnScreen();
		location.x -= popOver.getPreferredSize().width;
		popOver.show(location);
	    }
	});
	return episodeDetailedInfoButton;
    }

    private GroupPanel getEpisodeDetailedContent(int seasonOrdinal, int ordinal) {
	final Episode episode = show.getSeason(seasonOrdinal).getEpisode(ordinal);
	final GroupPanel content = new GroupPanel(GroupingType.fillAll, 0, false);
	content.setMargin(10);
	content.setGap(3);

	//close button
	WebButton closeButton = new WebButton(Resources.getImageIcon("x.png"));
	closeButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		content.setVisible(false);
	    }
	});
	closeButton.setFocusable(false);

	//show title
	WebLabel showTitleLabel = new WebLabel(show.getTitle());
	showTitleLabel.setBoldFont();

	GroupPanel titleGroup = new GroupPanel(GroupingType.fillFirst, showTitleLabel, closeButton);
	content.add(titleGroup);

	//season ordinal
	WebLabel episodeSeasonOrdinalLabel = new WebLabel("Sezon " + seasonOrdinal);
	episodeSeasonOrdinalLabel.setBoldFont();
	content.add(episodeSeasonOrdinalLabel);

	//episode ordinal
	WebLabel episodeOrdinalLabel = new WebLabel("Odcinek " + ordinal);
	episodeOrdinalLabel.setBoldFont();
	content.add(episodeOrdinalLabel);

	content.add(new WebSeparator());

	//episode title
	WebLabel episodeTitleLabel = new WebLabel(episode.getTitle());
	episodeTitleLabel.setMargin(0, 0, 0, 5);
	//content.add(episodeTitleLabel);

	//next and previous
	WebButton nextButton = new WebButton(Resources.getImageIcon("chevron-right.png"));
	nextButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		Episode nextEpisode = episode.getNextEpisode();

		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(getEpisodeDetailedContent(nextEpisode.getSeasonOrdinal(), nextEpisode.getOrdinal()));
		popOver.setShadeWidth(0);
		popOver.setMovable(false);
		Point location = panel.getLocationOnScreen();
		location.x -= popOver.getPreferredSize().width;
		popOver.show(location);
	    }
	});
	WebButton previousButton = new WebButton(Resources.getImageIcon("chevron-left.png"));
	previousButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		Episode previous = episode.getPreviousEpisode();

		final WebPopOver popOver = new WebPopOver();
		//popOver.
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(getEpisodeDetailedContent(previous.getSeasonOrdinal(), previous.getOrdinal()));
		popOver.setShadeWidth(0);
		popOver.setMovable(false);
		Point location = panel.getLocationOnScreen();
		location.x -= popOver.getPreferredSize().width;
		popOver.show(location);
	    }
	});
	GroupPanel chevrons = new GroupPanel(GroupingType.fillFirst, episodeTitleLabel, previousButton, nextButton);
	nextButton.setShadeWidth(0);
	previousButton.setShadeWidth(0);
	//buttons disable when needs

	if (show.getLastEpisode() == episode) {
	    nextButton.setVisible(false);
	}
	if (episode.getSeasonOrdinal() == 1 && episode.getOrdinal() == 1) {
	    previousButton.setVisible(false);
	}
	content.add(chevrons);

	//summary
	WebLabel summaryLabel = new WebLabel(show.getTitle() + " " + Utils.Others.prepareStandardSummary(seasonOrdinal, ordinal));
	//content.add(summaryLabel);

	//date
	WebLabel releaseDateLabel = new WebLabel();
	String dateString = "Premiera: " + Utils.DateManager.convertLongIntoUnifiedFormat(episode.getReleaseDate());
	long timeDiff = Utils.DateManager.getTimeDifferenceInDays(Utils.DateManager.getCurrentDayInMilis(), episode.getReleaseDate());

	if (timeDiff < -1 && timeDiff > -180) {
	    dateString += ", " + (-timeDiff) + " dni temu";
	} else if (timeDiff == -1) {
	    dateString += ", wczoraj";
	} else if (timeDiff > 1) {
	    dateString += ", za " + timeDiff + " dni";
	} else if (timeDiff == 1) {
	    dateString += ", jutro";
	} else if (timeDiff == 0) {
	    dateString += ", dziś";
	}
	releaseDateLabel.setText(dateString);
	content.add(releaseDateLabel);
	content.add(new WebSeparator());

	//external links TODO
	GroupPanel externalLinksGroup = new GroupPanel(0, false);

	//tvcom
	WebButton tvcomButton = new WebButton("Tv.com", Resources.getImageIcon("external-link.png"));
	if (Properties.getInstance().OPTION_CONNECT_PIRATEBAY.getValue() || (Properties.getInstance().OPTION_CONNECT_EKINO.getValue())) {
	    tvcomButton.setDrawSides(true, true, false, true);
	}
	tvcomButton.setHorizontalAlignment(SwingConstants.LEFT);
	tvcomButton.setShadeWidth(0);
	tvcomButton.setAction(new ButtonAction(Type.OPEN_TVCOM_EPISODE, episode));
	tvcomButton.setText("Tv.com");
	tvcomButton.setIcon(Resources.getImageIcon("external-link.png"));
	externalLinksGroup.add(tvcomButton);

	//piratebay
	if (Properties.getInstance().OPTION_CONNECT_PIRATEBAY.getValue()) {
	    WebButton piratebayButton = new WebButton();
	    piratebayButton.setDrawSides(false, true, true, true);
	    if (Properties.getInstance().OPTION_CONNECT_EKINO.getValue()) {
		piratebayButton.setDrawBottom(false);
	    }
	    piratebayButton.setHorizontalAlignment(SwingConstants.LEFT);
	    piratebayButton.setShadeWidth(0);
	    piratebayButton.setAction(new ButtonAction(Type.OPEN_PIRATEBAY_EPISODE, episode));
	    piratebayButton.setText("Torrent");
	    piratebayButton.setIcon(Resources.getImageIcon("external-link.png"));
	    externalLinksGroup.add(piratebayButton);
	}

	//ekino
	if (Properties.getInstance().OPTION_CONNECT_EKINO.getValue()) {
	    WebButton ekinoButton = new WebButton("Ekino.TV", Resources.getImageIcon("external-link.png"));
	    ekinoButton.setDrawSides(false, true, true, true);
	    ekinoButton.setHorizontalAlignment(SwingConstants.LEFT);
	    ekinoButton.setShadeWidth(0);
	    ekinoButton.setAction(new ButtonAction(Type.OPEN_EKINO_EPISODE, episode));
	    ekinoButton.setText("Ekino.TV");
	    ekinoButton.setIcon(Resources.getImageIcon("external-link.png"));
	    externalLinksGroup.add(ekinoButton);
	}

	content.add(externalLinksGroup);

	content.add(new WebSeparator());

	//last viewed
	if (Properties.getInstance().OPTION_MENU_MARKED_EPISODE.getValue()) {
	    final WebToggleButton viewedButton = new WebToggleButton("Oznaczyć?", Resources.getImageIcon("pin.png"));
	    viewedButton.setFontSize(10);
	    viewedButton.setDrawSides(false, false, false, false);
	    if (episode.isNextToBeWatched()) {
		viewedButton.setSelected(true);
		viewedButton.setEnabled(false);
	    }

	    viewedButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    episode.edit().setAsNextToWatch();
		    viewedButton.setEnabled(false);
		    reloadNextEpisodeButton();
		}
	    }
	    );
	    content.add(viewedButton);
	}
	return content;
    }
}
