/**
 * mpmp server; see proto.md for the protocol
 */
package main;

import java.io.IOException;
import static main.ClientMain.clientmain;
import static main.SrvMain.srvmain;

/**
 * Main class of the server
 */
public class Main {
	public static void main(String[] args) throws IOException {
		if(args.length < 1)
			usage();

		// 0th argument is not the command in Java -oki
		switch(args[0]) {
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
