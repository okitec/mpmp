/* Generated at Sun Nov 13 14:40:16 CET 2016 */

package net;

import java.util.Arrays;

/**
 * S->C game turn-update
 */
public class TurnUpdate implements CmdFunc {
	private TurnUpdater tu;

	@Override
	public void exec(String line, Conn conn) {
		String args[] = line.split(" ");
		int roll;
		int paschs;
		String cpname;

		if(args.length < 4) {
			conn.sendErr(ErrCode.Usage, "turn-update <sum> <paschs> <player>");
			return;
		}

		cpname = String.join(" ", Arrays.copyOfRange(args, 3, args.length));

		try {
			roll = Integer.parseInt(args[1]);
			paschs = Integer.parseInt(args[2]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "turn-update <sum> <paschs> <player>");
			return;
		}

		tu.turnUpdate(roll, paschs, cpname);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/* herp derp */}

	public interface TurnUpdater {
		public void turnUpdate(int roll, int paschs, String cpname);
	}

	public void addTurnUpdater(TurnUpdater tu) {
		this.tu = tu;
	}
}
