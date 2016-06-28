package cmds;

import main.Conn;
import main.ErrCode;
import model.Player;
import view.Displayer;

/**
 * clientlist-update S->C packet.
 */
public class ClientlistUpdate implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		int nclients;
		String args[];

		args = line.split(" ");
		if(args.length < 2) {
			conn.sendErr(ErrCode.Usage, "clientlist-update <number of clients>");
			return;
		}

		try {
			nclients = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Unexpected, "'" + args[1] + "' is not a number");
			return;
		}

		d.reset();
		Player.reset();
		while(nclients --> 0) {
			String s;
			String fields[];
			Player.Mode mode;

			s = conn.readLine();
			if(s == null) {
				conn.sendErr(ErrCode.EOF);
				return;
			}

			fields = s.split(": ");
			if(fields.length < 3) {
				conn.sendErr(ErrCode.ClientlistUpdateMissingFields);
				return;
			}

			switch(fields[1].toLowerCase()) {
			case "spectator":
				mode = Player.Mode.Spectator;
				break;
			case "player":
				mode = Player.Mode.Player;
				break;
			default:
				conn.sendErr(ErrCode.Unexpected, "bad gamemode '" + fields[1] + "'");
				return;
			}

			d.show("" + new Player(Player.parseColor(fields[0]), mode, fields[2]));
		}

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
