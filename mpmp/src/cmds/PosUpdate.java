package cmds;

import java.util.Arrays;
import main.Conn;
import main.ErrCode;
import model.Player;

/**
 *
 * @author Leander
 */
public class PosUpdate implements CmdFunc {

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
		
		//TODO
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
