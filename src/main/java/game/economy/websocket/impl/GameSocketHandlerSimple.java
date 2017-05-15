package game.economy.websocket.impl;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import game.economy.GameServer;
import game.economy.websocket.Request;
import game.economy.websocket.RequestType;
import lombok.Getter;

public class GameSocketHandlerSimple extends WebSocketServer {
	private Gson gson = new Gson();
	private Map<String, RequestType> requestTypes;

	@Getter
	private GameServer server;

	@Getter
	private Set<WebSocket> connections = new HashSet<>();

	public GameSocketHandlerSimple(GameServer server, Map<String, RequestType> requestTypes, int port, Draft d) {
		super(new InetSocketAddress(port), Collections.singletonList(d));

		this.requestTypes = requestTypes;
		this.server = server;
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		connections.remove(arg0);

	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO: implement

	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		try {
			Request r = Request.fromJson(gson, arg1);

			RequestType type = requestTypes.get(r.getType());

			if (type == null) {
				// TODO send back an error message
				return;
			}

			// do what the request should do
			type.runTask(server);
		} catch (JsonSyntaxException e) {
			// TODO send back an error message
		}

	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		connections.add(arg0);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

	}

}
