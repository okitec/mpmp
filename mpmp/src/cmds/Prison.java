package cmds;

import java.util.Arrays;

import main.Conn;
import main.ErrCode;
import model.Player;
import view.Displayer;

/**
 * S->C
 *
 * @author Leander
 */
public class Prison implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		Player p;

		if (args.length < 3) {
			conn.sendErr(ErrCode.Usage, "prison <enter|leave> <Spieler>");
			return;
		}

		p = Player.search(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
		if (p == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}

		switch (args[1]) {
		case "enter":
			p.prison(true);
			d.show("Sie gehen in das Gefängnis...");
			break;
		case "leave":
			p.prison(false);
			d.show("Sie dürfen das Gefängnis verlassen...");
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
}
