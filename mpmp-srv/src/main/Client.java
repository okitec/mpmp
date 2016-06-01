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
	// XXX why is "private enum Mode {...} mode;" not allowed in Java? Because enums are objects. Uh.
	public enum Mode {PreSubscribe, Spectator, Player}
	private Mode mode;

	public Client(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		this.name = null;
	}

	/**
	 * Handles the connection to a client, reading and writing commands and replies.
	 */
	public void handle() throws SocketException, IOException {
		String line, cmd;
		Cmd c;
		int delim;

		for(;;) {
			line = in.readLine();
			delim = line.indexOf(' ');
			if(delim < 0)
				delim = line.length();

			cmd = line.substring(0, delim);
			c = Cmd.search(cmd);
			if(c == null) {
				sendErr("");
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
		// XXX change spec and allow for a reason string
		out.println("-NEIN");
	}

	public void subscribe(Mode mode, String name) {
		this.mode = mode;
		this.name = name;
	}

	public String name() {
		return name;
	}
}
