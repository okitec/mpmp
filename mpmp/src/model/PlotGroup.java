package model;

import java.util.HashSet;

/**
 * PlotGroup describes a group of plots: ordinary coloured plots,
 * train stations, factories, entirely special fields.
 * There is no static PlotGroup table; there are only the references
 * in the Plot.allplots table; a Plot remembers its group.
 */
public class PlotGroup {
	private static final int NoBuild = 0x01;
	private static final int NoBuy   = 0x02;

	private HashSet<Plot> plots;
	private final int attr;

	public PlotGroup(int attr) {
		plots = new HashSet<>();
		this.attr = attr;
	}

	/**
	 * @return true when a house can be built on the plot.
	 */
	public int canAddHouse(Player p, HousePlot plot) {
		for(Plot pl : plots) {
			HousePlot hp = (HousePlot) pl;

			// You must control all plots in the group.
			if(hp.getOwner() != p)
				return -2;

			// You can't build a house if another plot of the group has fewer houses already.
			if(hp.getHouses() < plot.getHouses())
				return -3;
		}

		return 1;	
	}

	/**
	 * @return true when house can be removed.
	 */
	public boolean canRmHouse(Player p, HousePlot plot) {
		for (Plot pl : plots) {
			HousePlot hp = (HousePlot) pl;
			if (hp.getHouses() > plot.getHouses())
				return false;
		}

		return true;
	}

	/**
	 * @return number of plots in the group owned by a player.
	 */
	public int plotsOwned(Player p) {
		int n;

		n = 0;
		for(Plot pl : plots)
			if(pl.owner == p)
				n++;

		return n;
	}

	private void add(Plot p) {
		plots.add(p);
	}
}
