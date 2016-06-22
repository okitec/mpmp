package cmds;

import main.Conn;
import main.Client;
import main.ErrCode;

/**
 * chat C->S packet
 * @author oki, Leander
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
		
		String[] args = line.split("@");
		if (args.length > 2) {
			c.sendErr("Don't put a '@' in your message if you want to whisper, please.");
			return;
		}
		if (args.length > 1) {
			String name = args[1].substring(0, args[1].indexOf(' '));
			String message = args[1].substring(args[1].indexOf(' ')+1);
			Client receiver = Client.search(name);
			if (receiver != null){
				receiver.send("chat-update [" + c.getName() + "] "+ message);
				return;
			}
		}
		
		argpos = line.indexOf(' ');
		if(argpos < 0) {
			argpos = line.length();
		} else {
			while(argpos < line.length() && Character.isWhitespace(line.codePointAt(argpos)))
				argpos++;
		}

		chat = line.substring(argpos);
		Client.broadcast("chat-update " + "(" + c.getName() + ") " + chat);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		System.err.println("Can't happen: " + err.getMessage());
	}
}
