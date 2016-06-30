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

	/**
	 * Initialise the Plot.allplots table.
	 */
	 // @author Oskar Loeprecht, Simon Stolz
	 
	public static void init() {
		Plot.init();
		PlotGroup pg30041c = new PlotGroup(0);
		PlotGroup pg91c3d8 = new PlotGroup(0);
		PlotGroup pg860459 = new PlotGroup(0);
		PlotGroup pgde5126 = new PlotGroup(0);
		PlotGroup pgd01f26 = new PlotGroup(0);
		PlotGroup pgfbe821 = new PlotGroup(0);
		PlotGroup pg168140 = new PlotGroup(0);
		PlotGroup pg183a66 = new PlotGroup(0);
		Plotgroup pgStations = new PlotGroup(PlotGroup.NoBuild)
		
		HousePlot Küstenbahndamm = new HousePlot(pg30041c, "Küstenbahndamm", 1200, int[]{40, 200, 6 200, 3600, 6400, 9000}, int[]{1000};
		HousePlot Zimmererstraße = new HousePlot(pg30041c, "Zimmererstraße", 1200, int[]{80	,400	,1200	,3600	,6400	,9000}, int[]{1000};
		HousePlot Wasserschifffahrtsamt = new HousePlot(pg91c3d8, "Wasserschifffahrstamt", 2000, int[]{120,	600, 1800, 5400, 8000, 11000}, int[]{1000});
		HousePlot AugustBebelStraße = new HousePlot(pg91c3d8, "August-Bebel-Straße", 2000, int[]{120, 600, 1800, 5400, 8000, 11000}, int[]{1000});
		HousePlot AmEisenbahndock = new HousePlot(pg91c3d8, "Am Eisenbahndock", 2400, int[]{160, 800, 2000, 6000, 9000, 12000}, int[]{1000});
		HousePlot Faldernstraße = new HousePlot(pg860459, "Faldernstraße", 2800, int[]{200, 1000, 2000, 6000, 9000, 12000}, int[]{2000});
		HousePlot SiegesAllee = new HousePlot(pg860459, "Sieges-Alee", 2800, int[]{200	,1000	,3000	,9000	,12500	,15000}, int[]{2000});
		HousePlot NeueStraße = new HousePlot(pg860459, "Neue Straße", 3200, int[]{240,	1200,	3600,	10000,	14000,	18000}, int[]{2000});
		HousePlot GorchFockStraße = new HousePlot(pgde5126, "Gorch-Fock-Straße", 3600, int[]{280,	1400,	4000,	11000,	15000,	19000}, int[]{2000});
		HousePlot AmBurggraben = new HousePlot(pgde5126, "Am Burggraben", 3600, int[]{280,	1400,	4000,	11000,	15000,	19000}, int[]{2000});
		HousePlot Bollwerkstraße = new HousePlot(pgde5126, "Bollwerkstraße", 4000, int[]{320,	1600,	4400,	12000,	16000,	20000}, int[]{2000});
		HousePlot Philosophenweg = new HousePlot(pgd01f26, "Philosophenweg", 4400, int[]{360,	1800,	5000,	14000,	17500,	21000}, int[]{3000});
		HousePlot Otto-von-Bismarck-Straße = new HousePlot(pgd01f26, "Otto-von-Bismarck-Straße", 4400, int[]{360,	1800,	5000,	14000,	17500,	21000	}, int[]{3000});
		HousePlot Friedrich-Ebert-Straße = new HousePlot(pgd01f26, "Friedrich-Ebert-Straße", 4800, int[]{400	,2000	,6000	,15000	,18500	,22000}, int[]{3000});
		HousePlot Schützenstraße = new HousePlot(pgfbe821, "Schützenstraße", 5200, int[]{440,	2200,	6600,	16000,	19500,	23000}, int[]{3000});
		HousePlot Hafenstraße = new HousePlot (pgfbe821, "Hafenstraße",5200, int[]{440, 2200, 6600, 16000, 19500, 23000,},int[]{3000});
		HousePlot HindenburgStraße = new HousePlot (pgfbe821, "Hindenburg-Straße",5600, int[]{480, 2400, 7200, 17000, 20500, 24000},int[]{3000});
		HousePlot Jahnstraße = new HousePlot (pg168140, "Jahnstraße",6000, int[]{480, 2400, 7200, 17000, 20500, 24000},int[]{4000});
		HousePlot KarlMarxStraße = new HousePlot (pg168140,"Karl-Marx-Straße",6000, int[]{520 ,2600 ,7800 ,18000 ,22000 ,25500},int[]{4000});
		HousePlot AmBollwerk = new HousePlot (pg168140,"Am Bollwerk",6400, int[]{560 ,3000, 9000, 20000, 24000, 28000},int[]{4000});
		HousePlot Pariserplatz = new HousePlot (pg183a66,"Pariserplatz",7000, int[]{700, 3500, 10000, 22000, 26000, 30000},int[]{4000});
		HousePlot Triumphstraße = new HousePlot (pg183a66,"Triumphstraße",8000, int[]{1000 ,4000 ,12000 ,28000 ,34000 ,40000},int[]{4000});
		TrainSation BahnhofWeimar = new TrainStation (pgStations,"Bahnhof Weimar",4000);
		TrainSation AnhalterBahnhof = new TrainStation (pgStations,"Anhalter Bahnhof",4000);
		TrainSation Centralbahnhof  = new TrainStation (pgStations,"Centralbahnhof",4000);
		TrainSation Flügelbahnhof = new TrainStation (pgStations,"Flügelbahnhof",4000);
}
