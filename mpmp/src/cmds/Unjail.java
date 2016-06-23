package cmds;

import java.util.Arrays;
import main.Conn;
import main.ErrCode;
import model.Player;

/**
 *
 * @author Leander
 */
public class Unjail implements CmdFunc{
	//Usage: unjail [card|money] <player>

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		Player p;
		
		if (args.length < 3) {
			conn.sendErr("Usage ..."); //TODO
			return;
		}
		
		String pname = null;
		for (String s : Arrays.copyOfRange(args, 2, args.length)) {
			if (pname == null)
				pname = s;
			else
				pname += s;
		}
		
		if (Player.search(pname).isPlayer())
			p = Player.search(pname);
		else {
			conn.sendErr("Not a player"); //TODO
			return;
		}
		
		switch (args[1]) {
		case "card":
			if (!p.useUnjailCard())
				conn.sendErr("Player not in prison"); //TODO
			break;
		case "money":
			if (!p.addMoney(-500)) //check;
				conn.sendErr("Not enough money"); //TODO
			else
				p.prison(false);
			break;
		default:
			conn.sendErr("Usage: ..."); //TODO;
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
