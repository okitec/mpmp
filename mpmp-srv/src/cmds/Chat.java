package cmds;

import java.io.BufferedReader;
import java.io.PrintWriter;

import main.CmdFunc;

public class Chat implements CmdFunc {

	@Override
	public void exec(String line, BufferedReader in, PrintWriter out) {
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
		out.println("(froop) " + chat);
	}
}
