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
		// XXX check if plot was bought, if not -> auction
		Client c = (Client) conn;

		Player last = Player.getCurrentPlayer();
		ArrayList<Player> players = Player.getRealPlayers();
		Player next = players.get((players.indexOf(last)+1) % players.size());

		// Only current player can end their turn.
		if(!c.getName().equals(last.getName()))
			return;

		Diceroll dr = new Diceroll();
		if(dr.getPaschs() >= 3) {
			next.prison(true);
			conn.send("prison enter");
			Client.broadcast("turn-update " + 0 + " " + dr.getPaschs() + " " + next.getName());
			Client.broadcast("pos-update " + Field.Prison + " " + next.getName());
		} else {
			next.move(dr.getSum());
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
