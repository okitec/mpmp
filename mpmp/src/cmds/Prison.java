package cmds;

import java.util.Arrays;
import main.Conn;
import main.ErrCode;
import model.Player;

/**
 * S->C
 * @author Leander
 */
public class Prison implements CmdFunc {

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
			break;
		case "leave":
			p.prison(false);
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
	
}
