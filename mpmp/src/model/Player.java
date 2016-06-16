package model;

import java.awt.Color;
import java.util.HashSet;

/**
 * Player represents spectators and the actual, actuve players.
 * The info stored here exists in both client and server and is
 * kept up-to-date by the clientlist-update packet.
 * @author Leander, oki
 */
public class Player {
	public enum Mode {
		Spectator, Player
	}

	/* common to spectators and players */
	private String name;
	private Color color;
	private Mode mode;

	/* only active players */
	private static HashSet<Player> players;
	private boolean inPrison;
	private int money;

	public Player(Color color, Mode mode, String name) {
		this.name = name;
		this.color = color;
		this.mode = mode;

		if(mode == Mode.Player)
			players.add(this);
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public String toString() {
		// Convert to hex triplet without alpha value and in uppercase.
		String col = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
		return col + ": " + mode + ": " + name;
	}

	/**
	 * Initialise the player table.
	 */
	public static void init() {
		players = new HashSet<>();
	}
}
