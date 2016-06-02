package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import cmds.Cmd;

public class Client {
	private BufferedReader in;
	private PrintWriter out;
	private String name;

	// XXX why is "private enum Mode {...} mode;" not allowed in Java? Because
	// enums are objects. Uh.
	public enum Mode {
		PreSubscribe, Spectator, Player
	}

	private Mode mode;

	public Client(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		this.name = null;
		this.mode = Mode.PreSubscribe;
		send("Willkommen, Genosse! Subscriben Sie!");
	}

	/**
	 * Handles the connection to a client, reading and writing commands and
	 * replies.
	 */
	public void handle() throws SocketException, IOException {
		String line, cmd;
		Cmd c;
		int delim;

		for (;;) {
			line = in.readLine();
			delim = line.indexOf(' ');
			if (delim < 0)
				delim = line.length();

			cmd = line.substring(0, delim);
			c = Cmd.search(cmd);
			if (c == null) {
				sendErr("Command does not exist!");
				continue;
			}

			c.exec(line, this);
		}
	}

	public void send(String line) {
		out.println(line);
	}

	public void sendOK() {
		out.println("+JAWOHL");
	}

	public void sendErr(String s) {
		out.println("-NEIN " + s);
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
	public static void listCLients() {
		// XXX use sendToAll
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
