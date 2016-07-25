package net;

import java.util.ArrayList;
import java.util.Arrays;

import model.Player;
import view.Displayer;

/**
 * S->C
 */
public class TurnUpdate implements CmdFunc {
	private Displayer d;
	private TurnUpdater tu;

	@Override
	public void exec(String line, Conn conn) {
		String[] args = line.split(" ");
		int sum, pasch;
		String cpname;
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
		
		cpname = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
		tu.turnUpdate(sum, pasch, cpname);
		d.show("Gesamtwürfelsumme: " + sum + "; Anzahl Paschs: " + pasch);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}

	public interface TurnUpdater {
		public void turnUpdate(int roll, int paschs, String cpname);
	}

	public void addTurnUpdater(TurnUpdater tu) {
		this.tu = tu;
	}
}
