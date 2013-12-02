package com.undeadstudio.quest.net;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.esotericsoftware.kryonet.Listener;


public class Network extends Listener {
	
	public static final String TAG = Network.class.getName();

	Client client;
	String ip = "localhost";
	int port = 27960;
	
	public void connect(){
		client = new Client();
		client.getKryo().register(PacketUpdateX.class);
		client.getKryo().register(PacketUpdateY.class);
		client.getKryo().register(PacketAddPlayer.class);
		client.getKryo().register(PacketRemovePlayer.class);
		client.addListener(this);
		
		client.start();
		try {
			client.connect(5000, ip, port, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketAddPlayer){
			PacketAddPlayer packet = (PacketAddPlayer) o;
			MPPlayer newPlayer = new MPPlayer();
			NetClient.players.put(packet.id, newPlayer);
			
		}else if(o instanceof PacketRemovePlayer){
			PacketRemovePlayer packet = (PacketRemovePlayer) o;
			NetClient.players.remove(packet.id);
			
		}else if(o instanceof PacketUpdateX){
			PacketUpdateX packet = (PacketUpdateX) o;
			NetClient.players.get(packet.id).x = packet.x;
			
		}else if(o instanceof PacketUpdateY){
			PacketUpdateY packet = (PacketUpdateY) o;
			NetClient.players.get(packet.id).y = packet.y;
			
		}
		else if(o instanceof KeepAlive){
			//ignore internal message
		}
		else{
			Gdx.app.log(TAG, "Unexpected packet recieved: " + o.getClass().getName());
		}
	}
}
