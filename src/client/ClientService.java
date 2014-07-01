/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
import misc.Bundles;
import net.KryonetSerialisation;
import net.NetCourier;

/**
 *
 * @author Filip
 */
public class ClientService {

    String IP = "89.239.83.107";
    Client client;

    public ClientService(final ClientController portal) {
	com.esotericsoftware.minlog.Log.set(com.esotericsoftware.minlog.Log.LEVEL_NONE);
	client = new Client(16384, 16384);

	client.addListener(new Listener() {

	    @Override
	    public void connected(Connection cnctn) {
		super.connected(cnctn);
		System.out.println("[CLIENT]: CONNECTED");

		//sending macAddress
		NetCourier netCourier = new NetCourier();
		netCourier.initialize("macAddress", Bundles.getUser(), NetCourier.Type.respond);
		send(netCourier);
	    }

	    @Override
	    public void received(Connection cnctn, Object object) {
		super.received(cnctn, object);
		if (!(object instanceof com.esotericsoftware.kryonet.FrameworkMessage)) {
		    System.out.println("[CLIENT]: OBJECT RECEIVED: " + object.getClass() + ": \t" + object);
		    portal.processReceivedObject(object);
		}
	    }

	    @Override
	    public void disconnected(Connection cnctn) {
		super.disconnected(cnctn);
		System.out.println("[CLIENT]: DISCONNECTED");
	    }

	});
	Kryo kryo = client.getKryo();
	new KryonetSerialisation(kryo);
	new Thread(client).start();
    }

    public void start() {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		while (true) {
		    try {
			if (!client.isConnected()) {
			    client.connect(5000, IP, 54555, 54555);
			}
			Thread.sleep(30000);

		    } catch (IOException ex) {
			//System.out.println("[CLIENT]: CONNECTION FAILED: " + IP);
		    } catch (InterruptedException ex) {
			ex.printStackTrace();
		    }
		}
	    }

	}).start();

    }

    public void send(Object obj) {
	if (client.isConnected()) {
	    client.sendTCP(obj);
	    System.out.println("[CLIENT]: OBJECT SEND: " + obj.getClass() + ": \t" + obj);
	}
    }
}
