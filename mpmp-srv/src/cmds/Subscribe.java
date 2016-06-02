package cmds;

import java.util.Arrays;

import main.Client;
import main.Client.Mode;
import main.Main;

public class Subscribe implements CmdFunc {
	private static final String SubscribeSyntax = "Syntax: subscribe [spectator|player] <Name>";
	
	@Override
	public void exec(String line, Client c) {
		String[] args;
		String name = null;
		String msg;

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

		for (Client cl : Main.getClients())
			if (name.equals(cl.getName()) && cl != c) {
				c.sendErr("Name already taken!");
				return;
			}

		switch (c.getMode()) {
		case PreSubscribe:
			msg = "Subscribed new ";
			break;
		case Player:
			msg = "Player " + c.getName() + " resubscribed as ";
			break;
		case Spectator:
			msg = "Spectator " + c.getName() + " resubscribed as ";
			break;
		default:
			c.sendErr("Internal bug - invalid game mode");
			return;
		}

		// XXX just send player list, according to spec
		switch (args[1]) {
		case "player":
			c.subscribe(Mode.Player, name);
			Client.broadcast(msg + "player under the name " + name);
			break;
		case "spectator":
			c.subscribe(Mode.Spectator, name);
			Client.broadcast(msg + "spectator under the name " + name);
			break;
		default:
			c.sendErr(SubscribeSyntax);
		}
	}
}
