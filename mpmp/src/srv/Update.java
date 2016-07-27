package srv;

import model.SrvPlayer;

/**
 * Class update contains static methods for sending update packets. They are mainly called by the server model.
 */
public class Update {
	public static void pos(SrvPlayer sp, int pos) {
		Client.broadcast("pos-update " + pos + " " + sp.p.getName());
	}

	public static void prison(SrvPlayer sp, boolean enter) {
		Client.broadcast("prison " + (enter?"enter ":"leave ") + sp.p.getName());
	}

	/**
	 * Money transaction update: tell everyone the new amount, show transaction sum and reason to receiver.
	 */
	public static void transact(SrvPlayer recv, int sum, String reason) {
		Client.broadcast("money-update " + recv.p.getMoney() + " " + recv.p.getName());
		(Client.search(recv.p.getName())).send("show-transaction " + sum + " " + reason);
	}
}
