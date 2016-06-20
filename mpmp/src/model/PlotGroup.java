package model;

import java.util.HashSet;

/**
 * PlotGroup describes a group of plots sharing one color.
 *
 * @author oki
 */
public class PlotGroup {
	private HashSet<Plot> plots;

	public PlotGroup() {
		plots = new HashSet<>();
	}

	/**
	 * A plot that can be bought, sold, turned into a hypothec, ...
	 */
	public class Plot {
		public static final int MaxHouses = 5;        /* hotel counts as five houses */

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

		public boolean buy(Player buyer) {
			if(owner != null)
				return false;

			if(buyer.addMoney(-price)) {
				owner = buyer;
				return true;
			}

			return false;
		}

		public Player getOwner() {
			return owner;
		}

		public int getHouses() {
			return houses;
		}

		public boolean addHouse() {
			// One hotel per street.
			if(houses == 5)
				return false;

			if(!group.canAddHouse(owner, this))
				return false;

			if(owner.addMoney(-housePrices[houses])) {
				houses++;
				return true;
			}

			return false;
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

	}

	private boolean canAddHouse(Player p, Plot plot) {
		for(Plot pl : plots) {
			// You must control all plots in the group.
			if(pl.getOwner() != p)
				return false;

			// You can't build a house if another plot of the group has fewer houses already.
			if(pl.getHouses() < plot.getHouses())
				return false;
		}

		return true;	
	}
}
