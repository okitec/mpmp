package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import view.Displayer;

/**
 * sell-plot C->S
 */
public class SellPlot implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		Player p, buyer;
		Plot plot;
		String[] args = line.split(" ");
		int price = -1;

		p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "sell-plot <Position> <Preis> <Käufer>");
			return;
		}

		plot = null;
		// XXX see BuyPlot: we need a pos-to-plot mapping
		if(plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (p != plot.getOwner()) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}

		if (!plot.canSell()) {
			conn.sendErr(ErrCode.PlotWithHouse);
			return;
		}

		try {
			price = Integer.parseInt(args[2]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "sell-plot <Position> <Preis> <Käufer>");
			return;
		}

		String bname = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
		buyer = Player.search(bname);
		if(buyer == null) {
			conn.sendErr(ErrCode.NoSuchPlayer, "buyer '" + bname + "'");
			return;
		}

		if (!buyer.addMoney(-price)) {
			conn.sendErr(ErrCode.MissingMoney, "" + price);
			return;
		}

		p.addMoney(price);
		plot.resell(buyer);

		conn.sendOK();
		conn.send("show-transaction " + price + " Resell plot " + plot.getName());
		Client.broadcast("money-update" + p.getMoney() + " " + p.getName());
		Client.broadcast("money-update" + buyer.getMoney() + " " + buyer.getName());
		Client.broadcast("plot-update " + plot);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
