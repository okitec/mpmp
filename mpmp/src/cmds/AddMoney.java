package cmds;

import java.util.Arrays;

import main.Conn;
import main.ErrCode;
import view.Displayer;

/**
 * add-money S->C packet
 * @author oki
 */
public class AddMoney implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String reason;
		String args[];
		int amount;

		args = line.split(" ");
		if(args.length < 3) {
			// XXX one syntax/usage error code should suffice -oki
			conn.sendErr("Usage: add-money <amount> <reason>");
			return;
		}

		try {
			amount = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			conn.sendErr("Not a number");  // XXX make general error?
			return;
		}

		reason = null;
		// XXX make a static method for recombining the string -oki
		for (String s : Arrays.copyOfRange(args, 2, args.length)) {
			if (reason == null)
				reason = s;
			else
				reason +=  " " + s;
		}

		d.show("add-money: " + amount + " " + reason);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}

	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
