package cmds;

import main.Client;

/**
 * chat C->S packet
 */
public class Chat implements cmds.CmdFunc {
	@Override
	public void exec(String line, Client c) {
		int argpos;
		String chat;

		if (c.getName() == null) {
			c.sendErr("You are not subscribed!");
			return;
		}

		argpos = line.indexOf(' ');
		if(argpos < 0) {
			argpos = line.length();
		} else {
			while(argpos < line.length() && Character.isWhitespace(line.codePointAt(argpos)))
				argpos++;
		}

		chat = line.substring(argpos);
		Client.broadcast("chat-update 1\n" + "(" + c.getName() + ") " + chat);
	}
}
