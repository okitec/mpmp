package net;

import java.util.Arrays;

import model.Player;
import model.Plot;

/**
 * plot-update S->C
 */
public class PlotUpdate implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		Plot plot;
		int houses, pos;
		boolean hyp;
		Player owner;
		String[] args = line.split(" ");
		
		if (args.length < 5) {
			conn.sendErr(ErrCode.Usage, "plot-update <Position> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>");
			return;
		}

		try {
			pos = Integer.parseInt(args[1]); 
			houses = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Internal, "not a number");
			return;
		}
		
		plot = null;
		// XXX missing pos-to-plot map
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		
		if (args[3].equals("hypothec")) {
			hyp = true;
		} else if (args[3].equals("nohypothec")) {
			hyp = false;
		} else {
			conn.sendErr(ErrCode.Usage, "plot-update <Name des Grundstücks> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>");
			return;
		}
		
		owner = Player.search(Player.matches(String.join(" ", Arrays.copyOfRange(args, 4, args.length)), false));
		if (owner == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}
		
		// XXX Do something with that information.
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
