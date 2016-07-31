package model;

import srv.Update;

/**
 * Class SrvPlayer holds the game logic for players; it stores an object of type Player
 * that is modified accordingly. Appropiate game update packets are sent by these methods,
 * which breaks abstraction but it ensures correctness in a simple manner.

 * Hold one promise:
 * Use these methods if there is one for the purpose, instead of using the Player's setter.
 * This way, the update packets won't be forgotten.
 */
public class SrvPlayer {
	private static final int Wage = 4000;  // XXX value
	private static final int IncomeTax = 4000;  // XXX value
	private static final int ExtraTax = 2000;  // XXX value
	public static final int UnjailFee = 1000;

	public Player p;  /* don't modify */

	public SrvPlayer(Player p) {
		this.p = p;
	}

	/**
	 * Give up and auction everything of value and delete from player list.
	 */
	public void ragequit() {
		// XXX auction all the plots and houses the player had
	}

	/**
	 * Add a unjail card to the player.
	 */
	public void addUnjailCard() {
		p.setUnjails(p.getUnjails() + 1);
	}

	private void rmUnjailCard() {
		p.setUnjails(p.getUnjails() + 1);
	}

	/**
	 * Give one unjail card to the receiver. Usually against monetary compensation.
	 */
	public boolean giveUnjailCard(SrvPlayer recv) {
		if (p.getUnjails() <= 0)
			return false;

		rmUnjailCard();
		recv.addUnjailCard();
		return true;
	}

	/**
	 * Leave the prison by using a unjail card.
	 */
	public boolean useUnjailCard() {
		if (!p.isInJail())
			return false;

		prison(false);
		rmUnjailCard();
		return true;
	}

	/**
	 * Adds or removes money from a player. Returns the amount of money the player has.
	 *
	 * SENDS UPDATE PACKET
	 */
	public int addMoney(int sum) {
		int total = p.getMoney() + sum;
		p.setMoney(total);
		Update.transact(this, sum, "derp"); // XXX redesign addMoney and rename to transact -oki
		return total;
	}

	/**
	 * Collect a sum of money from each player.
	 */
	public void collect(int sum) {
		int total;

		if (sum < 0)
			return;

		total = 0;
		for (SrvPlayer sp : SrvModel.self.getSrvPlayers()) {
			if (sp.addMoney(-sum) < 0) {
				continue; // XXX eh, what to do (loaning is not implemented)
			}
			total += sum;
		}

		p.setMoney(p.getMoney() + total);
	}

	/**
	 * Add or remove hypothec status of plot.
	 * XXX reactivate -oki
	 */
	public void hypothec(Plot plot, boolean addhyp) {
		/*
		if(plot.getOwner() != p)
			return;

		if (addhyp) {
			if (plot.isHypothec())
				return;

			hypothecs.add(p);
			money += p.hypothec(true);
		} else {
			hypothecs.remove(p);
			money -= p.hypothec(false);
		}
		*/
	}

	/**
	 * Move a number of fields. If you pass the start, you get paid.
	 * @return whether moving was possible
	 */
	public boolean move(int distance) {
		return teleport((p.getPos() + distance) % Field.Nfields, true);
	}

	/**
	 * Teleport the player to the position; fails if they are in prison.
	 *
	 * SENDS UPDATE PACKET
	 *
	 * @param pos index of destination (start is 0; counted clockwise)
	 * @param passStart whether you get money if you pass start.
	 * @return whether teleporting was possible
	 */
	public boolean teleport(int pos, boolean passStart) {
		int oldpos;

		if (p.isInJail())
			return false;

		oldpos = p.getPos();
		p.setPos(pos);
		Update.pos(this, pos);

		/* Axiom: pos < oldpos is true if we passed the start */
		if (passStart && pos < oldpos) {
			addMoney(Wage);
		}

		switch (pos) {
			case Field.EventField1:
			case Field.EventField2:
			case Field.EventField3:
				Card.getRandomCard(true).run(this);
				break;

			case Field.CommunityField1:
			case Field.CommunityField2:
			case Field.CommunityField3:
				Card.getRandomCard(false).run(this);
				break;

			case Field.IncomeTax:
				addMoney(-IncomeTax);
				break;

			case Field.FreeParking:
				// XXX implement rule variation where you get all the tax money?
				break;

			case Field.Police:
				prison(true);
				break;

			case Field.ExtraTax:
				addMoney(-ExtraTax);
				break;

			case Field.Start:
				/* Get double wage if you end up on the start. */
				addMoney(Wage);
				break;

			case Field.Prison:
				break;

			default:
				SrvModel.self.m.getPlot(pos).payRent(this);
				break;
		}

		return true;
	}

	/**
	 * SENDS UPDATE PACKET
	 *
	 * @param enter if true, you enter the prison; if false, you leave it.
	 */
	public void prison(boolean enter) {
		if (enter)
			teleport(Field.Prison, false);

		p.setPrison(enter);
		Update.prison(this, enter);
	}
}
