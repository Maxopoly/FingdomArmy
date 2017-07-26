package com.github.maxopoly.FingdomArmy;

import com.github.maxopoly.angeliacore.actions.actions.LookAt;
import com.github.maxopoly.angeliacore.actions.actions.Wait;
import com.github.maxopoly.angeliacore.connection.ServerConnection;
import com.github.maxopoly.angeliacore.event.AngeliaEventHandler;
import com.github.maxopoly.angeliacore.event.AngeliaListener;
import com.github.maxopoly.angeliacore.event.events.ActionQueueEmptiedEvent;
import com.github.maxopoly.angeliacore.model.location.Location;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static Logger logger = LogManager.getLogger("Main");

	public static void main(String[] args) {
		final String host = "localhost";
		final int port = 25565;
		int connections = 100;
		logger.error("Starting " + connections + " connections  to " + host + ":" + port);
		for (int i = 0; i < connections; i++) {
			final String name = "botAcc" + i;
			new Thread(new Runnable() {

				public void run() {
					final ServerConnection conn;
					try {
						conn = new ServerConnection(host, port, logger, new DummyAuth(name));
						conn.connect();
					} catch (IOException e) {
						logger.error("Connection failed to connect acc " + name, e);
						return;
					}
					conn.getEventHandler().registerListener(new AngeliaListener() {
						@AngeliaEventHandler
						public void queueEmpty(ActionQueueEmptiedEvent e) {
							Location playerLoc = conn.getPlayerStatus().getHeadLocation();
							conn.getActionQueue().queue(new LookAt(conn, playerLoc.relativeBlock(1, 0, 0)));
							conn.getActionQueue().queue(new Wait(conn, 20));
							conn.getActionQueue().queue(new LookAt(conn, playerLoc.relativeBlock(-1, 0, 0)));
							conn.getActionQueue().queue(new Wait(conn, 20));
							conn.getActionQueue().queue(new LookAt(conn, playerLoc.relativeBlock(0, 0, -1)));
							conn.getActionQueue().queue(new Wait(conn, 20));
							conn.getActionQueue().queue(new LookAt(conn, playerLoc.relativeBlock(0, 0, 1)));
							conn.getActionQueue().queue(new Wait(conn, 20));
						}

					});
					conn.getActionQueue().queue(new Wait(conn, 10));
				}
			}).start();
		}
	}
}
