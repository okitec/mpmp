package cmds;

import main.Conn;
import main.Client;
import main.ErrCode;

/**
 * ragequit C->S: give up and become a spectator.
 */
public class Ragequit implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		Client c = (Client) conn;
		c.sendOK();
		c.ragequit();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
}
