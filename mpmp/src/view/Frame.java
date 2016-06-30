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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.AffineTransform;
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
	private JTextField tChatField;
	private JTextPane tChatBox;
	private JTextPane tPlayerList;
	private JLabel lmP;
	private JLabel lmPMoney;
	private JLabel lmPPlots;
	private JButton bEndTurn;
	private ScrollPane sP;
	private JSVGCanvas gameboard;
	private org.w3c.dom.Document doc;
	private Font fo;
	private Player cP;
	private Converter converter;
	private Graphics g;

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
		converter = new Converter(304, 506);	  // XXX magic: original unresized wfld, hfld
		setMinimumSize(new Dimension(800, 800));
		setPreferredSize(new Dimension(1920, 1080));
		createFrame();
	}

	/**
	 * Create the frame.
	 */
	public void createFrame() {
		setTitle("MPMP");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameboard = new JSVGCanvas();

		//Canvas-Stuff
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("graphics/background.png")))));
			fo = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/font/SourceSansPro-Light.ttf"));
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			String uri = new File("graphics/svg/gameboard.svg").toURI().toString();
			doc = f.createDocument(uri);

			gameboard.setBackground(new Color(0, 0, 0, 0));
			gameboard.setFont(fo);
			gameboard.setDocument(doc);
			gameboard.setRecenterOnResize(false);
			gameboard.setEnableRotateInteractor(false);
			gameboard.setEnableResetTransformInteractor(true);

			gameboard.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
				public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
					setTitle("MPMP - Loading...");
				}

				public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
					setTitle("MPMP");
					System.out.println("Resized");
					gameboard.invalidate();
					System.out.println(gameboard.getComponents());
					redrawPlayers();
				}
			});
		} catch (FontFormatException | IOException e) {
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
		bUpdatePlayer = new JButton("Update Spieler");
		bUpdatePlayer.setVisible(false);

		lmP = new JLabel();
		lmPMoney = new JLabel();
		lmPPlots = new JLabel();

		tPlayerList = new JTextPane();
		tPlayerList.setEditable(false);

		tChatBox = new JTextPane();
		tChatBox.setEditable(false);
		tChatBox.setMaximumSize(new Dimension(300, 500));
		sP = new ScrollPane();
		sP.setMaximumSize(new Dimension(300, 500));
		sP.setSize(300, 500);
		//http://stackoverflow.com/questions/2483572/making-a-jscrollpane-automatically-scroll-all-the-way-down
		/*sP.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});*/
		sP.add(tChatBox);

		tChatField = new JTextField();
		tChatField.requestFocus(true);
		tChatField.setSelectionColor(Color.pink);

		pLeft.add(new JLabel("Spieler:"));
		pLeft.add(tPlayerList);
		pChat.add(bUpdatePlayer);
		pChat.add(bStartGame);
		pChat.add(sP);
		pChat.add(tChatField);
		pChat.add(bEndTurn);

		//Real stuff cP = current Player
		//Player cP = new Player();
		pCurrentPlayer.add(new JLabel("Aktueller Spieler"));
		pCurrentPlayer.add(lmP);
		pCurrentPlayer.add(lmPMoney);
		pCurrentPlayer.add(lmPPlots);

		pBottomMenu.add(bTrade);
		pBottomMenu.add(bBuyHouse);
		pBottomMenu.add(bBuyPlot);
		pBottomMenu.add(bSurrender);
		pBottom.add(pBottomMenu);
		pBottom.add(new JLabel("        "));
		pBottom.add(pCurrentPlayer);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				gameboard.invalidate();
				redrawPlayers();
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
		bUpdatePlayer.addActionListener(al);
	}

	public double getCurrentZoom() {
		System.out.println("Current zoom: " + gameboard.getSVGDocument().getRootElement().getCurrentScale() + "\nCurrent rotation: " + getCurrentRotation());
		return gameboard.getSVGDocument().getRootElement().getCurrentScale();
	}

	public double getCurrentRotation() {
		System.out.println("Current Rotation: " + gameboard.getSVGDocument().getRootElement().getZoomAndPan());
		return gameboard.getSVGDocument().getRootElement().getZoomAndPan();
	}

	public void addStartGameListener(ActionListener al) {
		bStartGame.addActionListener(al);
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

	/**
	 * Draw a player piece.
	 */
	public void drawPlayer(Player p) {
		double scale;
		int rOuter = 15;
		int rInner = 12;

		AffineTransform aT = gameboard.getViewBoxTransform();
		scale = gameboard.getSVGDocument().getRootElement().getCurrentScale();
		g = gameboard.getGraphics();

		Point pt = converter.middleRelPx(p.getPos());
		// 0.258: scale transform set in the gameboard SVG internally
		pt.x *= 0.258 * scale;
		pt.y *= 0.258 * scale;
		pt.x += aT.getTranslateX();
		pt.y += aT.getTranslateY();
		rOuter *= scale;
		rInner *= scale;

		g.setColor(Color.BLACK);
		g.fillOval(pt.x - rOuter, pt.y - rOuter, 2 * rOuter, 2 * rOuter);
		g.setColor(p.getColor());
		g.fillOval(pt.x - rInner, pt.y - rInner, 2 * rInner, 2 * rInner);
	}

	/**
	 * Redraw all the players.
	 */
	public void redrawPlayers() {
		gameboard.validate();

		for (Player p : Player.getPlayers()) {
			drawPlayer(p);
		}
	}

	public void updateMyPlayerText(Player p) {
		if (p.isInJail()) {
			lmP.setText(p.getName() + "(Im Gefängnis) (Farbe: " + p.getColor() + ")");
		} else {
			lmP.setForeground(p.getColor());
			lmP.setText(p.getName());
		}

		lmPMoney.setText("RM " + p.getMoney());
		lmPPlots.setText("Gekaufte Grundstücke: " + p.getPlots());
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
				redrawPlayers();
			});
		}
	}
}
