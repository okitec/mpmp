package cmds;

import java.util.Arrays;

import main.Client;
import main.Client.Mode;
import main.Main;
import model.Msgbuf;

public class Subscribe implements CmdFunc {
	@Override
	public void exec(String line, Client c) {
		String[] args;

		args = line.split(" ");
		if (args.length < 3) {
			c.sendErr("-Syntax: subscribe [spectator|player] <Name>");
			return;
		}
		String name = null;
		for (String s : Arrays.copyOfRange(args, 2, args.length)) {
			if (name == null)
				name = s;
			else
				name = name + " " + s;
		}
		for (Client cl : Main.getClients())
			if (name.equals(cl.getName()) && cl != c) {
				c.sendErr("-This name is already taken!");
				return;
			}

		String msg;
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
			c.sendErr("");
			return;
		}

		switch (args[1]) {
		case "player":
			c.subscribe(Mode.Player, name);
			c.sendOK();
			Msgbuf.send(msg + "player under the name " + name);
			break;
		case "spectator":
			c.subscribe(Mode.Spectator, name);
			c.sendOK();
			Msgbuf.send(msg + "spectator under the name " + name);
			break;
		default:
			c.sendErr("-Syntax: subscribe [spectator|player] <Name>");
		}
	}
}
