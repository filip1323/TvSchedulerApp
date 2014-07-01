/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

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
			Thread.sleep(5000);
		    } catch (InterruptedException ex) {
			ex.printStackTrace();
		    }
		}

		//when connected
		NetCourier courier = new NetCourier();
		courier.initialize("clientHashcode", NetCourier.Type.request);
		clientService.send(courier);
	    }

	}).start();
    }

    public void send(Object obj) {
	clientService.send(obj);
    }

    void processReceivedObject(Object object) {
	if (object instanceof NetCourier) {
	    NetCourier courier = (NetCourier) object;
	    if (courier.getType() == NetCourier.Type.respond) {
		if ("clientHashcode".equals(courier.getHead())) { //check integrality
		    try {
			String clientHashcode = courier.getBody();
			String thisHashcode = Utils.Files.getMD5Checksum("TvSchedulerApp.jar");

			if (!clientHashcode.equals(thisHashcode)) {
			    userInterface.suggestUpdate();
			}
		    } catch (Exception ex) {
			ex.printStackTrace();
		    }
		}
	    }
	}
	//TODO
    }

    public ClientService getClientService() {
	return clientService;
    }
}
