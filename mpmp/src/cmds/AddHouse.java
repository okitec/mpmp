package cmds;

import java.util.Arrays;
import main.Client;
import main.Conn;
import main.ErrCode;
import model.Player;
import model.Plot;

/**
 * C->S
 * @author Leander
 */
public class AddHouse implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");

		Player p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "add-house <Grundstück>");
			return;
		}

		Plot plot = Plot.search(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
		if (plot == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (plot.getOwner() != p) {
			conn.sendErr(ErrCode.AlreadyOwned, plot.getOwner().getName());
			return;
		}

		switch (plot.addHouse()) {
		case -1:
			conn.sendErr(ErrCode.TooManyHouses);
			return;
		case -2:
			conn.sendErr(ErrCode.DontHave, "every plot of that plotgroup");
			return;
		case -3:
			conn.sendErr(ErrCode.UnbalancedColor);
			return;
		case -4:
			conn.sendErr(ErrCode.MissingMoney);
			return;
		case 1:
			conn.sendOK();
			//conn.send("add-money " + <preis für das Haus> + " Buy house for plot " + plot.getName());
			conn.send("plot-update " + plot.getName() + " " + plot.getHouses() + " " + plot.isHypothec() + plot.getOwner());
			break;
		default:
			conn.sendErr(ErrCode.Internal, "Unexpected error");
		}
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
