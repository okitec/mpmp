package cmds;

import main.Client;
import main.Conn;
import main.ErrCode;
import model.Player;

/**
 * Sets a player free by using either a unjail-card or by paying the fee.
 * @author Leander
 */
public class Unjail implements CmdFunc{
	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		Player p = Player.search(((Client) conn).getName());
		
		if (!p.isPlayer()){
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}
		
		if (args.length < 2) {
			conn.sendErr(ErrCode.UnjailUsage);
			return;
		}
		
		switch (args[1]) {
		case "card":
			if (!p.useUnjailCard())
				System.err.println("Player not in prison");
			break;
		case "money":
			if (!p.addMoney(-Player.UnjailFee))
				conn.sendErr(ErrCode.MissingMoney);
			else
				p.prison(false);
			break;
		default:
			conn.sendErr(ErrCode.UnjailUsage);
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
