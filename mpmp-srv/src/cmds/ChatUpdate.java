package cmds;

import main.Client;


/**
 * chat-update S->C packet. This is the clien's handler and is but a stub until later.
 */
public class ChatUpdate implements CmdFunc {
	@Override
	public void exec(String line, Client c) {
		c.send("Hello, chat-update!");
		// XXX continue
	}
}
