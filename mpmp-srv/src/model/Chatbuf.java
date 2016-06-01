package model;

import main.Client;

public class Chatbuf extends Msgbuf{
	// XXX who needs a chat *buffer*? spec change?
	public static void send(Client sender, String chat) {
		for (Client c : main.Main.getClients())
			if (sender.getName() == null)
				c.send("chat-update 1\n" + "(???) " + chat);
			else
				c.send("chat-update 1\n" + "(" + sender.getName() + ") " + chat);
	}
}
