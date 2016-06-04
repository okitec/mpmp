package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import cmds.Cmd;

/**
 * Class Client implements the connection to a Client and has static methods for
 * sending packets to all clients. No gameplay state is saved here, only a name for
 * chatting.
 * @author Leander, oki
 */
public class Client extends Conn {
	private String name;

	// XXX why is "private enum Mode {...} mode;" not allowed in Java? Because
	// enums are objects. Uh.
	public enum Mode {
		PreSubscribe, Spectator, Player
	}

	private Mode mode;

	public Client(BufferedReader in, PrintWriter out) {
		super(in, out);
		this.name = null;
		this.mode = Mode.PreSubscribe;
		send("Willkommen, Genosse! Subscriben Sie!");
	}

	public void subscribe(Mode mode, String name) {
		this.mode = mode;
		this.name = name;
		this.sendOK();
	}

	public String getName() {
		return name;
	}

	public Mode getMode() {
		return mode;
	}

	/**
	 * Send a client list to all clients.
	 */
	public static void listClients() {
		// XXX use broadcast
		for (Client c : Main.getClients()) {
			for (Client player : Main.getClients()) {
				String name = player.getName();
				String mode = player.getMode().toString();
				c.send(mode + " " + name);
			}
		}
	}
	
	/**
	 * Send a string to all clients.
	 */
	public static void broadcast(String s) {
		for (Client c : main.Main.getClients())
			c.send(s);
	}
}
