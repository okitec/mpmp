package model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * A plot that can be bought, sold, turned into a hypothec, ...
 *
 * @author oki, Leander
 */
public class Plot {
	public static final int MaxHouses = 5;        /* hotel counts as five houses */
	private static HashSet<Plot> allplots;

	private final PlotGroup group;
	private final String name;
	private final int price;
	private final int hypothecValue;  /* money gained by making it a hypothec; usually half the price */
	private final int rent[];         /* six entries: plot alone, with one house, two houses, ..., hotel */
	private final int housePrices[];

	private Player owner;
	private int houses;
	private boolean hypothec;

	public Plot(PlotGroup group, String name, int price, int[] rent, int[] housePrices) {
		this.group = group;
		this.name = name;
		this.price = price;
		this.hypothecValue = price/2; // XXX is this always true?
		this.rent = rent;
		this.housePrices = housePrices;
	}
	
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
	
	public boolean buy(Player buyer) {
		if(owner != null)
			return false;

		owner = buyer;
		return true;
	}
	
	public boolean resell(Player buyer) {
		if (owner == null)
			return false;
		
		owner = buyer;
		return true;
	}

	public Player getOwner() {
		return owner;
	}

	public int getHouses() {
		return houses;
	}

	public int addHouse() {
		// One hotel per street.
		if(houses == 5)
			return -1;

		if(group.canAddHouse(owner, this) != 1)
			return group.canAddHouse(owner, this);

		if(!owner.addMoney(-housePrices[houses])) {
			return -4;
		}
		
		houses++;
		return 1;
	}

	/**
	 * Makes the visitor PAY for staying on our lands! Mwahahah
	 */
	public int payRent(Player visitor) {
		int r = rent[houses];

		if(hypothec)
			return 0;

		visitor.addMoney(-r);
		return r;
	}

	public boolean isHypothec() {
		return hypothec;
	}

	/**
	 * Add or remove hypothec status. Return the hypothec's value.
	 */
	public int hypothec(boolean addhyp) {
		hypothec = addhyp;
		return hypothecValue;
	}

	public static Plot search(String name) {
		for (Plot p : allplots)
			if (name.equals(p.getName()))
				return p;
		return null;
	}
	
	public static String matches(String name) {
		Iterator i = allplots.iterator();
		String bestMatch = "";
		while (i.hasNext()) {
			Plot p = (Plot) i.next();
			String pname = p.getName();
			if (name.matches(pname + "(.*)") && pname.length() > bestMatch.length())
				bestMatch = pname;
		}
		if (bestMatch.equals(""))
			return null;
		return bestMatch;
	}
}
