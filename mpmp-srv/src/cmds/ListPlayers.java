package cmds;

import java.util.Iterator;

import main.Client;
import main.Main;

public class ListPlayers implements CmdFunc {
	@Override
	public void exec(String line, Client c) {
		Iterator<Client> i = Main.getClients().iterator();
		c.sendOK();
		while (i.hasNext()) {
			Client player = i.next();
			String name = player.getName();
			String mode = player.getMode().toString();
			c.send(mode + " " + name);
		}
	}
}
