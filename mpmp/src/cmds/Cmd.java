package cmds;

import main.Conn;
import main.ErrCode;

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
	Disconnect("disconnect", new cmds.Disconnect()),
	Whisper("whisper", new cmds.Whisper()),
	Unjail("unjail", new cmds.Unjail()),
	BuyPlot("buy-plot", new cmds.BuyPlot()),
	SellPlot("sell-plot", new cmds.SellPlot()),
	Hypothec("hypothec", new cmds.Hypothec()),
	AddHouse("add-house", new cmds.AddHouse()),
	RmHouse("rm-house", new cmds.RmHouse()),

	/* S->C */
	ChatUpdate("chat-update", new cmds.ChatUpdate()),
	ClientlistUpdate("clientlist-update", new cmds.ClientlistUpdate()),
	AddMoney("show-transaction", new cmds.ShowTransaction()),
	AuctionPlot("auction-plot", new cmds.AuctionPlot()),
	PlotUpdate("plot-update", new cmds.PlotUpdate());

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
	
	public void error(ErrCode err, String line, Conn conn){
		fn.error(err, line, conn);
	}
}
