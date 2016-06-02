/**
 * mpmp server; see proto.md for the protocol
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;

/**
 * Main class of the server
 *
 * @author Leander, oki
 */
public class Main {
	private static HashSet<Client> clients;

	public static void main(String[] args) {
		ServerSocket listener = null;
		clients = new HashSet<Client>();

		try {
			listener = new ServerSocket(1918);

			// XXX total exception madness - WHY, JAVA, WHY?
			for (;;) {
				final Socket sock = listener.accept();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Client c = null;

						System.out.println("Client connected through " + sock);
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
							PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
							c = new Client(in, out);
							clients.add(c);
							c.handle();
						} catch (SocketException se) {
							System.out.println("Client " + sock + " disconnected");
							if(c != null)
								clients.remove(c);
						} catch (IOException ioe) {
							;
						}
					}
				}).start();
			}
		}
		catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}

	// XXX this seems out of place and should be in the Client class -oki
	public static HashSet<Client> getClients() {
		return clients;
	}
}
