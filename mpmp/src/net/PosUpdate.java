/* Generated at Sun Nov 13 14:33:20 CET 2016 */

package net;

import java.util.Arrays;

/**
 * S->C game pos-update
 */
public class PosUpdate implements CmdFunc {
	private PosUpdater pu;

	@Override
	public void exec(String line, Conn conn) {
		String args[] = line.split(" ");
		int pos;
		String name;

		if(args.length < 3) {
			conn.sendErr(ErrCode.Usage, "pos-update <pos> <player>");
			return;
		}

		name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

		try {
			pos = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "pos-update <pos> <player>");
			return;
		}

		pu.posUpdate(pos, name);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/* herp derp */}

	public interface PosUpdater {
		public void posUpdate(int pos, String name);
	}

	public void addPosUpdater(PosUpdater pu) {
		this.pu = pu;
	}
}
