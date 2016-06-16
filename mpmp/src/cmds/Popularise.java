package cmds;

import main.Client;
import main.Conn;

/**
 *
 * @author Leander
 */
public class Popularise implements CmdFunc{

	@Override
	public void exec(String line, Conn conn) {
		String message = line.substring(11);
		Client.broadcast("chat-update " + message);
	}
	
}
