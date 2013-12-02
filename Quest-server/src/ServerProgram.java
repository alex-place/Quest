import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sun.security.x509.IPAddressName;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class ServerProgram extends Listener {

	enum DEBUG {
		VERBOSE, DEBUG, ERROR, NONE;
	}

	public static final DEBUG debug = DEBUG.VERBOSE;

	static Server server;
	static int port = 27960;
	static String address = "localhost";
	static Map<Integer, Player> players = new HashMap<Integer, Player>();

	public static void main(String[] args) throws IOException {

		readYamlConfig();
		server = new Server();
		server.getKryo().register(PacketUpdateX.class);
		server.getKryo().register(PacketUpdateY.class);
		server.getKryo().register(PacketAddPlayer.class);
		server.getKryo().register(PacketRemovePlayer.class);
		server.bind(port, port);
		server.start();
		server.addListener(new ServerProgram());
		if (debug != DEBUG.NONE)
			System.out.println("The server is ready \n" + "Address:" + address
					+ "\n" + "Port: " + port);

	}

	public static void readYamlConfig() {
		try {
			YamlReader reader = new YamlReader(new FileReader(
					"config/config.yml"));
			Object object = reader.read();
			// System.out.println(object);
			Map map = (Map) object;
			port = Integer.parseInt(map.get("port").toString());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (YamlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connected(Connection c) {
		Player player = new Player();
		player.x = 0;
		player.y = 0;
		player.c = c;

		PacketAddPlayer packet = new PacketAddPlayer();
		packet.id = c.getID();
		server.sendToAllExceptTCP(c.getID(), packet);

		for (Player p : players.values()) {
			PacketAddPlayer packet2 = new PacketAddPlayer();
			packet2.id = p.c.getID();
			c.sendTCP(packet2);
		}

		players.put(c.getID(), player);
		if (debug != DEBUG.NONE)
			System.out.println("Connection received.");
	}

	public void received(Connection c, Object o) {
		if (o instanceof PacketUpdateX) {
			PacketUpdateX packet = (PacketUpdateX) o;
			players.get(c.getID()).x = packet.x;

			packet.id = c.getID();
			server.sendToAllExceptUDP(c.getID(), packet);
			if (debug != DEBUG.NONE)
				System.out.println("received and sent an update X packet");

		} else if (o instanceof PacketUpdateY) {
			PacketUpdateY packet = (PacketUpdateY) o;
			players.get(c.getID()).y = packet.y;

			packet.id = c.getID();
			server.sendToAllExceptUDP(c.getID(), packet);
			if (debug != DEBUG.NONE)
				System.out.println("received and sent an update Y packet");

		}
	}

	public void disconnected(Connection c) {
		players.remove(c.getID());
		PacketRemovePlayer packet = new PacketRemovePlayer();
		packet.id = c.getID();
		server.sendToAllExceptTCP(c.getID(), packet);

		if (debug != DEBUG.NONE)
			System.out.println("Connection dropped.");
	}

}
