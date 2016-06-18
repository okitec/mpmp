package cmds;

import main.Conn;

/**
 * Class Cmd is the table of commands, pairing the String with the associated handler
 * of type CmdFunc.
 * @author oki, Leander
 */
public enum Cmd {
	/* C->S */
	Chat("chat", new cmds.Chat()),
	Subscribe("subscribe", new cmds.Subscribe()),
	Ragequit("ragequit", new cmds.Ragequit()),
	EndTurn("end-turn", new cmds.EndTurn()),

	/* S->C */
	ChatUpdate("chat-update", new cmds.ChatUpdate());


	private final String s;
	private final CmdFunc fn;

	private Cmd(String s, CmdFunc fn) {
		this.s = s;
		this.fn  = fn;
	}

	public static Cmd search(String s) {
		for(Cmd cmd: Cmd.values())
			if(cmd.s.equals(s))
				return cmd;
		
		return null;
	}

	public void exec(String line, Conn c) {
		fn.exec(line, c);
	}

	// XXX meh -oki
	public CmdFunc getFn() {
		return fn;
	}
}
