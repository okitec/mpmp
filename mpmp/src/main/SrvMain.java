/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author leander.dreier
 */
public class SrvMain {
    public static void srvmain(String[] args) {
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
							c = new Client(sock);
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
