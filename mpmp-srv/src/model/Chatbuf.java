package model;

import main.Client;

public class Chatbuf {
	// XXX who needs a chat *buffer*? spec change?
	public static void send(Client sender, String chat) {
		for(Client c: main.Main.clients)
			c.send("chat-update 1\n" + "(" + sender.name() + ") " + chat);
	}
}
