/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_interface;

import com.alee.laf.WebLookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Filip
 */
public class UserInterface {

    private TransparentWindow window;
    private ContentPane contentPane;

    public UserInterface() {
	try {
	    UIManager.setLookAndFeel(new WebLookAndFeel());
	} catch (UnsupportedLookAndFeelException ex) {
	    ex.printStackTrace();
	}
	window = new TransparentWindow();
	contentPane = new ContentPane();
	contentPane.initComponents();
	window.setContentPane(contentPane);
	window.show();
    }
}
