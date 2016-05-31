package cmds;

import main.Client;


/**
 * chat-update S->C packet
 */
public class ChatUpdate implements CmdFunc {
	@Override
	public void exec(String line, Client c) {
		c.send("Hello, chat-update!");
		// XXX continue
	}
}
