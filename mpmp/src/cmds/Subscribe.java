package cmds;

import java.util.Arrays;

import main.Conn;
import main.Client;
import main.ErrCode;
import model.Player.Mode;

/**
 * subscribe C->S packet
 * @author Leander
 */
public class Subscribe implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		String[] args;
		String name = null;
		Client c = (Client) conn;
		Mode mode; 
		
		args = line.split(" ");
		if (args.length < 4) {
			c.sendErr(ErrCode.SubscribeUsage);
			return;
		}

		for (String s : Arrays.copyOfRange(args, 3, args.length)) {
			if (name == null)
				name = s;
			else
				name = name + " " + s;
		}

		switch (args[1]) {
		case "player":
			mode = Mode.Player;
			break;
		case "spectator":
			mode = Mode.Spectator;
			break;
		default:
			c.sendErr(ErrCode.SubscribeUsage);
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
		if (err == ErrCode.NameTaken) {
			suerr.subscribeErr();
		} else
			System.err.println("Can't happen: " + err.getMessage());
	}
	
	public interface SubscribeErrer {
		public void subscribeErr();
	}
	private SubscribeErrer suerr;
	
	public void addSubscribeErrer(SubscribeErrer suerr) {
		this.suerr = suerr;
	}
}
