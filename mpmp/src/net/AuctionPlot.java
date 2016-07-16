package net;

import java.util.Arrays;

import model.Player;
import model.Plot;

/**
 * auction-plot S->C
 */
public class AuctionPlot implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		int price, pos;
		String[] args = line.split(" ");
		String bname;
		Plot plot;
		Player bidder;

		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "auction-plot <Position> <Preis> <Höchstbietender>");
			return;
		}

		try {
			pos = Integer.parseInt(args[1]);		
			price = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Internal, "not a number");
			return;
		}
		
		bname = Player.matches(String.join(" ", Arrays.copyOfRange(args, 3, args.length)), false);
		
		if (bname == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}

		plot = null;
		// XXX missing pos-to-plot map
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		bidder = Player.search(bname);
		
		// XXX continue
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
}
