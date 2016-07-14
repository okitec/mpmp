package net;

import java.util.Arrays;

import model.Player;
import view.Displayer;

/**
 * S->C
 */
public class PosUpdate implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int pos;
		Player p;

		if(args.length < 3) {
			conn.sendErr(ErrCode.Usage, "pos-update <Zielposition> <Spieler>");
			return;
		}

		try {
			pos = Integer.parseInt(args[1]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "pos-update <Zielposition> <Spieler>");
			return;
		}

		p = Player.search(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
		if (p == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}
		System.out.println("->pos-update " + pos + " " + p.getName());
		p.teleport(pos, false);
		d.reset();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}

	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
