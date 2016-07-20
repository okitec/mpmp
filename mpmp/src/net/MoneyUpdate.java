package net;

import java.util.Arrays;

import model.Player;

/**
 * money-update S->C
 */
public class MoneyUpdate implements CmdFunc {
	private MoneyUpdater mu;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int money;
		String name;
		
		if (args.length < 3) {
			conn.sendErr(ErrCode.Usage, "money-update <Geld> <Spieler>");
			return;
		}
		
		try {
			money = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "money-update <Geld> <Spieler>");
			return;
		}
		
		name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

		mu.moneyUpdate(money, name);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}

	public interface MoneyUpdater {
		public void moneyUpdate(int amount, String name);
	}

	public void addMoneyUpdater(MoneyUpdater mu) {
		this.mu = mu;
	}
}
