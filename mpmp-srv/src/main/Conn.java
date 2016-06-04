package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import cmds.Cmd;

/**
 * Class Conn implements the connection to a Client or the server.
 * @author Leander, oki
 */
public class Conn {
	private BufferedReader in;
	private PrintWriter out;

	public Conn(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
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
			if(line == null)
				return;  // Connection has been closed...

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
}
