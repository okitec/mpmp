package cmds;

import java.util.ArrayList;
import java.util.Random;
import main.Client;
import main.Conn;
import main.ErrCode;
import main.GameState;
import model.Diceroll;
import model.Field;
import model.Player;

/**
 * C->S
 * @author Leander
 */
public class StartGame implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		GameState.startGame();
		
		ArrayList<Player> players = Player.getRealPlayers();
		Random r = new Random();
		Player p = players.get(r.nextInt(players.size()));
		Player next = players.get((players.indexOf(p)+1) % players.size());
		
		Diceroll dr = new Diceroll();
		if(dr.getPaschs() >= 3) {
			next.prison(true);
			Client.broadcast("turn-update " + 0 + " " + dr.getPaschs() + " " + next.getName());
			Client.broadcast("pos-update " + Field.Prison + " " + next.getName());
		} else {
			next.move(dr.getSum());
			Client.broadcast("turn-update " + dr.getSum() + " " + dr.getPaschs() + " " + next.getName());
			Client.broadcast("pos-update " + next.getPos() + " " + next.getName());
		}

		Player.setCurrentPlayer(next);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
}
