package cmds;

import java.util.Arrays;
import main.Conn;
import main.ErrCode;
import model.Player;
import model.Plot;
import view.Displayer;

/**
 * S->C
 * @author Leander
 */
public class AuctionPlot implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		int price = -1, delim = -1;
		String[] args = line.split(" ");
		String pname, bname;
		Plot plot;
		Player bidder;
		
		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "auction-plot <Name des Grundstücks> <Preis> <Höchstbietender>");
			return;
		}
		
		for (int i = 0; i < args.length; i++) 
			if (args[i].matches("[0-9]+")) 
				try {
					price = Integer.parseInt(args[i]);
					delim = i;
					break;
				} catch (NumberFormatException nfe) {
					conn.sendErr(ErrCode.Internal, "'" + args[i] + "' is not a number");
					return;
				}

		if (price == -1) {
			conn.sendErr(ErrCode.Usage, "auction-plot <Name des Grundstücks> <Preis> <Höchstbietender>");
			return;
		}

		pname = Plot.matches(String.join(" ", Arrays.copyOfRange(args, 1, delim--)));
		bname = Player.matches(String.join(" ", Arrays.copyOfRange(args, delim++, args.length)));

		if (pname == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}
		
		if (bname == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}

		plot = Plot.search(pname);
		bidder = Player.search(bname);
		
		
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
