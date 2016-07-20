package model;

import java.util.HashSet;

/**
 * Overclass for property you can purchase. Concretely implemented
 * by HousePlot and TrainStation.
 */
public abstract class Plot {
	private static HashSet<Plot> allplots;

	protected final PlotGroup group;
	protected final String name;
	protected final int price;
	protected final int hypothecValue;  /* money gained by making it a hypothec; half the price */
	protected Player owner;
	protected boolean hypothec;

	public Plot(PlotGroup group, String name, int price) {
		this.group = group;
		this.name = name;
		this.price = price;
		this.hypothecValue = price / 2;

		allplots.add(this);
	}

	public String getName() {
		return name;
	}

	/**
	 * @return amount of houses; overridden in HousePlot.
	 */
	public int getHouses() {
		return 0;
	}

	public void setHouses(int houses) {
	}

	public int getPrice() {
		return price;
	}

	public String toString() {
		String hyp = (hypothec)? "hypothec": "nohypothec";
		return ""  + name + " " + getHouses() + " " + hyp + " " + owner.getName();
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

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * Makes the visitor PAY for staying on our lands! Mwahahah
	 */
	public abstract int payRent(Player visitor);

	/**
	 * @return true if this plot acts as a hypothec.
	 */
	public boolean isHypothec() {
		return hypothec;
	}

	/**
	 * @return true if the plot can be sold; overriden byHousePlot
	 */
	public boolean canSell() {
		return true;
	}

	/**
	 * Add or remove hypothec status. Return the hypothec's value.
	 */
	public int hypothec(boolean addhyp) {
		hypothec = addhyp;
		return hypothecValue;
	}

	/**
	 * Search for the plot of that name.
	 */
	public static Plot search(String name) {
		for (Plot p : allplots)
			if (name.equals(p.getName()))
				return p;
		return null;
	}

	/**
	 * Find longest string in the input that matches one of the plot names.
	 */
	public static String matches(String s) {
		String bestMatch = "";
		for(Plot p : allplots)
			if (s.matches(p.name + "(.*)") && p.name.length() > bestMatch.length())
				bestMatch = p.name;

		if (bestMatch.equals(""))
			return null;

		return bestMatch;
	}

	/**
	 * Initialise allplots set. It is filled by PlotGroup.init.
	 */
	public static void init() {
		allplots = new HashSet<>();
	}
}
