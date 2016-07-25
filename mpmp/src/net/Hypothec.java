package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import model.SrvModel;
import model.SrvPlayer;
import view.Displayer;

/**
 * hypothec C->S
 */
public class Hypothec implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		Plot plot;
		SrvModel sm = SrvModel.self;
		SrvPlayer sp;

		if (args.length < 3) {
			conn.sendErr(ErrCode.Usage, "hypothec <yes|no> <Position>");
			return;
		}

		sp = sm.getSrvPlayer(((Client) conn).getName());
		if (sp == null) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		plot = sm.m.getPlot(Integer.parseInt(args[2]));
		if(plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (sp.p != plot.getOwner()) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}

		int hyp;
		switch (args[1]) {
		case "yes":
			hyp = plot.hypothec(true);
			sp.addMoney(hyp);
			break;
		case "no":
			hyp = plot.hypothec(false);
			if (sp.addMoney(-hyp - hyp/10) < 0) {
				sp.addMoney(hyp + hyp/10);
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
		Client.broadcast("money-update " + sp.p.getMoney() + " " + sp.p.getName());
		Client.broadcast("plot-update " + plot);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
