package cmds;

import main.Conn;
import main.Client;

/**
 * chat C->S packet
 * @author oki, Leander
 */
public class Chat implements cmds.CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		int argpos;
		String chat;
		Client c = (Client) conn;

		if (!c.isSubscribed()) {
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
		Client.broadcast("chat-update " + "(" + c.getName() + ") " + chat);
	}
}
