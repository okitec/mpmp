/**
 * Controller part of the MVC model
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import clientmodel.Model;
import cmds.Cmd;
import cmds.ChatUpdate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import main.Conn;
import view.Frame;

/**
 * Controller class of the MVC model; currently not used correctly.
 * The main method is also located here.
 *
 * @author Leander, oki
 */
public class Controller {
	public Controller(String addr, int port, String mode, String color, String name) throws UnknownHostException, IOException {
		Model m = new Model();
		Frame frame = new Frame(m);
		Conn conn = new Conn(new Socket(addr, port));
		
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

		frame.addEndTurnListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("endturn");
			}
		});
	}
}
