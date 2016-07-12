package net;

import java.util.Arrays;

import model.Player;
import model.Plot;

/**
 * S->C
 */
public class PlotUpdate implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		Plot plot;
		int houses, delim = -1;
		boolean hyp;
		Player owner;
		String[] args = line.split(" ");
		
		if (args.length < 5) {
			conn.sendErr(ErrCode.Usage, "plot-update <Name des Grundstücks> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>");
			return;
		}
		
		for (int i = 0; i < args.length; i++) 
			if (args[i].matches("[0-9]+")) 
				try {
					houses = Integer.parseInt(args[i]);
					delim = i;
					break;
				} catch (NumberFormatException nfe) {
					conn.sendErr(ErrCode.Internal, "'" + args[i] + "' is not a number");
					return;
				}
		if (delim == -1) {
			conn.sendErr(ErrCode.Usage, "plot-update <Name des Grundstücks> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>");
			return;
		}
		
		plot = Plot.search(Plot.matches(String.join(" ", Arrays.copyOfRange(args, 0, delim-1))));
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		
		if (args[delim+1].equals("hypothec"))
			hyp = true;
		else if (args[delim+1].equals("nohypothec"))
			hyp = false;
		else {
			conn.sendErr(ErrCode.Usage, "plot-update <Name des Grundstücks> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>");
			return;
		}
		
		owner = Player.search(Player.matches(String.join(" ", Arrays.copyOfRange(args, delim+2, args.length)), false));
		if (owner == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}
		
		//Do something with that information.
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
