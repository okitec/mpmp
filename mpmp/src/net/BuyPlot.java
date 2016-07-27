package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import model.SrvModel;
import model.SrvPlayer;
import view.Displayer;

/**
 * buy-plot C->S packet; see https://github.com/leletec/mpmp/blob/master/proto.md#grundstücke
 */
public class BuyPlot implements CmdFunc {
	
	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		SrvModel sm = SrvModel.self;
		SrvPlayer sp;
		Plot plot;

		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "buy-plot <Position>");
			return;
		}

		sp = sm.getSrvPlayer(((Client) conn).getName());
		if (sp == null) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}
		
		plot = sm.m.getPlot(Integer.parseInt(args[1]));
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		
		if (plot.getOwner() != null) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}
		
		int price = plot.getPrice();
		if (sp.addMoney(-price) < 0) {
			sp.addMoney(price);
			conn.sendErr(ErrCode.MissingMoney, "" + price);
			return;
		}
		
		if (!plot.buy(sp)) {
			conn.sendErr(ErrCode.Internal, "somebody bought it before you");
			sp.addMoney(price);
			return;
		}

		conn.sendOK();
		Client.broadcast("plot-update " + plot); // XXX add to Update
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
