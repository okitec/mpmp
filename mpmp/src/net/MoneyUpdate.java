package net;

import java.util.Arrays;

import model.Player;

/**
 * money-update S->C
 */
public class MoneyUpdate implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int money;
		Player p;
		
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
		
		p = Player.search(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
		if (p == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}
		
		//TODO
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
