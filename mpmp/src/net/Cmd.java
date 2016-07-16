package net;

/**
 * Class Cmd is the table of commands, pairing the String with the associated handler
 * of type CmdFunc.
 */
public enum Cmd {
	/* C->S */
	Chat("chat", new Chat()),
	Subscribe("subscribe", new Subscribe()),
	Ragequit("ragequit", new Ragequit()),
	EndTurn("end-turn", new EndTurn()),
	Whisper("whisper", new Whisper()),
	Unjail("unjail", new Unjail()),
	BuyPlot("buy-plot", new BuyPlot()),
	SellPlot("sell-plot", new SellPlot()),
	Hypothec("hypothec", new Hypothec()),
	AddHouse("add-house", new AddHouse()),
	RmHouse("rm-house", new RmHouse()),
	StartGame("start-game", new StartGame()),

	/* S->C */
	ChatUpdate("chat-update", new ChatUpdate()),
	PlayerlistUpdate("clientlist-update", new PlayerlistUpdate()),
	ShowTransaction("show-transaction", new ShowTransaction()),
	AuctionPlot("auction-plot", new AuctionPlot()),
	PlotUpdate("plot-update", new PlotUpdate()),
	Prison("prison", new Prison()),
	PosUpdate("pos-update", new PosUpdate()),
	MoneyUpdate("money-update", new MoneyUpdate()),
	TurnUpdate("turn-update", new TurnUpdate()),
	StartUpdate("start-update", new StartUpdate());

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
