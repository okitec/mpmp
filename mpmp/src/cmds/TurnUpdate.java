package cmds;

import java.util.ArrayList;
import java.util.Arrays;
import main.Conn;
import main.ErrCode;
import model.Player;
import view.Displayer;

/**
 * S->C
 * @author Leander
 */
public class TurnUpdate implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int sum, pasch;
		Player p;
		ArrayList<Player> players  = Player.getPlayers();
		int i;
		
		if (args.length < 4) {
			conn.sendErr(ErrCode.Usage, "turn-update <Gesamtwürfelsumme> <Anzahl an Paschs> <Spieler am Zug>");
			return;
		}
		
		try {
			sum = Integer.parseInt(args[1]);
			pasch = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe) {
			conn.sendErr(ErrCode.Usage, "turn-update <Gesamtwürfelsumme> <Anzahl an Paschs> <Spieler am Zug>");
			return;
		}
		
		p = Player.search(String.join(" ", Arrays.copyOfRange(args, 3, args.length)));
		if (p == null) {
			conn.sendErr(ErrCode.NoSuchPlayer);
			return;
		}
		
		d.show("Gesamtwürfelsumme: " + sum + "; Anzahl Paschs: " + pasch);
		Player.setCurrentPlayer(p);
		
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
