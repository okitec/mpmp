package cmds;

import main.Conn;
import main.ErrCode;
import model.Player;
import view.Displayer;

/**
 * chat-update S->C packet.
 */
public class ChatUpdate implements CmdFunc {
	private Displayer d;

	@Override
	public void exec(String line, Conn conn) {
		String message = line.substring(12);
		String name = message.substring(message.indexOf('(') + 1, message.indexOf(')'));
		Player sender = Player.search(name);

		d.show(message, sender.getColor());
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
	public void addDisplayer(Displayer d) {
		this.d = d;
	}
}
