package main;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import model.Player;

/**
 * Class Client implements the connection to a Client and has static methods for
 * sending packets to all clients. No gameplay state is saved here, only a name for
 * chatting.
 * @author Leander, oki
 */
public class Client extends Conn {
	private static HashSet<Client> clients;
	private String name;
	private Player player;

	// XXX why is "private enum Mode {...} mode;" not allowed in Java? Because
	// enums are objects. Uh.
	public enum Mode {
		PreSubscribe, Spectator, Player
	}

	private Mode mode;

	public Client(Socket sock) throws IOException {
		super(sock);
		this.name = null;
		this.player = null;
		this.mode = Mode.PreSubscribe;
		clients.add(this);
		send("Willkommen, Genosse! Subscriben Sie!");
	}

	/**
	 * Method subscribe subscribes a client as discussed in the protocol.
	 * @return false on failure (name already used or nil), true otherwise
	 */
	public boolean subscribe(String color, Mode mode, String name) {
		Color col;

		if(name == null)
			return false;

		for(Client c : clients)
			if(name.equals(c.name) && c != this)
				return false;

		try {
			col = Color.decode(color);
		} catch(NumberFormatException nfe) {
			col = Color.BLACK;  // XXX default color - randomise
		}

		this.player = new Player(col);
		this.mode = mode;
		this.name = name;
		return true;
	}

	public String getName() {
		return name;
	}

	public Mode getMode() {
		return mode;
	}

	/**
	 * Remove this client from the client list.
	 */
	public void remove() {
		clients.remove(this);
	}

	/**
	 * Send a client list to all clients.
	 */
	public static void listClients() {
		// XXX use broadcast
		for (Client c : clients) {
			c.send("clientlist-update " + clients.size());
			for (Client player : clients) {
				// Convert to hex triplet without alpha value and in uppercase.
				String color = "#" + Integer.toHexString(player.player.getColor().getRGB()).substring(2).toUpperCase();
				c.send(color + ": " + player.mode + ": " + player.name);
			}
		}
	}
	
	/**
	 * Send a string to all clients.
	 */
	public static void broadcast(String s) {
		for (Client c : clients)
			c.send(s);
	}

	/**
	 * Initialise the client table.
	 */
	public static void init() {
		clients = new HashSet<>();
	}
}
