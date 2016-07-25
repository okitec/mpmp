package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import model.SrvModel;
import model.SrvPlayer;
import view.Displayer;

/**
 * sell-plot C->S
 */
public class SellPlot implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		SrvModel sm = SrvModel.self;
		SrvPlayer sp, buyer;
		Plot plot;
		String[] args = line.split(" ");
		int pos;
		int price = -1;

		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "sell-plot <Position> <Preis> <Käufer>");
			return;
		}

		try {
			pos = Integer.parseInt(args[1]);
			price = Integer.parseInt(args[2]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "sell-plot <Position> <Preis> <Käufer>");
			return;
		}

		sp = sm.getSrvPlayer(((Client) conn).getName());
		if (sp == null) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		plot = sm.m.getPlot(pos);
		if(plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (sp.p != plot.getOwner()) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}

		if (!plot.canSell()) {
			conn.sendErr(ErrCode.PlotWithHouse);
			return;
		}

		String bname = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
		buyer = sm.getSrvPlayer(bname);
		if(buyer == null) {
			conn.sendErr(ErrCode.NoSuchPlayer, "buyer '" + bname + "'");
			return;
		}

		if (buyer.addMoney(-price) < 0) {
			buyer.addMoney(price);
			conn.sendErr(ErrCode.MissingMoney, "" + price);
			return;
		}

		sp.addMoney(price);
		plot.resell(buyer);

		conn.sendOK();
		conn.send("show-transaction " + price + " Resell plot " + plot.getName());
		Client.broadcast("money-update" + sp.p.getMoney() + " " + sp.p.getName());
		Client.broadcast("money-update" + buyer.p.getMoney() + " " + buyer.p.getName());
		Client.broadcast("plot-update " + plot);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
