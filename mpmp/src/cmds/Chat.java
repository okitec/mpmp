package cmds;

import main.Conn;
import main.Client;
import main.ErrCode;
import model.Player;

/**
 * chat C->S packet
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
}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		System.err.println("Can't happen: " + err.getMessage());
	}
}
