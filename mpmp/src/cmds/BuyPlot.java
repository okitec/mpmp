/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmds;

import java.util.Arrays;
import main.Client;
import main.Conn;
import main.ErrCode;
import model.Player;
import model.Plot;

/**
 *
 * @author leander.dreier
 */
public class BuyPlot implements CmdFunc {
//https://github.com/leletec/mpmp/blob/master/proto.md#grundstücke
	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		Plot plot = Plot.search(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
		Player p = Player.search(((Client) conn).getName());
		
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}
		
		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "buy-plot <Name des Grundstücks>");
			return;
		}
		
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		
		if (plot.getOwner() != null) {
			conn.sendErr(ErrCode.PlotOwned, plot.getOwner().getName());
			return;
		}
		
		int price = plot.getPrice();
		if (!p.addMoney(price)) {
			conn.sendErr(ErrCode.InsufficientMoney, "RM" + price);
			return;
		}
		
		if (!plot.buy(p)) {
			conn.sendErr(ErrCode.Unexpected, "somebody bought it before you");
			p.addMoney(price);
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
