/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import local_data.Properties;
import logic.Auth;
import misc.Utils;
import net.NetCourier;
import user_interface.UserInterface;

/**
 *
 * @author Filip
 */
public class ClientController {

    ClientService clientService;
    UserInterface userInterface;

    public void assignUserInterface(UserInterface userInterface) {
	this.userInterface = userInterface;
    }

    public ClientController() {
	clientService = new ClientService(this);
	clientService.start();

	//TODO
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		while (!clientService.client.isConnected()) {
		    try {
			Thread.sleep(1000);
		    } catch (InterruptedException ex) {
			ex.printStackTrace();
		    }
		}

		if (!Auth.getInstance().isAuthorized()) {
		    NetCourier courier = new NetCourier();
		    courier.initialize("", NetCourier.Type.requestAuth);
		    clientService.send(courier);
		    while (!Auth.getInstance().isAuthorized()) {
			try {
			    Thread.sleep(1000);
			} catch (InterruptedException ex) {
			    ex.printStackTrace();
			}
		    }
		}
		//when connected
		if (Properties.getInstance().NOTIFICATION_UPDATE.getValue()) {
		    NetCourier courier = new NetCourier();
		    courier.initialize("clientHashcode", NetCourier.Type.request);
		    clientService.send(courier);
		}
	    }

	}).start();
    }

    public void send(Object obj) {
	clientService.send(obj);
    }

    void processReceivedObject(Object object) {
	if (object instanceof NetCourier) {
	    NetCourier courier = (NetCourier) object;
	    switch (courier.getType()) {
		case respond:
		    switch (courier.getHead()) {
			case "clientHashcode":
			    try {
				String clientHashcode = courier.getBody();
				String thisHashcode = Utils.Files.getMD5Checksum("TvSchedulerApp.jar");
				if (!clientHashcode.equals(thisHashcode)) {
//				    new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//					    while (!Auth.getInstance().isAuthorized()) {
//						try {
//						    Thread.sleep(1000);
//						} catch (InterruptedException ex) {
//						    ex.printStackTrace();
//						}
//					    }
				    userInterface.suggestUpdate();
//					}
//
//				    }).start();
				} else {

				}
			    } catch (Exception ex) {
				ex.printStackTrace();
			    }
			    break;
		    }
		    break;
		case respondAuth:
		    Auth.getInstance().authorize();
		    break;
	    }
	}
	//TODO
    }

    public ClientService getClientService() {
	return clientService;
    }
}
