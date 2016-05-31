package cmds;

import java.io.BufferedReader;
import java.io.PrintWriter;

import main.CmdFunc;

public class ChatUpdate implements CmdFunc{

	@Override
	public void exec(String line, BufferedReader in, PrintWriter out) {
		out.println("Hello, chat-update!");
		// XXX continue
	}

}
