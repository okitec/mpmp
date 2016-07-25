package net;

import java.util.Arrays;

import model.Player;
import view.Displayer;

/**
 * S->C
 */
public class PosUpdate implements CmdFunc {
	private PosUpdater pu;
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int pos;
		String name;

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

		name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
		pu.posUpdate(pos, name);
		d.reset();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}

	public void addDisplayer(Displayer d) {
		this.d = d;
	}

	public interface PosUpdater {
		public void posUpdate(int pos, String name);
	}

	public void addPosUpdater(PosUpdater pu) {
		this.pu = pu;
	}
}
