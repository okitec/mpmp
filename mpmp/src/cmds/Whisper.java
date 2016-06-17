package cmds;

import java.util.Arrays;
import main.Client;
import main.Conn;

/**
 * @author Leander
 */
public class Whisper implements CmdFunc {
	private static final String WhisperSyntax = "Syntax: whisper <playername> <message>";
	
	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		if (args.length < 3) {
			conn.sendErr(WhisperSyntax);
			return;
		}
		
		Client client = null;
		for (Client c : Client.getClients())
			if (args[1].equals(c.getName())) {
				client = c;
				break;
			}
		if (client== null) {
			conn.sendErr("This player does not exist!");
			return;
		}
		
		String message = null;
		for (String s : Arrays.copyOfRange(args, 2, args.length)) {
			if (message == null)
				message = s;
			else
				message = message + " " + s;
		}	
		
		client.send("chat-update " + message);
		conn.sendOK();
	}
}
