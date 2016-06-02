package model;

import main.Client;

public class Chatbuf extends Msgbuf{
	// XXX who needs a chat *buffer*? spec change?
	public static void send(Client sender, String chat) {
		if (sender.getName() == null)
			return;
		
		for (Client c : main.Main.getClients())
			c.send("chat-update 1\n" + "(" + sender.getName() + ") " + chat);
	}
}
