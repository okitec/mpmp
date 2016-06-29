package model;

import java.util.HashSet;

/**
 * PlotGroup describes a group of plots: ordinary coloured plots,
 * train stations, factories, entirely special fields.
 * There is no static PlotGroup table; there are only the references
 * in the Plot.allplots table; a Plot remembers its group.
 *
 * @author oki
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
	 * Tells whether the player can actually build a house on this plot.
	 */
	public boolean canAddHouse(Player p, Plot plot) {
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

	private void add(Plot p) {
		plots.add(p);
	}

	/**
	 * Initialise the Plot.allplots table.
	 */
	public static void init() {
		Plot.init();

		
	}
}
