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
	private Cmd lastcmd;

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

			if(cmd.equals("+JAWOHL"))
				continue;
			
			if (cmd.equals("-NEIN"))
				errHandle(line, lastcmd);

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
		System.out.println("proto->: " + line);

		// Save the last command for error handling
		int delim = line.indexOf(' ');
		if (delim < 0)
			delim = line.length();
			
		String cmd = line.substring(0, delim);
		lastcmd = Cmd.search(cmd);
	}

	/**
	 * Send a line continuing a multi-line command.
	 */
	public void sendCont(String line) {
		out.println(line);
	}

	public void sendOK() {
		out.println("+JAWOHL");
	}

	public void sendErr(String s) {
		out.println("-NEIN " + s);
	}
	
	public void sendErr(ErrCode err){
		out.println("-NEIN " + err.getCode() + " " + err.getMessage());
	}
	
	public void disconnect() {
		try {
			sock.close();
		} catch(IOException ioe) {
			;
		}
	}
	
	private void errHandle(String line, Cmd cmd) {
		String[] args = line.split(" ");
		ErrCode err;
		
		if (args.length < 2)
			return;
		
		try {
			err = ErrCode.search(Integer.parseInt(args[1]));
			cmd.error(err, line, this);
		} catch(NumberFormatException nfe) {
			//XXX error in error :(
		}
	}
}
