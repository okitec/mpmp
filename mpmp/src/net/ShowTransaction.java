package net;

import java.awt.Color;
import java.util.Arrays;

import view.Displayer;

/**
 * show-transaction S->C packet
 */
public class ShowTransaction implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String reason;
		String args[];
		Color col;
		int amount;

		args = line.split(" ");
		if(args.length < 3) {
			conn.sendErr(ErrCode.Usage, "show-transaction <amount> <reason>");
			return;
		}

		try {
			amount = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Internal, "'" + args[1] + "' is not a number");
			return;
		}

		reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

		if(amount < 0)
			col = Color.RED;
		else
			col = new Color(144, 238, 144); /* X11 Light green (#90EE90) */

		d.show("show-transaction: " + amount + " " + reason, col);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		System.err.println("Can't happen: " + err.getMessage());
	}

	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
