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
import model.PlotGroup;
import cmds.ShowTransaction;
import cmds.Cmd;
import cmds.ChatUpdate;
import cmds.ClientlistUpdate;
import cmds.PosUpdate;
import cmds.Subscribe;
import cmds.TurnUpdate;
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

		Player.reset();
		PlotGroup.init();

		((ChatUpdate) Cmd.ChatUpdate.getFn()).addDisplayer(frame.chatDisp);
		((ClientlistUpdate) Cmd.ClientlistUpdate.getFn()).addDisplayer(frame.playerDisp);
		((ShowTransaction) Cmd.AddMoney.getFn()).addDisplayer(frame.chatDisp);
		((TurnUpdate) Cmd.TurnUpdate.getFn()).addDisplayer(frame.chatDisp);
		((PosUpdate) Cmd.PosUpdate.getFn()).addDisplayer(frame.pieceDisp);
		((Subscribe) Cmd.Subscribe.getFn()).addSubscribeErrer(frame);

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
					if (line.length() == 0) {
						return;
					}
					conn.send("chat " + line);
				}
			}
		});

		frame.addEndTurnListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("end-turn");
			}
		});

		frame.addTradeListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//conn.send("trade");
			}
		});

		frame.addBuyHouseListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//conn.send("add-house " + Player.getPos();
			}
		});

		frame.addBuyPlotListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//conn.send("buy-plot " + Player.getPos());
			}
		});

		frame.addSurrenderListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("ragequit");
			}
		});

		frame.addStartGameListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("start-game");
				frame.updateMyPlayerText(Player.search(name));
				frame.removeStartGameButton();
			}
		});

		frame.addUpdatePlayerListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.updateMyPlayerText(Player.search(name));
			}
		});
	}
}
