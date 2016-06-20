package cmds;

import main.Conn;
import main.ErrCode;

/**
 * chat-update S->C packet.
 */
public class ChatUpdate implements CmdFunc {
	@Override
	public void exec(String line, Conn conn) {
		String message = line.substring(12);
		cha.addChat(message);
		conn.sendOK();
	}

	@Override
	public void error(ErrCode err, String line, Conn conn) {}
	
	public interface ChatAdder {
		public void addChat(String chat);
	}
	private ChatAdder cha;
	
	public void addChatAdder(ChatAdder cha) {
		this.cha = cha;
	}
}
