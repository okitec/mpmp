package model;

/**
 * Your ordinary plot that you can build houses on, mark as hypothec
 */
public class HousePlot extends Plot {
	public static final int MaxHouses = 5;   /* hotel counts as five houses */

	private final int rent[];         /* six entries: plot alone, with one house, two houses, ..., hotel */
	private final int housePrice;
	private int houses;

	public HousePlot(PlotGroup group, String name, int price, int[] rent, int housePrice) {
		super(group, name, price);

		this.rent = rent;
		this.housePrice = housePrice;
	}
	
	public int getHousePrice() {
		return housePrice;
	}

	@Override
	public int getHouses() {
		return houses;
	}

	@Override
	public void setHouses(int houses) {
		if(houses <= MaxHouses)
			this.houses = houses;
	}

	@Override
	public boolean canSell() {
		if(houses > 0)
			return false;

		return true;
	}

	/**
	 * Add a house to the street, if possible.
	 */
	public int addHouse() {
		// One hotel per street.
		if(houses == 5)
			return -1;

		if(group.canAddHouse(owner, this) != 1)
			return group.canAddHouse(owner, this);

		if(SrvModel.self.getSrvPlayer(owner).addMoney(-housePrice) < 0) {
			SrvModel.self.getSrvPlayer(owner).addMoney(housePrice);
			return -4;
		}
		
		houses++;
		return 1;
	}

	/**
	 * Remove a house and give it back to the bank for half the price.
	 */
	public int rmHouse() {
		if (houses == 0)
			return -1;

		if(!group.canRmHouse(owner, this))
			return -2;

		SrvModel.self.getSrvPlayer(owner).addMoney(housePrice/2);
		return 1;
	}

	/**
	 * Make the visitor PAY for staying on our lands! Mwahahah
	 */
	@Override
	public int payRent(SrvPlayer visitor) {
		int r = rent[houses];

		if(owner == null)
			return 0;

		if(hypothec)
			return 0;

		visitor.addMoney(-r);
		SrvModel.self.getSrvPlayer(owner).addMoney(r);
		return r;
	}
}
