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
import cmds.Prison;
import cmds.Subscribe;
import cmds.TurnUpdate;
import java.util.Timer;
import java.util.TimerTask;
import main.Conn;
import view.Frame;

/**
 * Controller class of the MVC model; used only by the client.
 *
 * @author Leander, oki
 */
public class Controller {

	Frame frame;
	Timer t;
	String name;

	public Controller(String addr, int port, String mode, String color, String name) throws UnknownHostException, IOException {
		Model m = new Model();
		frame = new Frame(m);
		Conn conn = new Conn(new Socket(addr, port));
		this.name = name;

		Player.reset();
		PlotGroup.init();

		((ChatUpdate) Cmd.ChatUpdate.getFn()).addDisplayer(frame.chatDisp);
		((ClientlistUpdate) Cmd.ClientlistUpdate.getFn()).addDisplayer(frame.playerDisp);
		((ShowTransaction) Cmd.AddMoney.getFn()).addDisplayer(frame.chatDisp);
		((TurnUpdate) Cmd.TurnUpdate.getFn()).addDisplayer(frame.chatDisp);
		((PosUpdate) Cmd.PosUpdate.getFn()).addDisplayer(frame.pieceDisp);
		((Subscribe) Cmd.Subscribe.getFn()).addSubscribeErrer(frame);
		((Prison) Cmd.Prison.getFn()).addDisplayer(frame.chatDisp);

		new Thread(() -> {
			try {
				conn.handle();
			} catch (IOException ioe) {
				// XXX "do what"?
			}
		}).start();

		conn.send("subscribe " + mode + " " + color + " " + name);

		//http://stackoverflow.com/questions/5844794/java-timertick-event-for-game-loop
		t = new Timer();
		t.schedule(new TimerTick(), 1000);

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

		frame.addPayPrisonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//conn.send("pay-prison");
			}
		});

		frame.addUsePrisonLeaveListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//conn.send("use-prisoncard");
			}
		});

		frame.addClearChatListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.chatDisp.reset();
			}
		});
	}

	public class TimerTick extends TimerTask {

		@Override
		public void run() {
			frame.updateMyPlayerText(Player.search(name));
		}
	}

}
