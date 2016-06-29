package cmds;

import main.Conn;
import main.ErrCode;
import main.GameState;

/**
 * C->S
 * @author Leander
 */
public class StartGame implements CmdFunc {

	@Override
	public void exec(String line, Conn conn) {
		GameState.startGame();
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
}
