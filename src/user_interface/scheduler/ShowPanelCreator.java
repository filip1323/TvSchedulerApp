/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface.scheduler;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.PopOverDirection;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.separator.WebSeparator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import local_data.Resources;
import local_data.Settings;
import misc.Utils;
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

    public GroupPanel getShowPanel(Show show) {
	this.show = show;
	//creating panel
	panel = new GroupPanel();
	panel.setGap(0);
	panel.setOrientation(SwingConstants.VERTICAL);

	//creating thumb img
	String thumbPath = "thumbs/" + show.getTitle() + "_thumb.jpg";
	WebDecoratedImage thumb = new WebDecoratedImage(new ImageIcon(thumbPath));
	panel.add(thumb);
	panel.add(new WebSeparator());

//	//creating title label
//	WebLabel titleLabel = new WebLabel(show.getTitle());
//	titleLabel.setFontSize(20);
//	titleLabel.setHorizontalAlignment(0);
//	panel.add(titleLabel);
	//tvcom button
	WebButton tvcomButton = new WebButton("tv.com", Resources.getImageIcon("external-link.png"));
	tvcomButton.setDrawSides(true, false, true, false);
	tvcomButton.setHorizontalAlignment(SwingConstants.LEFT);
	panel.add(tvcomButton);

	//ekino button
	if (Settings.getInstance().OPTION_CONNECT_EKINO) {
	    WebButton ekinoButton = new WebButton("ekino.tv", Resources.getImageIcon("external-link.png"));
	    ekinoButton.setDrawSides(false, false, true, false);
	    ekinoButton.setHorizontalAlignment(SwingConstants.LEFT);
	    panel.add(ekinoButton);
	}

	//seasons list
	WebButton seasonsButton = getSeasonsList();
	panel.add(seasonsButton);

	return panel;

    }

    private WebButton getSeasonsList() {
	final WebButton seasonsButton = new WebButton("Lista sezonów", Resources.getImageIcon("list.png"));
	seasonsButton.setHorizontalAlignment(SwingConstants.LEFT);
	seasonsButton.setDrawSides(false, false, false, false);

	final GroupPanel content = new GroupPanel(0, false);
	content.setShadeTransparency(100);
	WebLabel header = new WebLabel("Sezony");
	header.setHorizontalAlignment(SwingConstants.CENTER);
	header.setMargin(3);
	header.setFontSize(16);
	//content.add(header);

	for (int seasonOrdinal = 1; seasonOrdinal < show.getSeasonsNumber(); seasonOrdinal++) {
	    WebButton episodesButton = getEpisodesList(seasonOrdinal);
	    content.add(episodesButton);
	}

	seasonsButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(content);
		popOver.setShadeWidth(0);

		popOver.show((WebButton) e.getSource(), PopOverDirection.left);
	    }
	});

	return seasonsButton;
    }

    private WebButton getEpisodesList(int seasonOrdinal) {
	final WebButton episodesButton = new WebButton(seasonOrdinal + ". Sezon");
	episodesButton.setDrawSides(false, false, true, false);
	episodesButton.setHorizontalAlignment(SwingConstants.LEFT);

	if (seasonOrdinal == 1) {
	    episodesButton.setDrawSides(true, false, true, false);
	}
	final GroupPanel content = new GroupPanel(0, false);
	content.setShadeTransparency(100);
	content.setShadeWidth(0);

	WebLabel header = new WebLabel("Sezon " + seasonOrdinal);
	header.setHorizontalAlignment(SwingConstants.CENTER);
	header.setMargin(3);
	header.setFontSize(16);
	//content.add(header);

	Season season = show.getSeason(seasonOrdinal);

	for (int ordinal = 1; ordinal < season.getEpisodesNumber(); ordinal++) {
	    WebButton episodeButton = getEpisodeDetailed(seasonOrdinal, ordinal);
	    content.add(episodeButton);
	}

	episodesButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(content);
		popOver.setShadeWidth(0);

		popOver.show((WebButton) e.getSource(), PopOverDirection.left);
	    }
	});
	return episodesButton;
    }

    private WebButton getEpisodeDetailed(int seasonOrdinal, int ordinal) {
	Episode episode = show.getSeason(seasonOrdinal).getEpisode(ordinal);
	final WebButton episodeDetailedInfoButton = new WebButton(((ordinal < 10) ? "0" : "") + ordinal + ". " + episode.getTitle());
	episodeDetailedInfoButton.setHorizontalAlignment(SwingConstants.LEFT);
	episodeDetailedInfoButton.setDrawSides(false, false, true, false);
	if (ordinal == 1) {
	    episodeDetailedInfoButton.setDrawSides(true, false, true, false);
	}
	final GroupPanel content = new GroupPanel(0, false);
	content.setMargin(10);
	content.setGap(3);

	WebLabel showTitleLabel = new WebLabel(show.getTitle());
	showTitleLabel.setBoldFont();
	content.add(showTitleLabel);

	WebLabel episodeSeasonOrdinalLabel = new WebLabel("Sezon " + seasonOrdinal);
	episodeSeasonOrdinalLabel.setBoldFont();
	content.add(episodeSeasonOrdinalLabel);

	WebLabel episodeOrdinalLabel = new WebLabel("Odcinek " + ordinal);
	episodeOrdinalLabel.setBoldFont();
	content.add(episodeOrdinalLabel);

	content.add(new WebSeparator());

	WebLabel episodeTitleLabel = new WebLabel(episode.getTitle());
	content.add(episodeTitleLabel);

	WebLabel summaryLabel = new WebLabel(show.getTitle() + " " + Utils.Others.prepareStandardSummary(seasonOrdinal, ordinal));
	//content.add(summaryLabel);

	WebLabel releaseDateLabel = new WebLabel("Premiera: " + Utils.DateManager.convertLongIntoUnifiedFormat(episode.getReleaseDate()));
	content.add(releaseDateLabel);
	content.add(new WebSeparator());

	GroupPanel externalLinksGroup = new GroupPanel(0, false);

	WebButton tvcomButton = new WebButton("Tv.com", Resources.getImageIcon("external-link.png"));
	if (Settings.getInstance().OPTION_CONNECT_PIRATEBAY || (Settings.getInstance().OPTION_CONNECT_EKINO)) {
	    tvcomButton.setDrawSides(true, true, false, true);
	}
	tvcomButton.setHorizontalAlignment(SwingConstants.LEFT);
	tvcomButton.setShadeWidth(0);
	externalLinksGroup.add(tvcomButton);

	//piratebay
	if (Settings.getInstance().OPTION_CONNECT_PIRATEBAY) {
	    WebButton piratebayButton = new WebButton("Torrent", Resources.getImageIcon("external-link.png"));
	    piratebayButton.setDrawSides(false, true, true, true);
	    if (Settings.getInstance().OPTION_CONNECT_EKINO) {
		piratebayButton.setDrawBottom(false);
	    }
	    piratebayButton.setHorizontalAlignment(SwingConstants.LEFT);
	    piratebayButton.setShadeWidth(0);
	    externalLinksGroup.add(piratebayButton);
	}

	//ekino
	if (Settings.getInstance().OPTION_CONNECT_EKINO) {
	    WebButton ekinoButton = new WebButton("Ekino.TV", Resources.getImageIcon("external-link.png"));
	    ekinoButton.setDrawSides(false, true, true, true);
	    ekinoButton.setHorizontalAlignment(SwingConstants.LEFT);
	    ekinoButton.setShadeWidth(0);
	    externalLinksGroup.add(ekinoButton);
	}

	content.add(externalLinksGroup);

	WebCheckBox viewedCheckbox = new WebCheckBox("Obejrzany?");
	content.add(viewedCheckbox);

	episodeDetailedInfoButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		final WebPopOver popOver = new WebPopOver();
		popOver.setCloseOnFocusLoss(true);
		popOver.setAlwaysOnTop(true);
		popOver.add(content);
		popOver.setShadeWidth(0);

		popOver.show((WebButton) e.getSource(), PopOverDirection.right);
	    }
	});
	return episodeDetailedInfoButton;
    }
}
