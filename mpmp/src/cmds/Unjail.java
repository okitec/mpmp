package cmds;

import main.Client;
import main.Conn;
import main.ErrCode;
import model.Player;

/**
 * Sets a player free by using either a unjail-card or by paying the fee.
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
			conn.sendErr(ErrCode.Usage, "unjail [card|money]");
			return;
		}
		
		switch (args[1]) {
		case "card":
			if (!p.useUnjailCard())
				System.err.println("Player not in prison");
			break;
		case "money":
			if (!p.addMoney(-Player.UnjailFee)) {
				conn.sendErr(ErrCode.MissingMoney);
				conn.send("money-update " + Player.UnjailFee + " Payed Unjail-fee ");
			}
			else
				p.prison(false);
			break;
		default:
			conn.sendErr(ErrCode.Usage, "unjail [card|money]");
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		conn.send("chat-update " + err.getMessage());
	}
}
