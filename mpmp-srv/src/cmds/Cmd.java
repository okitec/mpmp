package cmds;

import main.Client;

public enum Cmd {
	Chat("chat", new cmds.Chat()),
	ChatUpdate("chat-update", new cmds.ChatUpdate()),
	Subscribe("subscribe", new cmds.Subscribe()),
	ListPlayers("listp", new cmds.ListPlayers());
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

	public void exec(String line, Client c) {
		fn.exec(line, c);
	}
}
