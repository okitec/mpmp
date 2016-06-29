package view;

import cmds.Subscribe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.SwingUtilities;
import main.ErrCode;
import model.Model;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.util.XMLResourceDescriptor;

public class Frame extends JFrame implements Subscribe.SubscribeErrer {

    public final ChatDisp chatDisp;
    public final PlayerDisp playerDisp;
    private Model m;
    private JPanel left;
    private JPanel chat;
    private JPanel bottom;
    private JPanel currentPlayer;
    private JPanel bottomMenu;
    private JButton trade;
    private JButton buyHouse;
    private JButton buyPlot;
    private JButton surrender;
    private JTextField chatField;
    private JTextPane chatBox;
    private JTextPane playerList;
    private JButton bEndTurn;
    private JSVGCanvas gameboard;
    private org.w3c.dom.Document doc;
    private Font fo;

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
	setMinimumSize(new Dimension(200, 200));
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
		    getCurrentZoom();
		}
	    });
	} catch (FontFormatException | IOException e) {
	    e.printStackTrace();
	}

	//Set all layouts
	setLayout(new BorderLayout());
	left = new JPanel();
	chat = new JPanel();
	bottom = new JPanel();
	bottomMenu = new JPanel();
	currentPlayer = new JPanel();

	left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
	chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
	bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
	bottomMenu.setLayout(new BoxLayout(bottomMenu, BoxLayout.Y_AXIS));
	currentPlayer.setLayout(new BoxLayout(currentPlayer, BoxLayout.Y_AXIS));

	trade = new JButton("Tauschen");
	buyHouse = new JButton("Haus kaufen");
	buyPlot = new JButton("Straße kaufen");
	surrender = new JButton("Aufgeben");
	bEndTurn = new JButton("Runde beenden");

	playerList = new JTextPane();
	playerList.setEditable(false);

	chatBox = new JTextPane();
	chatBox.setEditable(false);

	chatField = new JTextField();
	chatField.requestFocus(true);
	chatField.setSize(new Dimension(10, 100));
	chatField.setSelectionColor(Color.pink);

	//Implement this!
	trade.setEnabled(false);
	buyHouse.setEnabled(false);
	buyPlot.setEnabled(false);

	left.add(new JLabel("Spieler:"));
	left.add(playerList);
	chat.add(chatBox);
	chat.add(chatField);
	chat.add(bEndTurn);

	//Real stuff cP = current Player
	//Player cP = new Player();
	currentPlayer.add(new JLabel("Spieler"));
	currentPlayer.add(new JLabel("DieEchteNilente"));
	currentPlayer.add(new JLabel("RM 0"));
	currentPlayer.add(new JLabel("Gekaufte Straßen: --"));

	bottomMenu.add(trade);
	bottomMenu.add(buyHouse);
	bottomMenu.add(buyPlot);
	bottomMenu.add(surrender);
	bottom.add(bottomMenu);
	bottom.add(new JLabel("          "));
	bottom.add(currentPlayer);

	this.addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		gameboard.invalidate();
	    }
	});

	add(gameboard, BorderLayout.CENTER);
	add(left, BorderLayout.WEST);
	add(chat, BorderLayout.EAST);
	add(bottom, BorderLayout.SOUTH);
	setVisible(true);
	pack();
    }

    public void addChatListener(KeyAdapter k) {
	chatField.addKeyListener(k);
    }

    public String getChat() {
	String chat = chatField.getText();
	chatField.setText("");
	return chat;
    }

    public void addEndTurnListener(ActionListener al) {
	System.out.println("Runde beendet.");
	bEndTurn.addActionListener(al);
    }

    public void addTradeListener(ActionListener al) {
	System.out.println("Tausch.");
	trade.addActionListener(al);
    }

    public void addBuyHouseListener(ActionListener al) {
	System.out.println("Haus gekauft.");
	buyHouse.addActionListener(al);
    }

    public void addBuyPlotListener(ActionListener al) {
	System.out.println("Grundstück gekauft.");
	buyPlot.addActionListener(al);
    }

    public void addSurrenderListener(ActionListener al) {
	System.out.println("Aufgegeben.");
	surrender.addActionListener(al);
    }

    public double getCurrentZoom() {
	System.out.println("Current zoom: " + gameboard.getSVGDocument().getRootElement().getCurrentScale() + "\nCurrent rotation: " + getCurrentRotation());
	return gameboard.getSVGDocument().getRootElement().getCurrentScale();
    }

    public double getCurrentRotation() {
	System.out.println("Current Rotation: " + gameboard.getSVGDocument().getRootElement().getZoomAndPan());
	return gameboard.getSVGDocument().getRootElement().getZoomAndPan();
    }

    @Override
    public void subscribeErr() {
	JOptionPane.showMessageDialog(this, ErrCode.NameTaken.getMessage());
	System.exit(0);
    }

    public class ChatDisp implements Displayer {

	@Override
	public synchronized void show(String s) {
	    // Call in the Event Dispatching Thread.
	    SwingUtilities.invokeLater(() -> {
		try {
		    Document doc = chatBox.getDocument();
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
		    Document doc = playerList.getDocument();
		    doc.insertString(doc.getLength(), s + "\n", null);
		} catch (BadLocationException ble) {
		    ;// XXX how to handle?
		}
	    });
	}

	@Override
	public synchronized void reset() {
	    SwingUtilities.invokeLater(() -> {
		playerList.setDocument(new DefaultStyledDocument());
	    });
	}
    }
}
