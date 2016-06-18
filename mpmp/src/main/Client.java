package main;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import model.Player;
import model.Player.Mode;

/**
 * Class Client implements the connection to a Client and has static methods for
 * sending packets to all clients. No gameplay state is saved here except a reference
 * to a Player.
 * @author Leander, oki
 */
public class Client extends Conn {
	private static HashSet<Client> clients;
	private Player player;

	public Client(Socket sock) throws IOException {
		super(sock);
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
			if(c != this && c.isSubscribed() && name.equals(c.player.getName()))
				return false;

		try {
			col = Color.decode(color);
		} catch(NumberFormatException nfe) {
			col = Color.BLACK;  // XXX default color - randomise
		}

		this.player = new Player(col, mode, name);
		return true;
	}

	/**
	 * Give up, auction everything, become a spectator.
	 */
	public void ragequit() {
		if(player == null)
			return;

		player.ragequit();
		player = new Player(player.getColor(), player.getName(), Player.Mode.Spectator);
	}

	public String getName() {
		if(player != null)
			return player.getName();
		return null;
	}

	public boolean isSubscribed() {
		return player != null;
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
		for (Client receiver : clients) {
			receiver.send("clientlist-update " + clients.size());
			for (Client c : clients) {
				receiver.send("" + c.player);
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
