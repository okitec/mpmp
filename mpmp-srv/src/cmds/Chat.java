package cmds;

import main.Client;
import model.Chatbuf;

/**
 * chat C->S packet
 */
public class Chat implements cmds.CmdFunc {
	@Override
	public void exec(String line, Client c) {
		int argpos;
		String chat;

		argpos = line.indexOf(' ');
		if(argpos < 0) {
			argpos = line.length();
		} else {
			while(Character.isWhitespace(line.codePointAt(argpos)))
				argpos++;
		}

		chat = line.substring(argpos);
		Chatbuf.send(c, chat);
	}
}
