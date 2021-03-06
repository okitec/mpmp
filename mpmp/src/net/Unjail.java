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
				// XXX send error
			else
				conn.sendOK();
			break;
		case "money":
			if (sp.addMoney(-SrvPlayer.UnjailFee) < 0) {
				sp.addMoney(SrvPlayer.UnjailFee);
				conn.sendErr(ErrCode.MissingMoney);
			} else
				sp.prison(false);

			conn.sendOK();
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
