package net;

import java.util.Arrays;

import srv.Client;
import model.HousePlot;
import model.Player;
import model.Plot;
import model.SrvModel;

/**
 * C->S
 */
public class AddHouse implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		Player p;
		HousePlot hp;
		String[] args = line.split(" ");
		int pos;

		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "add-house <Position>");
			return;
		}

		// XXX outdated
		p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		pos = Integer.parseInt(args[1]);
		hp = (HousePlot) SrvModel.self.m.getPlot(pos);
		if (hp == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (hp.getOwner() != p) {
			conn.sendErr(ErrCode.AlreadyOwned, hp.getOwner().getName());
			return;
		}

		switch (hp.addHouse()) {
		case -1:
			conn.sendErr(ErrCode.TooManyHouses);
			return;
		case -2:
			conn.sendErr(ErrCode.DontHave, "every plot of that plot group");
			return;
		case -3:
			conn.sendErr(ErrCode.UnbalancedColor);
			return;
		case -4:
			conn.sendErr(ErrCode.MissingMoney);
			return;
		case 1:
			conn.sendOK();
			Client.broadcast("plot-update " + pos + " " + hp); // XXX move to Update
			break;
		default:
			conn.sendErr(ErrCode.Internal, "Unexpected error");
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
