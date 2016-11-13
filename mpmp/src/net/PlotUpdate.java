/* Generated at Sun Nov 13 14:40:16 CET 2016 */

package net;

import java.util.Arrays;

/**
 * S->C game plot-update
 */
public class PlotUpdate implements CmdFunc {
	private PlotUpdater pu;

	@Override
	public void exec(String line, Conn conn) {
		String args[] = line.split(" ");
		int pos;
		int nhouses;
		boolean hyp;
		String owner;

		if(args.length < 5) {
			conn.sendErr(ErrCode.Usage, "plot-update <pos> <houses> <hypothec|nohypothec> <owner>");
			return;
		}

		switch(args[3]) {
		case "hypothec":
			hyp = true;
			break;
		case "nohypothec":
			hyp = false;
			break;
		default:
			conn.sendErr(ErrCode.Usage, "plot-update <pos> <houses> <hypothec|nohypothec> <owner>");
			return;
		}

		owner = String.join(" ", Arrays.copyOfRange(args, 4, args.length));

		try {
			pos = Integer.parseInt(args[1]);
			nhouses = Integer.parseInt(args[2]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "plot-update <pos> <houses> <hypothec|nohypothec> <owner>");
			return;
		}

		pu.plotUpdate(pos, nhouses, hyp, owner);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/* herp derp */}

	public interface PlotUpdater {
		public void plotUpdate(int pos, int nhouses, boolean hyp, String owner);
	}

	public void addPlotUpdater(PlotUpdater pu) {
		this.pu = pu;
	}
}
