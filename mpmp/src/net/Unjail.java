package net;

import srv.Client;
import model.Player;
import model.SrvModel;
import model.SrvPlayer;

/**
 * Sets a player free by using either a unjail-card or by paying the fee.
 */
public class Unjail implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		SrvModel sm = SrvModel.self;
		String[] args = line.split(" ");
		SrvPlayer sp;

		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "unjail [card|money]");
			return;
		}

		sp = sm.getSrvPlayer(((Client) conn).getName());
		if (sp == null) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}
		
		switch (args[1]) {
		case "card":
			if (!sp.useUnjailCard())
				System.err.println("Player not in prison");
			break;
		case "money":
			if (sp.addMoney(-SrvPlayer.UnjailFee) < 0) {
				sp.addMoney(SrvPlayer.UnjailFee);
				conn.sendErr(ErrCode.MissingMoney);
				conn.send("money-update " + SrvPlayer.UnjailFee + " Paid unjail fee");
			}
			else
				sp.prison(false);
			break;
		default:
			conn.sendErr(ErrCode.Usage, "unjail [card|money]");
		}

		Client.broadcast("prison leave " + sp.p.getName());
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		conn.send("chat-update " + err.getMessage());
	}
}
