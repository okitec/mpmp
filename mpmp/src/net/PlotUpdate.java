package net;

import java.util.Arrays;

/**
 * plot-update S->C
 */
public class PlotUpdate implements CmdFunc {
	private PlotUpdater pu;

	@Override
	public void exec(String line, Conn conn) {
		int houses, pos;
		boolean hyp;
		String ownername;
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
		
		if (args[3].equals("hypothec")) {
			hyp = true;
		} else if (args[3].equals("nohypothec")) {
			hyp = false;
		} else {
			conn.sendErr(ErrCode.Usage, "plot-update <Position> <Häuserzahl> <hypothec|nohypothec> <Eigentümer>");
			return;
		}
	
		ownername = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
		pu.plotUpdate(pos, houses, hyp, ownername);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}

	public interface PlotUpdater {
		public void plotUpdate(int pos, int houses, boolean hyp, String ownername);
	}

	public void addPlotUpdater(PlotUpdater pu) {
		this.pu = pu;
	}
}
