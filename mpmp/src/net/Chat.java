package net;

import srv.Client;
import srv.Interpreter;
import model.Player;
import model.SrvPlayer;
import model.SrvModel;

/**
 * chat C->S packet
 */
public class Chat implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		String chat;
		Client c = (Client) conn;

		if (!c.isSubscribed()) {
			c.sendErr(ErrCode.NotSubscribed);
			return;
		}
		
		chat = line.substring("chat".length());
		chat = chat.trim();

		/* Cheat lines start with a slash. */
		if(chat.charAt(0) == '/') {
			SrvPlayer sp;

			sp = SrvModel.self.getSrvPlayer(c.getName());
			if(sp == null)
				return;

			Interpreter.run(sp, chat.substring(1));
		}

		String receiver = Player.matches(chat, true);

		if (receiver == null)
			Client.broadcast("chat-update (" + c.getName() + ") " + chat);
		else {
			String[] s = chat.split("@" + receiver);
			if (s.length < 2)
				return;
			String message = s[1];
			Client.search(receiver).send("chat-update [" + c.getName() + "] " + message);
			c.send("chat-update [->" + receiver + "] " + message);
		}

		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		System.err.println("Can't happen: " + err.getMessage());
	}
}
