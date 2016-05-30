package main;

import java.io.BufferedReader;
import java.io.PrintWriter;

public enum Cmd {
	Chat("chat");

	private String s;
	private CmdFunc fn;

	private Cmd(String s) {
		this.s = s;
	}

	public static Cmd search(String s) {
		for(Cmd cmd: Cmd.values())
			if(cmd.s.equals(s))    // XXX check only the prefix
				return cmd;
		
		return null;
	}

	public void exec(BufferedReader in, PrintWriter out) {
		fn.exec(in, out);
	}
}
