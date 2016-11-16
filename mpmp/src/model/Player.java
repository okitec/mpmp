package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Player represents spectators and the actual, active players. The info stored here exists in both
 * client and server and is kept up-to-date by the clientlist-update packet and others.
 */
public class Player {
	// XXX move to a new file of constants?
	private static final int StartMoney = 30000; // XXX value

	public enum Mode {
		Spectator, Player
	}

	/* common to spectators and players */
	private String name;
	private Color color;
	private Mode mode;
	private static ArrayList<Player> subscribed; /* players and spectators */

	/* only active players */
	private HashSet<Plot> plots;
	private HashSet<Plot> hypothecs;
	private int money;
	private int pos;           /* 0 is start; counted clockwise */
	private boolean inPrison;
	private int unjails;       /* number of unjail cards */

	public Player(Color color, Mode mode, String name) {
		this.name = name;
		this.color = color;
		this.mode = mode;

		synchronized (subscribed) {
			subscribed.add(this);
		}

		if (mode == Mode.Player) {
			plots = new HashSet<>();
			hypothecs = new HashSet<>();
			pos = 0;
			money = StartMoney;
		}
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getUnjails() {
		return unjails;
	}

	public void setUnjails(int n) {
		if(n < 0)
			unjails = 0;
		else
			unjails = n;
	}

	// XXX misleading name
	public String getPlots() {
		if(mode == Mode.Player)
			return plots.toString();
		return "";
	}

	// XXX why not inPrison?
	public boolean isInJail() {
		return inPrison;
	}

	public void setPrison(boolean inPrison) {
		this.inPrison = inPrison;		
	}

	public boolean addPlot(Plot plot) {
		return plots.add(plot);
	}

	public boolean rmPlot(Plot plot) {
		return plots.remove(plot);
	}

	public String toString() {
		// Convert to hex triplet without alpha value and in uppercase.
		String col = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
		return col + ": " + mode + ": " + name;
	}

	public void remove() {
		synchronized (subscribed) {
			subscribed.remove(this);
		}
	}

	/* STATIC */

	/**
	 * Reset the subscribed table. Called on start. In the client, this is also called when a
	 * clientlist-update comes in.
	 */
	public static void reset() {
		subscribed = new ArrayList<>();
	}

	/**
	 * Get the subscribed table. XXX abstraction
	 */
	public static ArrayList<Player> getSubscribed() {
		return subscribed;
	}

	/**
	 * Decode a HTML-style RGB hex triplet (#00AABB).
	 */
	public static Color parseColor(String s) {
		try {
			return Color.decode(s);
		} catch (NumberFormatException nfe) {
			return Color.BLACK;  // XXX default color - randomise
		}
	}

	public static int numPlayers() {
		return subscribed.size();
	}

	public static String matches(String name, boolean at) {
		String best = "";

		for (Player p : subscribed)
			if (name.matches(((at)?"@":"") + p.name + "(.*)") && p.name.length() > best.length())
				best = p.name;

		/* Tricky Java: "" != null */
		if(best == "")
			return null;
		return best;
	}

	public static Player search(String name) {
		for (Player p : subscribed) {
			if (name.equals(p.getName())) {
				return p;
			}
		}
		return null;
	}

	public boolean isPlayer() {
		return mode == Mode.Player;
	}
}
