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
	public static HashSet<Client> clients; // XXX write fancy getter if Hanauska wants it

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket listener = null;
		clients = new HashSet<Client>();

		try {
			listener = new ServerSocket(1918);

			for (;;) {
				final Socket sock = listener.accept();
				new Thread(new Runnable() {
					@Override
					public void run() {
						System.out.println("Client connected through " + sock);
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
							PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
							Client c = new Client(in, out);
							clients.add(c);
							c.handle();
						} catch (SocketException se) {
							System.out.println("Client " + sock + " disconnected");
						} catch (IOException ioe) {
							;
						}
					}
				}).start();
			}
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
