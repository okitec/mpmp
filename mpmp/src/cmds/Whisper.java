package cmds;

import java.util.Arrays;
import main.Client;
import main.Conn;
import main.ErrCode;

/**
 * C->S whisper packet
 * @author Leander
 */
public class Whisper implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		if (args.length < 3) {
			conn.sendErr(ErrCode.Usage + "whisper <playername> <message>");
			return;
		}
		
		Client client = Client.search(args[1]);
		if (client == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}
		
		String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));	
		client.send("chat-update " + message);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
