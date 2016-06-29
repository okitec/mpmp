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
	
	public int canAddHouse(Player p, Plot plot) {
		for(Plot pl : plots) {
			// You must control all plots in the group.
			if(pl.getOwner() != p)
				return -2;

			// You can't build a house if another plot of the group has fewer houses already.
			if(pl.getHouses() < plot.getHouses())
				return -3;
		}

		return 1;	
	}
}
