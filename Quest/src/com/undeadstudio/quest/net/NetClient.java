package com.undeadstudio.quest.net;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.entities.player.Player;
import com.undeadstudio.quest.game.Assets;
import com.undeadstudio.quest.game.Level;

public class NetClient {
	Level level;
	Player player;

	static Network network = new Network();
	static Map<Integer, MPPlayer> players = new HashMap<Integer, MPPlayer>();

	public NetClient(Level level) {
		this.level = level;
		init();
	}

	public void init() {
		player = level.player;
		network.connect();
	}

	public void update(float deltaTime) {

		for (MPPlayer mpPlayer : players.values()) {
			mpPlayer.update(deltaTime);
		}

		// Update position
		if (player.networkPosition.x != player.position.x) {
			// Send the player's X value
			PacketUpdateX packet = new PacketUpdateX();
			packet.x = player.position.x;
			network.client.sendUDP(packet);

			player.networkPosition.x = player.position.x;
		}
		if (player.networkPosition.y != player.position.y) {
			// Send the player's Y value
			PacketUpdateY packet = new PacketUpdateY();
			packet.y = player.position.y;
			network.client.sendUDP(packet);

			player.networkPosition.y = player.position.y;
		}
	}

	public void render(SpriteBatch batch) {
		for (MPPlayer mpPlayer : players.values()) {
			Texture tex = Assets.instance.playerTexture;

			batch.draw(tex, mpPlayer.position.x, mpPlayer.position.y,
					mpPlayer.bounds.width, mpPlayer.bounds.height);

		}
	}

}
