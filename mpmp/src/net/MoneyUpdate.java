/* Generated at Sun Nov 13 14:40:16 CET 2016 */

package net;

import java.util.Arrays;

/**
 * S->C game money-update
 */
public class MoneyUpdate implements CmdFunc {
	private MoneyUpdater mu;

	@Override
	public void exec(String line, Conn conn) {
		String args[] = line.split(" ");
		int amount;
		String name;

		if(args.length < 3) {
			conn.sendErr(ErrCode.Usage, "money-update <money> <player>");
			return;
		}

		name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

		try {
			amount = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "money-update <money> <player>");
			return;
		}

		mu.moneyUpdate(amount, name);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {/* herp derp */}

	public interface MoneyUpdater {
		public void moneyUpdate(int amount, String name);
	}

	public void addMoneyUpdater(MoneyUpdater mu) {
		this.mu = mu;
	}
}
