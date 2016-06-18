package cmds;

/**
 * ragequit C->S: give up and become a spectator.
 */
public class Ragequit implements CmdFn {
	@Override
	public void exec(String line, Conn conn) {
		Client c = (Client) conn;
		c.sendOK():
		c.ragequit();
	}
}
