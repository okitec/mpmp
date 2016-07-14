package net;

import java.util.Arrays;

import srv.Client;
import model.Player;
import model.Plot;
import view.Displayer;

/**
 * buy-plot C->S packet; see https://github.com/leletec/mpmp/blob/master/proto.md#grundstücke
 */
public class BuyPlot implements CmdFunc {
	private Displayer d;
	
	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		
		Player p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}
		
		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "buy-plot <Position>");
			return;
		}
		
		Plot plot = null;
		// XXX rework plot system: we need pos-to-plot lookup! -oki
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		
		if (plot.getOwner() != null) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}
		
		int price = plot.getPrice();
		if (!p.addMoney(-price)) {
			conn.sendErr(ErrCode.MissingMoney, "" + price);
			return;
		}
		
		if (!plot.buy(p)) {
			conn.sendErr(ErrCode.Internal, "somebody bought it before you");
			p.addMoney(price);
			return;
		}

		conn.sendOK();
		conn.send("show-transaction " + price + " Buy plot " + plot.getName());
		Client.broadcast("money-update " + p.getMoney() + " " + p.getName());
		Client.broadcast("plot-update " + plot);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
