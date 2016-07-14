package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import view.Displayer;

/**
 * C->S
 */
public class Hypothec implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		Plot plot;

		Player p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		if (args.length < 3) {
			conn.sendErr(ErrCode.Usage, "hypothec <yes|no> <Position>");
			return;
		}

		plot = null;
		// XXX missing pos-to-plot map
		if(plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (p != plot.getOwner()) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}

		int hyp;
		switch (args[1]) {
		case "yes":
			hyp = plot.hypothec(true);
			p.addMoney(hyp);
			break;
		case "no":
			hyp = plot.hypothec(false);
			if (!p.addMoney(-hyp - hyp/10)) {
				conn.sendErr(ErrCode.MissingMoney, hyp + "");
				return;
			}
			break;
		default:
			conn.sendErr(ErrCode.Usage, "hypothec <yes|no> <Position>");
			return;
		}
		
		conn.sendOK();
		conn.send("show-transaction " + hyp + " Hypothec for plot " + plot.getName());
		Client.broadcast("money-update " + p.getMoney() + " " + p.getName());
		Client.broadcast("plot-update " + plot);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
