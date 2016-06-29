package cmds;

import java.util.Arrays;
import main.Client;
import main.Conn;
import main.ErrCode;
import model.Player;
import model.Plot;
import view.Displayer;

/**
 * C->S
 * @author Leander
 */
public class SellPlot implements CmdFunc{
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int price = -1;

		Player p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "sell-plot <Name des Grundstückes> @<Käufer> <Preis>");
			return;
		}

		String plotname = Plot.matches(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
		if (plotname == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		Plot plot = Plot.search(plotname);

		if (p != plot.getOwner()) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}

		if (plot.getHouses() != 0) {
			conn.sendErr(ErrCode.PlotWithHouse);
			return;
		}

		String[] s1 = line.split(plotname + " ");
		String buyername = Player.matches(s1[1], true);
		if (buyername == null) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		Player buyer = Player.search(buyername);

		String[] s2 = line.split("@" + buyername + " ");
		try {
			price = Integer.parseInt(s2[1]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Internal, "'" + s2[1] + "' is not a number");
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
		conn.send("money-update " + plot.getHousePrice(plot.getHouses()) + " Resell plot " + plot.getName());
		conn.send("plot-update " + plot.getName() + " " + plot.getHouses() + " " + plot.isHypothec() + plot.getOwner());
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
