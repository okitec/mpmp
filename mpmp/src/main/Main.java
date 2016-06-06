/**
 * mpmp server; see proto.md for the protocol
 */
package main;

import static main.ClientMain.clientmain;
import static main.SrvMain.srvmain;

/**
 * Main class of the server
 *
 * @author Leander, oki
 */
public class Main {
	public static void main(String[] args) {
		if(args.length < 2)
			usage();

		switch(args[1]) {
		case "server":
			srvmain(args);
			break;
		case "client":
			clientmain(args);
			break;
		default:
			usage();
		}

	}

	private static void usage() {
		System.err.println("Usage: mpmp <server|client>");
		System.exit(1);
	}
}
