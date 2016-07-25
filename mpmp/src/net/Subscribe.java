package net;

import java.util.Arrays;

import srv.Client;
import model.Player.Mode;
import model.SrvModel;

/**
 * subscribe C->S packet
 */
public class Subscribe implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		String[] args;
		String name = null;
		Client c = (Client) conn;
		Mode mode; 

		if (SrvModel.self.m.running()) {
			conn.sendErr(ErrCode.GameRunning);
			return;
		}

		args = line.split(" ");
		if (args.length < 4) {
			c.sendErr(ErrCode.Usage, "subscribe <spectator|player> #<rgb hex triplet> <Name>");
			return;
		}

		name = String.join(" ", Arrays.copyOfRange(args, 3, args.length));

		switch (args[1]) {
		case "player":
			mode = Mode.Player;
			break;
		case "spectator":
			mode = Mode.Spectator;
			break;
		default:
			c.sendErr(ErrCode.Usage, "subscribe <spectator|player> #<rgb hex triplet> <Name>");
			return;
		}

		if(c.subscribe(args[2], mode, name) == false)
			c.sendErr(ErrCode.NameTaken);
		else {
			c.sendOK();
			Client.listClients();
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		switch (err) {
		case NameTaken:
			suerr.subscribeErr();
			break;
		case GameRunning:
			suerr.subscribeErr();
			break;
		default:
			System.err.println("Can't happen: " + err.getMessage());
			break;
		}
	}
	
	public interface SubscribeErrer {
		public void subscribeErr();
	}
	private SubscribeErrer suerr;
	
	public void addSubscribeErrer(SubscribeErrer suerr) {
		this.suerr = suerr;
	}
}
