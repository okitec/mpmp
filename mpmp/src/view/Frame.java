package view;

import cmds.Subscribe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import model.Model;
import model.Player;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.UpdateManagerEvent;
import org.apache.batik.bridge.UpdateManagerListener;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.util.XMLResourceDescriptor;

public class Frame extends JFrame implements Subscribe.SubscribeErrer {

	public final ChatDisp chatDisp;
	public final PlayerDisp playerDisp;
	public final PieceDisp pieceDisp;
	private Model m;
	private JPanel pLeft;
	private JPanel pChat;
	private JPanel pBottom;
	private JPanel pCurrentPlayer;
	private JPanel pBottomMenu;
	private JButton bUpdatePlayer;
	private JButton bTrade;
	private JButton bBuyHouse;
	private JButton bBuyPlot;
	private JButton bSurrender;
	private JButton bStartGame;
	private JButton bPayPrison;
	private JButton bUsePrisonLeave;
	private JButton bClearChat;
	private JTextField tChatField;
	private JTextPane tChatBox;
	private JTextPane tPlayerList;
	private JLabel lmP;
	private JLabel lmPMoney;
	private JLabel lmPPlots;
	private JButton bEndTurn;
	private ScrollPane sP;
	private Gameboard gameboard;

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Frame(null);
			}
		});
	}	
	public Frame(Model m) {
		this.m = m;
		chatDisp = new ChatDisp();
		playerDisp = new PlayerDisp();
		pieceDisp = new PieceDisp();
		setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension(1920, 1080));
		createFrame();
		setBackground(new Color(247, 247, 124));
	}

	/**
	 * Create the frame.
	 */
	public void createFrame() {
		setTitle("MPMP");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameboard = new Gameboard(this);

		//Set background
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("graphics/background.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Set all layouts
		setLayout(new BorderLayout());
		pLeft = new JPanel();
		pChat = new JPanel();
		pBottom = new JPanel();
		pBottomMenu = new JPanel();
		pCurrentPlayer = new JPanel();

		pLeft.setLayout(new BoxLayout(pLeft, BoxLayout.Y_AXIS));
		pChat.setLayout(new BoxLayout(pChat, BoxLayout.Y_AXIS));
		pBottom.setLayout(new BoxLayout(pBottom, BoxLayout.X_AXIS));
		pBottomMenu.setLayout(new BoxLayout(pBottomMenu, BoxLayout.Y_AXIS));
		pCurrentPlayer.setLayout(new BoxLayout(pCurrentPlayer, BoxLayout.Y_AXIS));

		bTrade = new JButton("Tauschen");
		bBuyHouse = new JButton("Haus kaufen");
		bBuyPlot = new JButton("Straße kaufen");
		bSurrender = new JButton("Aufgeben");
		bEndTurn = new JButton("Runde beenden");
		bStartGame = new JButton("Spiel starten");
		bClearChat = new JButton("Chat leeren");
		bPayPrison = new JButton("Aus dem Gefängnis freikaufen");
		bUsePrisonLeave = new JButton("Benutze Gefängnis-Frei-Karte");
		bUpdatePlayer = new JButton("Update Spieler");
		bUpdatePlayer.setVisible(false);

		lmP = new JLabel("Kein Spieler");
		lmPMoney = new JLabel("RM 0");
		lmPPlots = new JLabel("Keine Grundstücke");

		tPlayerList = new JTextPane();
		tPlayerList.setEditable(false);
		tPlayerList.setSize(new Dimension(200, 500));

		tChatBox = new JTextPane();
		tChatBox.setEditable(false);
		tChatBox.setSize(300, 500);
		tChatBox.setMaximumSize(new Dimension(300, 500));

		sP = new ScrollPane();
		sP.setMaximumSize(new Dimension(300, 500));
		sP.setSize(300, 500);
		sP.add(tChatBox);

		tChatField = new JTextField();
		tChatField.requestFocus(true);
		tChatField.setSelectionColor(Color.pink);

		pLeft.add(new JLabel("Alle Spieler:"));
		pLeft.add(tPlayerList);
		pLeft.add(bPayPrison);
		pLeft.add(bUsePrisonLeave);

		pChat.add(bUpdatePlayer);
		pChat.add(bStartGame);
		pChat.add(sP);
		pChat.add(tChatField);
		pChat.add(bEndTurn);
		pChat.add(bClearChat);

		pCurrentPlayer.add(new JLabel("Spieler"));
		pCurrentPlayer.add(lmP);
		pCurrentPlayer.add(lmPMoney);
		pCurrentPlayer.add(lmPPlots);

		pBottomMenu.add(bTrade);
		pBottomMenu.add(bBuyHouse);
		pBottomMenu.add(bBuyPlot);
		pBottomMenu.add(bSurrender);
		pBottomMenu.add(bPayPrison);
		pBottomMenu.add(bUsePrisonLeave);

		pBottom.add(pBottomMenu);
		pBottom.add(pCurrentPlayer);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				gameboard.repaint();
			}
		});

		add(gameboard, BorderLayout.CENTER);
		add(pLeft, BorderLayout.WEST);
		add(pChat, BorderLayout.EAST);
		add(pBottom, BorderLayout.SOUTH);

		setVisible(true);
		/*
		 System.out.println(gameboard.showPrompt("Welcher Spieler"));
		 System.out.println(gameboard.showPrompt("Welches Grundstück"));
		 */
		pack();
	}

	public void addChatListener(KeyAdapter k) {
		tChatField.addKeyListener(k);
	}

	public String getChat() {
		String chat = tChatField.getText();
		tChatField.setText("");
		return chat;
	}

	public void addEndTurnListener(ActionListener al) {
		System.out.println("Runde beendet.");
		bEndTurn.addActionListener(al);
	}

	public void addTradeListener(ActionListener al) {
		System.out.println("Tausch.");
		bTrade.addActionListener(al);
	}

	public void addBuyHouseListener(ActionListener al) {
		System.out.println("Haus gekauft.");
		bBuyHouse.addActionListener(al);
	}

	public void addBuyPlotListener(ActionListener al) {
		System.out.println("Grundstück gekauft.");
		bBuyPlot.addActionListener(al);
	}

	public void addSurrenderListener(ActionListener al) {
		System.out.println("Aufgegeben.");
		bSurrender.addActionListener(al);
	}

	public void addUpdatePlayerListener(ActionListener al) {
		System.out.println("Player updated.");
		bUpdatePlayer.addActionListener(al);
	}

	public void addPayPrisonListener(ActionListener al) {
		System.out.println("Aus dem Gefängnis freigekauft");
		bPayPrison.addActionListener(al);
	}

	public void addUsePrisonLeaveListener(ActionListener al) {
		System.out.println("Gefängnis-Frei-Karte benutzt.");
		bUsePrisonLeave.addActionListener(al);
	}

	public void addStartGameListener(ActionListener al) {
		System.out.println("Spiel gestartet.");
		bStartGame.addActionListener(al);
	}

	public void addClearChatListener(ActionListener al) {
		System.out.println("Chat geleert.");
		bClearChat.addActionListener(al);
	}

	public void removeStartGameButton() {
		bStartGame.setVisible(false);
		bUpdatePlayer.setVisible(true);
	}

	@Override
	public void subscribeErr() {
		JOptionPane.showMessageDialog(this, "Name taken or running game!");
		System.exit(0);
	}

	public void updateMyPlayerText(Player p) {
		if (p != null) {
			if (p.inPrison) {
				lmP.setText(p.getName() + "(Im Gefängnis)");
			} else {
				lmP.setText(p.getName());
			}

			lmP.setForeground(p.getColor());
			bUsePrisonLeave.setVisible(p.isInJail());
			bPayPrison.setVisible(p.isInJail());
			lmPMoney.setText("RM " + p.getMoney());
			lmPPlots.setText("Gekaufte Grundstücke: " + p.getPlots());
		}
	}

	public class ChatDisp implements Displayer {

		@Override
		public synchronized void show(String s) {
			// Call in the Event Dispatching Thread.
			SwingUtilities.invokeLater(() -> {
				try {
					Document doc = tChatBox.getDocument();
					doc.insertString(doc.getLength(), s + "\n", null);
				} catch (BadLocationException ble) {
					;// XXX how to handle?
				}
			});
		}

		@Override
		public void reset() {
			tChatBox.setText("");
		}
	}

	public class PlayerDisp implements Displayer {

		@Override
		public synchronized void show(String s) {
			SwingUtilities.invokeLater(() -> {
				try {
					Document doc = tPlayerList.getDocument();
					doc.insertString(doc.getLength(), s + "\n", null);
				} catch (BadLocationException ble) {
					;// XXX how to handle?
				}
			});
		}

		@Override
		public synchronized void reset() {
			SwingUtilities.invokeLater(() -> {
				tPlayerList.setDocument(new DefaultStyledDocument());
			});
		}
	}

	public class PieceDisp implements Displayer {

		@Override
		public void show(String s) {
		}

		@Override
		public void reset() {
			SwingUtilities.invokeLater(() -> {
				gameboard.repaint();
			});
		}
	}
}
