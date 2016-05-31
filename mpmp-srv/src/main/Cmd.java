package main;

import java.io.BufferedReader;
import java.io.PrintWriter;

public enum Cmd {
	Chat("chat", new cmds.Chat()),
	ChatUpdate("chat-update", new cmds.ChatUpdate());
	private String s;
	private CmdFunc fn;

	private Cmd(String s, CmdFunc fn) {
		this.s = s;
		this.fn  = fn;
	}

	public static Cmd search(String s) {
		for(Cmd cmd: Cmd.values())
			if(cmd.s.equals(s))    // XXX check only the prefix
				return cmd;
		
		return null;
	}

	public void exec(String line, BufferedReader in, PrintWriter out) {
		fn.exec(line, in, out);
	}
}
