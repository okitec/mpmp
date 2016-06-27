/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;

/**
 *
 * @author leander.dreier
 */
/**
	 * A plot that can be bought, sold, turned into a hypothec, ...
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
		
		public boolean sell() { //TODO
			if (owner == null)
				return false;
			
			owner = null;
			return true;
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

		public static Plot search(String name) {
			for (Plot p : allplots)
				if (name.equals(p.getName()))
					return p;
			return null;
		}
	}
