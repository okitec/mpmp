/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmds;

import java.util.ArrayList;
import main.Client;
import main.Conn;
import main.ErrCode;
import model.Diceroll;
import model.Field;
import model.Player;

/**
 * C->S
 * @author Leander
 */
public class EndTurn implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		//check if plot was bought, if not -> auction
		Player last = Player.getCurrentPlayer();
		ArrayList<Player> players = Player.getPlayers();
		Player next = players.get((players.indexOf(last)+1) % players.size());
		System.out.println("next Player: " + next);
		
		Diceroll dr = new Diceroll();
		if(dr.getPaschs() >= 3) {
			next.prison(true);
			Client.broadcast("turn-update " + 0 + " " + dr.getPaschs() + " " + next.getName());
			Client.broadcast("pos-update " + Field.Prison + " " + next.getName());
		} else {
			next.teleport(dr.getSum(), true);
			Client.broadcast("turn-update " + dr.getSum() + " " + dr.getPaschs() + " " + next.getName());
			Client.broadcast("pos-update " + next.getPos() + " " + next.getName());
		}

		Player.setCurrentPlayer(next);
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {
		//TODO
	}
	
}
