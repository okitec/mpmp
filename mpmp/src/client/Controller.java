/**
 * Controller part of the MVC model (MVC is used in the client only).
 */
package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import model.Model;
import model.Player;
import model.Plot;
import model.PlotGroup;
import net.ShowTransaction;
import net.Cmd;
import net.Conn;
import net.ChatUpdate;
import net.MoneyUpdate;
import net.PlayerlistUpdate;
import net.PlotUpdate;
import net.PosUpdate;
import net.Prison;
import net.StartUpdate;
import net.Subscribe;
import net.TurnUpdate;

import net.MoneyUpdate.MoneyUpdater;
import net.PosUpdate.PosUpdater;
import net.Prison.PrisonUpdater;
import net.TurnUpdate.TurnUpdater;
import net.StartUpdate.StartUpdater;
import net.PlotUpdate.PlotUpdater;

import view.Frame;

/**
 * Controller class of the MVC model; used only by the client.
 */
public class Controller implements MoneyUpdater, PosUpdater, TurnUpdater, PrisonUpdater, StartUpdater, PlotUpdater {

	Frame frame;
	Timer t;
	String name; /* player name */
	Model m;

	public Controller(String addr, int port, String mode, String color, String name) throws UnknownHostException, IOException {
		Conn conn = new Conn(new Socket(addr, port));
		this.name = name;

		Player.reset();
		Model.init();
		m = new Model();
		frame = new Frame(m);

		/* Please insert alphabetically. -oki */
		((ChatUpdate) Cmd.ChatUpdate.getFn()).addDisplayer(frame.chatDisp);
		((MoneyUpdate) Cmd.MoneyUpdate.getFn()).addMoneyUpdater(this);
		((PlayerlistUpdate) Cmd.PlayerlistUpdate.getFn()).addDisplayer(frame.playerDisp);
		((PlotUpdate) Cmd.PlotUpdate.getFn()).addPlotUpdater(this);
		((PosUpdate) Cmd.PosUpdate.getFn()).addDisplayer(frame.pieceDisp);
		((PosUpdate) Cmd.PosUpdate.getFn()).addPosUpdater(this);
		((Prison) Cmd.Prison.getFn()).addDisplayer(frame.chatDisp);
		((Prison) Cmd.Prison.getFn()).addPrisonUpdater(this);	
		((ShowTransaction) Cmd.ShowTransaction.getFn()).addDisplayer(frame.chatDisp);
		((StartUpdate) Cmd.StartUpdate.getFn()).addDisplayer(frame.startDisp);
		((StartUpdate) Cmd.StartUpdate.getFn()).addStartUpdater(this);
		((Subscribe) Cmd.Subscribe.getFn()).addSubscribeErrer(frame);
		((TurnUpdate) Cmd.TurnUpdate.getFn()).addDisplayer(frame.chatDisp);
		((TurnUpdate) Cmd.TurnUpdate.getFn()).addTurnUpdater(this);

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
					String line = frame.getChat();
					if (line.length() == 0)
						return;

					conn.send("chat " + line);
				}
			}
		});

		frame.addSurrenderListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("ragequit");
			}
		});

		frame.addEndTurnListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m.running()) {
					conn.send("end-turn");
				} else {
					conn.send("start-game");
					frame.updateMyPlayerText(Player.search(name));
				}
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
				System.out.println(frame.showDialog("Welcher Spieler"));
				System.out.println(frame.showDialog("Welches Grundstück"));
			}
		});

		frame.addBuyHouseListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//conn.send("add-house " + Player.getPos());
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
				conn.send("unjail money");
			}
		});

		frame.addUsePrisonLeaveListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conn.send("unjail card");
			}
		});

		frame.addClearChatListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.chatDisp.reset();
			}
		});
	}

	/* S->C UPDATES */

	public void moneyUpdate(int amount, String name) {
		Player p = m.getPlayer(name);
		if(p == null)
			return;

		p.setMoney(amount);
	}

	public void plotUpdate(int pos, int houses, boolean hyp, String ownername) {
		Plot plot; 
		Player owner;

		plot = m.getPlot(pos);
		if(plot == null)
			return;

		owner = m.getPlayer(ownername);
		if(owner == null)
			return;

		plot.setHouses(houses);
		plot.hypothec(hyp);     // XXX inconsistent
		plot.setOwner(owner);

		frame.chatDisp.show("<game> plot update: " + plot);
	}

	public void posUpdate(int pos, String name) {
		Player p = m.getPlayer(name);
		if(p == null)
			return;

		p.setPos(pos);
	}

	public void turnUpdate(int roll, int paschs, String cpname) {
		m.setCurrentPlayer(m.getPlayer(cpname));
	}

	public void startUpdate() {
		m.startGame();
	}

	public void prisonUpdate(boolean enter, String name) {
		Player p = m.getPlayer(name);
		if(p == null)
			return; // XXX return error to allow for no-such-player error

		p.setPrison(enter);
	}

	/* KLAUS'S THINGSIES */

	public class TimerTick extends TimerTask {

		@Override
		public void run() {
			frame.updateMyPlayerText(Player.search(name));
		}
	}

}
