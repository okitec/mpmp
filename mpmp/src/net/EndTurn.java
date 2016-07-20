package net;

import java.util.ArrayList;

import srv.Client;
import model.Diceroll;
import model.Field;
import model.Player;

/**
 * end-turn C->S
 */
public class EndTurn implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		// XXX check if plot was bought, if not -> auction
		Client c = (Client) conn;
		Player p;
		ArrayList<Player> players;

		/*
		if(!GameState.running()) {
			conn.sendErr(ErrCode.GameNotRunning);
			return;
		}

		p = Player.getCurrentPlayer();

		// Only current player can end their turn.
		if(!c.getName().equals(p.getName()))
			return;

		Diceroll dr = new Diceroll();
		if(dr.getPaschs() >= 3) {
			p.prison(true);
			conn.send("prison enter");
			Client.broadcast("turn-update " + 0 + " " + dr.getPaschs() + " " + p.getName());
			Client.broadcast("pos-update " + Field.Prison + " " + p.getName());
		} else {
			p.move(dr.getSum());
			Client.broadcast("turn-update " + dr.getSum() + " " + dr.getPaschs() + " " + p.getName());
			Client.broadcast("pos-update " + p.getPos() + " " + p.getName());
		}

		players = Player.getRealPlayers();
		Player.setCurrentPlayer(players.get((players.indexOf(p)+1) % players.size()));
		*/
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
