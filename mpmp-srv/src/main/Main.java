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

/**
 * Main class of the server
 *
 * @author Leander, oki
 */
public class Main {
	public static void main(String[] args) {
		ServerSocket listener = null;
		Client.init();

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
							c.handle();
							// XXX duplication -oki
							System.out.println("Client " + sock + " disconnected");
							if(c != null)
								c.remove();
						} catch (SocketException se) {
							// XXX duplication -oki
							System.out.println("Client " + sock + " disconnected");
							if(c != null)
								c.remove();
						} catch (IOException ioe) {
							// XXX duplication -oki
							System.out.println("Client " + sock + " disconnected");
							if(c != null)
								c.remove();
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
}
