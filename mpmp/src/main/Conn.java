package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.Socket;

import cmds.Cmd;

/**
 * Class Conn implements the connection to a Client or the server.
 * @author Leander, oki
 */
public class Conn {
	private BufferedReader in;
	private PrintWriter out;
	private Socket sock;

	public Conn(Socket sock) throws IOException {
		this.sock = sock;

		// It is detestable that UTF-8 is not the default -oki
		in = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
		out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"), true);
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

			// Log all protocol packets. Neat debugging hook.
			System.err.println("->proto: " + line);

			delim = line.indexOf(' ');
			if (delim < 0)
				delim = line.length();

			cmd = line.substring(0, delim);
			if(cmd == "+JAWOHL" || cmd == "-NEIN")  // XXX show error on -NEIN -oki
				continue;

			c = Cmd.search(cmd);
			if (c == null) {
				sendErr("Command '" + cmd + "' does not exist!");
				continue;
			}

			c.exec(line, this);
		}
	}

	public String readLine() {
		try {
			return in.readLine();
		} catch(IOException ioe) {
			return null;
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

	public void disconnect() {
		try {
			sock.close();
		} catch(IOException ioe) {
			;
		}
	}
}
