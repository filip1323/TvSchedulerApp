/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local_data;

import java.io.Serializable;

/**
 *
 * @author Filip
 */
public class Property implements Serializable {

    private String text;
    private boolean state;

    public void setName(String text) {
	this.text = text;
    }

    public String getName() {
	return this.text;
    }

    public void setValue(boolean state) {
	this.state = state;
    }

    public boolean getValue() {
	if (state == false) {
	    return false;
	}
	return this.state;
    }

    public void toggle() {
	state = !state;
    }
}
