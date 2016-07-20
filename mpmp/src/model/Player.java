package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Player represents spectators and the actual, active players. The info stored here exists in both
 * client and server and is kept up-to-date by the clientlist-update packet and others.
 * XXX HUGE MESS AT THE MOMENT (probably the ugliest code) -oki
 */
public class Player {

	// XXX move to a new file of constants?
	private static final int StartMoney = 30000; // XXX value
	private static final int Wage = 4000;  // XXX value
	private static final int IncomeTax = 4000;  // XXX value
	private static final int ExtraTax = 2000;  // XXX value
	public static final int UnjailFee = 1000;
	private static Player currentPlayer;

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
	private int money; /* actual liquid money */
	private int pos;
	/* 0 is start; counted clockwise */
	private boolean inPrison;
	private int unjails;

	/* number of unjail cards */
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

	public String getPlots() {
		return plots.toString();
	}

	public boolean isInJail() {
		return inPrison;
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

	/* GAME LOGIC */
	/**
	 * Adds or removes money from a player. If the player can't pay, false is returned.
	 */
	public boolean addMoney(int sum) {
		// XXX simply allow negative money instead of returning right here might be better. -oki

		/* Sum is often negative. Have we positive money if we add sum? */
		if (money + sum < 0) {
			return false;
		}

		money += sum;
		return true;
	}

	/**
	 * Collect a sum of money from each player.
	 */
	public void collect(int sum) {
		int total;

		if (sum < 0) {
			return;
		}

		total = 0;
		for (Player p : subscribed) {
			if (p.addMoney(-sum) == false) {
				continue; // XXX eh, what to do (loaning is not implemented)
			}
			total += sum;
		}

		money += total;
	}

	/**
	 * Add or remove hypothec status of plot.
	 */
	public void hypothec(Plot p, boolean addhyp) {
		if (addhyp) {
			if (p.isHypothec()) {
				return;
			}
			hypothecs.add(p);
			money += p.hypothec(true);
		} else {
			hypothecs.remove(p);
			money -= p.hypothec(false);
		}
	}

	/**
	 * Give up and auction everything of value and delete from player list.
	 */
	public void ragequit() {
		// XXX auction all the plots and houses the player had
		// We'll be replaced by a fresh spectator Player, so remove ourselves.
		remove();
	}

	/**
	 * Add a unjail card to the player.
	 */
	public void addUnjailCard() {
		unjails++;
	}

	/**
	 * Give one unjail card to player p. Usually against monetary compensation.
	 */
	public boolean giveUnjailCard(Player p) {
		if (unjails <= 0) {
			return false;
		}

		unjails--;
		p.unjails++;
		return true;
	}

	/**
	 * Leave the prison by using a unjail card.
	 */
	public boolean useUnjailCard() {
		if (!inPrison) {
			return false;
		}

		inPrison = false;
		unjails--;
		return true;
	}

	/**
	 * Move a number of fields. If you pass the start, you get paid.
	 */
	public boolean move(int distance) {
		return teleport((pos + distance) % Field.Nfields, true);
	}

	/**
	 * Teleport the player to the position; fails if they are in prison.
	 *
	 * @param pos index of destination (start is 0; counted clockwise)
	 * @param passStart whether you get money if you pass start.
	 */
	public boolean teleport(int pos, boolean passStart) {
		int oldpos;

		if (inPrison) {
			return false;
		}

		oldpos = this.pos;
		this.pos = pos;

		/* Axiom: pos < oldpos is true if we passed the start */
		if (passStart && pos < oldpos) {
			addMoney(Wage);
		}

		switch (pos) {
			case Field.EventField1:
			case Field.EventField2:
			case Field.EventField3:
				Card.getRandomCard(true).run(this);
				break;

			case Field.CommunityField1:
			case Field.CommunityField2:
			case Field.CommunityField3:
				Card.getRandomCard(false).run(this);
				break;

			case Field.IncomeTax:
				addMoney(-IncomeTax);
				break;

			case Field.FreeParking:
				// XXX implement rule variation where you get all the tax money?
				break;

			case Field.Police:
				prison(true);
				break;

			case Field.ExtraTax:
				addMoney(-ExtraTax);
				break;

			default:
		}

		return true;
	}

	/**
	 * @param enter if true, you enter the prison; if false, you leave it.
	 */
	public void prison(boolean enter) {
		if (enter) {
			teleport(Field.Prison, false);
			inPrison = true;
		} else {
			inPrison = false;
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
