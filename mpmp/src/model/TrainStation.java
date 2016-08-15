package model;

public class TrainStation extends Plot {
	private static final int baseRent = 500; // XXX value

	public TrainStation(PlotGroup group, String name, int price) {
		super(group, name, price);
	}

	@Override
	public int payRent(SrvPlayer visitor) {
		int r;
		int n;

		if(owner == null)
			return 0;

		/* Double the rent for every owned station. */
		r = baseRent;
		n = group.plotsOwned(owner);
		for(int i = 0; i < n-1; i++) /* -1: don't double on first trainstation */
			r *= 2;

		visitor.addMoney(-r);
		SrvModel.self.getSrvPlayer(owner).addMoney(r);
		return r;
	}
}
