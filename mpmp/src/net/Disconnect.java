package net;

import srv.Client;

/**
 * disconnect C->S packet
 */
public class Disconnect implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		Client c = (Client) conn;

		if (c.isSubscribed())
			c.ragequit();

		// XXX relay reason message

		c.sendOK();
		c.disconnect();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
}
