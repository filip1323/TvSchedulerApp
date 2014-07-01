/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.PrintWriter;
import java.io.StringWriter;
import net.NetCourier;
import portal.ClientController;

/**
 *
 * @author Filip
 */
public class ExceptionReceiver {

    static private ClientController portal;

    static ExceptionReceiver instance;

    static public void assignPortal(ClientController portal) {
	ExceptionReceiver.portal = portal;
    }

    static public ExceptionReceiver getInstance() {
	if (instance == null) {
	    instance = new ExceptionReceiver();
	}
	return instance;
    }

    private ExceptionReceiver() {
    }

    public void handle(Exception ex) {
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ex.printStackTrace(pw);
	sw.toString();
	NetCourier courier = new NetCourier();
	courier.initialize(ex.getClass().getSimpleName(), sw.toString(), NetCourier.Type.exception);
	portal.send(courier);
    }

}
