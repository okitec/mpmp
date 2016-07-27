package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import model.HousePlot;

/**
 * C->S
 */
public class RmHouse implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		HousePlot hp;
		String[] args = line.split(" ");

		Player p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "rm-house <Grundstück>");
			return;
		}

		hp = (HousePlot) Plot.search(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
		if (hp == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (hp.getOwner() != p) {
			conn.sendErr(ErrCode.AlreadyOwned, hp.getOwner().getName());
			return;
		}
		
		switch (hp.rmHouse()) {
		case -1:
			conn.sendErr(ErrCode.DontHave, "a single house");
			return;
		case -2:
			conn.sendErr(ErrCode.UnbalancedColor);
			return;
		case 1:
			conn.sendOK();
			conn.send("plot-update " + hp);
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
