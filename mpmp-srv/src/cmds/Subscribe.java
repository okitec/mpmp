package cmds;

import java.util.Arrays;

import main.Conn;
import main.Client;
import main.Client.Mode;
import main.Main;

/**
 * subscribe C->s packet
 * @author Leander
 */
public class Subscribe implements CmdFunc {
	private static final String SubscribeSyntax = "Syntax: subscribe [spectator|player] <Name>";
	
	@Override
	public void exec(String line, Conn conn) {
		String[] args;
		String name = null;
		String msg;
		Client c = (Client) conn;
		Client.Mode mode = Client.Mode.PreSubscribe; 

		args = line.split(" ");
		if (args.length < 3) {
			c.sendErr(SubscribeSyntax);
			return;
		}

		for (String s : Arrays.copyOfRange(args, 2, args.length)) {
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
			c.sendErr(SubscribeSyntax);
		}

		if(c.subscribe(mode, name) == false)
			c.sendErr("Name already taken");
		else {
			c.sendOK();
			Client.listClients();
		}
	}
}
