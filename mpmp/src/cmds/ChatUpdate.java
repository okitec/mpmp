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
		String name = null;
		if (message.startsWith("("))
			name = message.substring(1, message.indexOf(')'));
		else if (message.startsWith("[->"))
			name = message.substring(3, message.indexOf(']'));
		else if (message.startsWith("["))
			name = message.substring(1, message.indexOf(']'));
		
		System.out.println("name: " + name);
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
