/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Filip
 */
public class ContentPane extends JPanel {

    private GroupPanel contentGroupPanel;
    private WebButton exitButton;

    public void initComponents() {
	//-----------------------------BODY-----------------------------//
	//creating exit button
	exitButton = new WebButton("Exit");

	//-----------------------------CONTENT-----------------------------//
	//creating contentGroupPanel panel
	contentGroupPanel = new GroupPanel(0, false,
		exitButton
	);

	//setting outer maring
	contentGroupPanel.setMargin(5);

	//-----------------------------FINAL-----------------------------//
	add(contentGroupPanel, this);
	setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
    }
}
