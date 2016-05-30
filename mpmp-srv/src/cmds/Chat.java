package cmds;

import java.io.BufferedReader;
import java.io.PrintWriter;

import main.CmdFunc;

public class Chat implements CmdFunc {

	@Override
	public void exec(BufferedReader in, PrintWriter out) {
		out.println("Hello, world!");
		// XXX continue
	}
}
