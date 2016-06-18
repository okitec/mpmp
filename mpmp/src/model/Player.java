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
	private static final int StartCash = 30000; // XXX value

	public enum Mode {
		Spectator, Player
	}

	/* common to spectators and players */
	private String name;
	private Color color;
	private Mode mode;

	/* only active players */
	private static HashSet<Player> players;
	private HashSet<PlotGroup.Plot> plots;
	private HashSet<PlotGroup.Plot> hypothecs;
	private int cash;                         /* actual liquid money */
	private int hyp;                          /* hypothec money */
	private int pos;                          /* 0 is start; counted clockwise */
	private boolean inPrison;

	public Player(Color color, Mode mode, String name) {
		this.name = name;
		this.color = color;
		this.mode = mode;

		if(mode == Mode.Player) {
			players.add(this);
			plots = new HashSet<>();
			hypothecs = new HashSet<>();
			pos = 0;
			cash = StartCash;
			hyp = 0;
		}
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
	 * Adds or removes money from a player. If the player can't pay, false is returned.
	 */
	public boolean addMoney(int sum) {
		/* Sum is often negative. Have we positive money if we add sum? */
		if(cash + hyp + sum < 0)
			return false;

		cash += sum;

		/* cash exhausted; change to hypothecs instead */
		if(cash < 0) {
			hyp += cash;  /* cash is negative */
			cash = 0;
		}

		return true;
	}

	/**
	 * Add or remove hypothec status of plot.
	 */
	public void hypothec(PlotGroup.Plot p, boolean addhyp) {
		if(addhyp) {
			if(p.isHypothec())
				return;
			hypothecs.add(p);
			hyp += p.hypothec(true);
		} else {
			hypothecs.remove(p);
			hyp -= p.hypothec(false);
		}
	}

	/**
	 * Give up and auction everything of value.
	 */
	public void ragequit() {
		// XXX auction all the plots and houses the player had 
	}

	/**
	 * Initialise the player table.
	 */
	public static void init() {
		players = new HashSet<>();
	}

	/**
	 * Decode a HTML-style RGB hex triplet (#00AABB).
	 */
	public static Color parseColor(String s) {
		try {
			return Color.decode(s);
		} catch(NumberFormatException nfe) {
			return Color.BLACK;  // XXX default color - randomise
		}
	}
}
