package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import net.Subscribe;
import model.Model;
import model.Player;

public class Frame extends JFrame implements Subscribe.SubscribeErrer {

	public final ChatDisp chatDisp;
	public final PlayerDisp playerDisp;
	public final PieceDisp pieceDisp;
	public final StartDisp startDisp;
	private Model m;
	private JPanel pLeft;
	private JPanel pChat;
	private JPanel pBottom;
	private JPanel pCurrentPlayer;
	private JPanel pBelowChat;
	private JButton bUpdatePlayer;
	private JButton bTrade;
	private JButton bBuyHouse;
	private JButton bBuyPlot;
	private JButton bSurrender;
	private JButton bPayPrison;
	private JButton bUsePrisonLeave;
	private JButton bClearChat;
	private JTextField tChatField;
	private JTextPane tChatBox;
	private JTextPane tPlayerList;
	private JLabel lmyPlayer;
	private JLabel lmyPlayerMoney;
	private JLabel lmyPlayerPlots;
	private JButton bEndTurn;
	private ScrollPane spChatBox;
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
		startDisp = new StartDisp();
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
		pCurrentPlayer = new JPanel();
		pBelowChat = new JPanel();

		pLeft.setLayout(new BoxLayout(pLeft, BoxLayout.Y_AXIS));
		pChat.setLayout(new BoxLayout(pChat, BoxLayout.Y_AXIS));
		pBottom.setLayout(new BoxLayout(pBottom, BoxLayout.X_AXIS));
		pCurrentPlayer.setLayout(new BoxLayout(pCurrentPlayer, BoxLayout.Y_AXIS));
		pBelowChat.setLayout(new BoxLayout(pBelowChat, BoxLayout.X_AXIS));

		bTrade = new JButton("Tauschen");
		bBuyHouse = new JButton("Haus kaufen");
		bBuyPlot = new JButton("Straße kaufen");
		bSurrender = new JButton("Aufgeben");
		bEndTurn = new JButton("Spiel starten");
		bClearChat = new JButton("Chat leeren");
		bPayPrison = new JButton("Aus dem Gefängnis freikaufen");
		bUsePrisonLeave = new JButton("Benutze Gefängnis-Frei-Karte");
		bUpdatePlayer = new JButton("Update Spieler");

		/* Left-side buttons should neatly fill the space. */
		bTrade.setAlignmentX(Component.CENTER_ALIGNMENT);
		bBuyHouse.setAlignmentX(Component.CENTER_ALIGNMENT);
		bBuyPlot.setAlignmentX(Component.CENTER_ALIGNMENT);
		bSurrender.setAlignmentX(Component.CENTER_ALIGNMENT);
		bPayPrison.setAlignmentX(Component.CENTER_ALIGNMENT);
		bUsePrisonLeave.setAlignmentX(Component.CENTER_ALIGNMENT);
		bTrade.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bBuyHouse.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bBuyPlot.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bSurrender.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bPayPrison.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bUsePrisonLeave.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		bUpdatePlayer.setVisible(false);
		bPayPrison.setVisible(false);
		bUsePrisonLeave.setVisible(false);

		lmyPlayer = new JLabel("Kein Spieler");
		lmyPlayerMoney = new JLabel("RM 0");
		lmyPlayerPlots = new JLabel("Keine Grundstücke");

		tPlayerList = new JTextPane();
		tPlayerList.setEditable(false);

		tChatBox = new JTextPane();
		tChatBox.setEditable(false);
		spChatBox = new ScrollPane();
		spChatBox.add(tChatBox);

		tChatField = new JTextField();
		tChatField.requestFocus(true);
		tChatField.setSelectionColor(Color.pink);
		tChatField.setMinimumSize(new Dimension(300, 40));
		tChatField.setMaximumSize(new Dimension(300, 60));

		pLeft.add(new JLabel("Alle Spieler:"));
		pLeft.add(tPlayerList);
		pLeft.add(bPayPrison);
		pLeft.add(bUsePrisonLeave);

		pBelowChat.add(bClearChat);
		pBelowChat.add(bEndTurn);

		pChat.add(spChatBox);
		pChat.add(tChatField);
		pChat.add(pBelowChat);
		pChat.add(bUpdatePlayer);

		pCurrentPlayer.add(new JLabel("Spieler"));
		pCurrentPlayer.add(lmyPlayer);
		pCurrentPlayer.add(lmyPlayerMoney);
		pCurrentPlayer.add(lmyPlayerPlots);

		pLeft.add(bTrade);
		pLeft.add(bBuyHouse);
		pLeft.add(bBuyPlot);
		pLeft.add(bSurrender);
		pLeft.add(bPayPrison);
		pChat.add(bUsePrisonLeave);
		pBottom.add(pCurrentPlayer);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				gameboard.repaint();
			}
		});

		add(gameboard, BorderLayout.CENTER);
		add(pBottom, BorderLayout.SOUTH);
		add(pLeft, BorderLayout.WEST);
		add(pChat, BorderLayout.EAST);

		setVisible(true);
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
		bEndTurn.addActionListener(al);
	}

	public void addTradeListener(ActionListener al) {
		bTrade.addActionListener(al);
	}

	public void addBuyHouseListener(ActionListener al) {
		bBuyHouse.addActionListener(al);
	}

	public void addBuyPlotListener(ActionListener al) {
		bBuyPlot.addActionListener(al);
	}

	public void addSurrenderListener(ActionListener al) {
		bSurrender.addActionListener(al);
	}

	public void addUpdatePlayerListener(ActionListener al) {
		bUpdatePlayer.addActionListener(al);
	}

	public void addPayPrisonListener(ActionListener al) {
		bPayPrison.addActionListener(al);
	}

	public void addUsePrisonLeaveListener(ActionListener al) {
		bUsePrisonLeave.addActionListener(al);
	}

	public void addClearChatListener(ActionListener al) {
		bClearChat.addActionListener(al);
	}

	public void startGame() {
		bEndTurn.setText("Runde beenden");
		bUpdatePlayer.setVisible(true);
	}

	@Override
	public void subscribeErr() {
		JOptionPane.showMessageDialog(this, "Name taken or running game!");
		System.exit(0);
	}
	
	public String showDialog(String msg){
		return gameboard.showPrompt(msg);
	}

	public void updateMyPlayerText(Player p) {
		if (p != null) {
			if (p.isInJail()) {
				lmyPlayer.setText(p.getName() + "(Im Gefängnis)");
			} else {
				lmyPlayer.setText(p.getName());
			}

			lmyPlayer.setForeground(p.getColor());
			lmyPlayerMoney.setText("RM " + p.getMoney());
			lmyPlayerPlots.setText("Gekaufte Grundstücke: " + p.getPlots());

			if(p.isInJail()) {
				bUsePrisonLeave.setVisible(true);
				bPayPrison.setVisible(true);
			} else {
				bUsePrisonLeave.setVisible(false);
				bPayPrison.setVisible(false);
			}
		}
	}

	/**
	 * Append text to a text pane in that color and boldness.
	 */
	public void append(JTextPane tp, String s, Color col, boolean bold) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet as;
		StyledDocument doc;
		int start, end;

		as = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, col);
		as = sc.addAttribute(as, StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		as = sc.addAttribute(as, StyleConstants.Bold, bold);

		doc = tp.getStyledDocument();
		start = doc.getLength();
		end = start + s.length();
		try {
			doc.insertString(start, s + "\n", null);
		} catch (BadLocationException ble) {
			;// XXX how to handle?
		}

		doc.setCharacterAttributes(start, end, as, true);
	}

	public class ChatDisp implements Displayer {

		/**
		 * Take a string and an optional color and display it in the chat box.
		 */
		@Override
		public synchronized void show(Object... args) {
			String s = (String) args[0];
			final Color col;

			if (args.length == 2) {
				col = (Color) args[1];
			} else {
				col = Color.BLACK;
			}

			// Call in the Event Dispatching Thread.
			SwingUtilities.invokeLater(() -> {
				append(tChatBox, s, col, false);
			});
		}

		@Override
		public void reset() {
			tChatBox.setText("");
		}
	}

	public class PlayerDisp implements Displayer {

		/**
		 * Take a Player and display it in the player list.
		 */
		@Override
		public synchronized void show(Object... args) {
			final Player p = (Player) args[0];

			SwingUtilities.invokeLater(() -> {
				append(tPlayerList, "" + p, p.getColor(), true);
			});
		}

		@Override
		public void reset() {
			SwingUtilities.invokeLater(() -> {
				tPlayerList.setText("");
			});
		}

	}

	public class PieceDisp implements Displayer {

		@Override
		public void show(Object... args) {
		}

		@Override
		public void reset() {
			SwingUtilities.invokeLater(() -> {
				gameboard.repaint();
			});
		}
	}

	public class StartDisp implements Displayer {

		@Override
		public void show(Object... args) {
		}

		/**
		 * 'Changes' the StartGame button to a EndTurn button
		 */
		@Override
		public void reset() {
			startGame();
		}
	}
}
