package net;

import java.util.Arrays;

import model.Player;

/**
 * S->C
 */
public class MoneyUpdate implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int cash, hyp;
		Player p;
		
		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "money-update <Cash> <Hypothekengeld> <Spieler>");
			return;
		}
		
		try {
			cash = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "money-update <Cash> <Hypothekengeld> <Spieler>");
			return;
		}
		
		try {
			hyp = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "money-update <Cash> <Hypothekengeld> <Spieler>");
			return;
		}
		
		p = Player.search(String.join(" ", Arrays.copyOfRange(args, 3, args.length)));
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
