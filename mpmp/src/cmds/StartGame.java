package cmds;

import java.util.ArrayList;
import java.util.Random;
import main.Conn;
import main.ErrCode;
import main.GameState;
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
		
		conn.sendOK();
		conn.send("turn-update 0 0 " + p.getName());
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
}
