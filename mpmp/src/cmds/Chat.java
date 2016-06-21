package cmds;

import main.Conn;
import main.Client;
import main.ErrCode;

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
			c.sendErr(ErrCode.NotSubscribed);
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

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		System.err.println("Can't happen: " + err.getMessage());
	}
}
