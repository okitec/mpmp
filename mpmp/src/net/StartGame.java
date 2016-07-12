package net;

import java.util.ArrayList;
import java.util.Random;

import main.Client;
import main.GameState;
import model.Diceroll;
import model.Field;
import model.Player;

/**
 * C->S
 */
public class StartGame implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		GameState.startGame();
		
		ArrayList<Player> players = Player.getRealPlayers();
		Random r = new Random();
		
		Player p = Player.search(((Client) conn).getName());
		if (!p.isPlayer()) {
			conn.sendErr(ErrCode.NotAPlayer);
			return;
		}

		Diceroll dr = new Diceroll();
		if(dr.getPaschs() >= 3) {
			p.prison(true);
			Client.broadcast("turn-update " + 0 + " " + dr.getPaschs() + " " + p.getName());
			Client.broadcast("pos-update " + Field.Prison + " " + p.getName());
		} else {
			p.move(dr.getSum());
			Client.broadcast("turn-update " + dr.getSum() + " " + dr.getPaschs() + " " + p.getName());
			Client.broadcast("pos-update " + p.getPos() + " " + p.getName());
		}

		Player.setCurrentPlayer(players.get((players.indexOf(p)+1) % players.size()));
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
}
