package cmds;

import java.util.Arrays;

import main.Client;
import main.Conn;
import main.ErrCode;
import model.Player;
import model.Plot;
import model.HousePlot;

/**
 * C->S
 * @author Leander
 */
public class AddHouse implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		Player p;
		HousePlot hp;
		String[] args = line.split(" ");

		p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		if (args.length < 2) {
			conn.sendErr(ErrCode.Usage, "add-house <Grundstück>");
			return;
		}

		hp = (HousePlot) Plot.search(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
		if (hp == null) {
			conn.sendErr(ErrCode.NotAPlot);
			return;
		}

		if (hp.getOwner() != p) {
			conn.sendErr(ErrCode.AlreadyOwned, hp.getOwner().getName());
			return;
		}

		switch (hp.addHouse()) {
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
			conn.send("show-transaction " + hp.getHousePrice(hp.getHouses()) + " Buy house for plot " + hp.getName());
			Client.broadcast("plot-update " + hp);
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
