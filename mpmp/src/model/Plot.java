package model;

import java.util.HashSet;

/**
 * Overclass for property you can purchase. Concretely implemented
 * by HousePlot and TrainStation.
 */
public abstract class Plot {
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

	// XXX doesn't report position; buy-plot adds that manually, which is hacky.
	public String toString() {
		String hyp = (hypothec)? "hypothec": "nohypothec";
		return "" + getHouses() + " " + hyp + " " + owner.getName();
	} 

	public boolean buy(SrvPlayer buyer) {
		if(owner != null)
			return false;

		owner = buyer.p;
		return true;
	}

	public boolean resell(SrvPlayer buyer) {
		if (owner == null)
			return false;
		
		owner = buyer.p;
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
	public abstract int payRent(SrvPlayer visitor);

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
}
