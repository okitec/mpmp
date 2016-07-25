package net;

import java.awt.Color;

import model.Player;
import view.Displayer;

/**
 * playerlist-update S->C packet.
 */
public class PlayerlistUpdate implements CmdFunc {
	private PlayerlistUpdater plu;

	@Override
	public void exec(String line, Conn conn) {
		int nclients;
		String args[];

		args = line.split(" ");
		if(args.length < 2) {
			conn.sendErr(ErrCode.Usage, "playerlist-update <number of clients>");
			return;
		}

		try {
			nclients = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			conn.sendErr(ErrCode.Internal, "'" + args[1] + "' is not a number");
			return;
		}

		Player.reset();         /* reset subscriber list */
		plu.playerlistReset();  /* reset subsriber list and model's player list */
		while(nclients --> 0) {
			String s;
			String fields[];
			Player.Mode mode;

			s = conn.readLine();
			if(s == null) {
				conn.sendErr(ErrCode.EOF);
				return;
			}

			fields = s.split(": ");
			if(fields.length < 3) {
				conn.sendErr(ErrCode.PlayerlistUpdateMissingFields);
				return;
			}

			plu.playerlistAdd(fields[0], fields[1], fields[2]);
		}

		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		System.err.println("Can't happen: " + err.getMessage());
	}

	public interface PlayerlistUpdater {
		public void playerlistAdd(String col, String mode, String name);
		public void playerlistReset();
	}

	public void addPlayerlistUpdater(PlayerlistUpdater plu) {
		this.plu = plu;
	}
}
