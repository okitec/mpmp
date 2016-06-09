package cmds;

import main.Conn;

/**
 * chat-update S->C packet.
 */
public class ChatUpdate implements CmdFunc {
	public interface ChatAdder {
		public void addChat(String chat);
	}
	private ChatAdder cha;

	@Override
	public void exec(String line, Conn conn) {
		String message = line.substring(12);
		cha.addChat(message);
		conn.sendOK();
	}

	public void addChatAdder(ChatAdder cha) {
		this.cha = cha;
	}
}
