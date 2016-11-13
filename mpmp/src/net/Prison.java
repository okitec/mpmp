package net;

import java.util.Arrays;

import view.Displayer;

/**
 * prison S->C
 * XXX rename to PrisonUpdate
 */
public class Prison implements CmdFunc {
	private PrisonUpdater pu;
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		String name;

		if (args.length < 3) {
			conn.sendErr(ErrCode.Usage, "prison <enter|leave> <Spieler>");
			return;
		}

		name = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

		switch (args[1]) {
		case "enter":
			pu.prisonUpdate(true, name);
			d.show("Sie gehen in das Gefängnis...");
			conn.sendOK();
			break;
		case "leave":
			pu.prisonUpdate(false, name);
			d.show("Sie dürfen das Gefängnis verlassen...");
			conn.sendOK();
			break;
		default:
			conn.sendErr(ErrCode.Usage, "prison <enter|leave> <Spieler>");
			return;
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}

	public void addDisplayer(Displayer d) {
		this.d = d;
	}

	public interface PrisonUpdater {
		public void prisonUpdate(boolean enter, String name);
	}

	public void addPrisonUpdater(PrisonUpdater pu) {
		this.pu = pu;
	}
}
