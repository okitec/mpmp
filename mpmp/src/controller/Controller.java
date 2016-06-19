/**
 * Controller part of the MVC model (MVC is used in the client only).
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Model;
import model.Player;
import cmds.Cmd;
import cmds.ChatUpdate;
import cmds.ClientlistUpdate;
import main.Conn;
import view.Frame;

/**
 * Controller class of the MVC model; used only by the client.
 *
 * @author Leander, oki
 */
public class Controller {
	public Controller(String addr, int port, String mode, String color, String name) throws UnknownHostException, IOException {
		Model m = new Model();
		Frame frame = new Frame(m);
		Conn conn = new Conn(new Socket(addr, port));

		Player.init();

		new Thread(() -> {
			try {
				conn.handle();
			} catch (IOException ioe) {
				// XXX "do what"?
			}
		}).start();
		
		conn.send("subscribe " + mode + " " + color + " " + name);
		
		frame.addChatListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					// XXX add scrolling to the chat box
					String line = frame.getChat();
					System.out.println("Chatline = " + line);
					if(line.length() == 0)
						return;
					conn.send("chat " + line);
				}
			}
		});
		
		((ChatUpdate) Cmd.ChatUpdate.getFn()).addChatAdder(frame);
		((ClientlistUpdate) Cmd.ClientlistUpdate.getFn()).addClientLister(frame);

		frame.addEndTurnListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("end-turn");
			}
		});
	}
}
