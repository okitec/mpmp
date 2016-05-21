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

/**
 * Main class of the server
 *
 * @author Leander, oki
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket listener = null;
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
							out.println("Hallo Welt!");
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
