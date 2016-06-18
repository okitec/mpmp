package cmds;

import main.Conn;
import main.Client;

/**
 * disconnect C->S packet
 */
public class Disconnect implements cmds.CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		Client c = (Client) conn;

		if (c.isSubscribed())
			c.ragequit();

		// XXX relay reason message

		c.sendOK();
		c.disconnect();
	}
}
