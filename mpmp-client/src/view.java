import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Klaus, Oskar
 */
public class view extends javax.swing.JFrame {

    JSVGCanvas canvas;

    public view() {
	setTitle("Monopoly Multiplayer");
	setResizable(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	canvas = new JSVGCanvas();
	canvas.setBackground(new Color(0, 0, 0, 0));

	try {
	    setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("graphics/background.png")))));
	    canvas.setFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/res/SourceSansPro-Light.ttf")));

	    String parser = XMLResourceDescriptor.getXMLParserClassName();
	    SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
	    String uri = new File("graphics/svg/gameboard.svg").toURI().toString();
	    Document doc = f.createDocument(uri);
	    canvas.setDocument(doc);

	} catch (FontFormatException | IOException e) {

	}

	canvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
	    public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
		setTitle("Monopoly Multiplayer - Loading...");
	    }

	    public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
		setTitle("Monopoly Multiplayer");
		//Document svgd = canvas.get
		System.out.println(canvas.getX() + "  " + canvas.getSize() + "  " + canvas.getRenderRect() + "  " + canvas.getSVGDocument().getRootElement());
		Graphics g = canvas.getGraphics();
		g.drawOval(0, 0, 50, 50);

		canvas.invalidate();
	    }
	});

	createFrame();
	setVisible(true);
	pack();
    }

    public void createFrame() {
	setLayout(new BorderLayout());

	JPanel left = new JPanel();
	JPanel chat = new JPanel();
	JPanel bottom = new JPanel();
	left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
	chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
	bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));

	JButton btn1 = new JButton("getSVG");
	JButton btn2 = new JButton("Hello World 2");
	JButton btn3 = new JButton("Hello World 3");
	JButton btn4 = new JButton("Hello World 4");
	JButton btn5 = new JButton("Hello World 5");
	JButton btn6 = new JButton("Hello World 6");
	JButton btn8 = new JButton("Hello World 2");
	JButton btn9 = new JButton("Hello World 3");
	JButton btn10 = new JButton("Hello World 4");
	JButton btn11 = new JButton("Hello World 5");
	JButton btn12 = new JButton("Hello World 6");
	JButton btn13 = new JButton("Me is no chat box");
	JButton bEndTurn = new JButton("Runde beenden");

	btn1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

	    }
	});

	left.add(btn1);
	left.add(btn2);
	left.add(btn3);
	left.add(btn4);
	left.add(btn5);
	left.add(btn6);

	JTextPane chatBox = new JTextPane();
	chatBox.setEditable(false);

	JTextField chatField = new JTextField();
	chatField.setToolTipText("Enter chat here...");

	JTextPane playerList = new JTextPane();
	playerList.setEditable(false);

	bEndTurn.setPreferredSize(new Dimension(200, 60));
	chatBox.setPreferredSize(new Dimension(200, 300));
	playerList.setPreferredSize(new Dimension(200, 300));
	chatField.setPreferredSize(new Dimension(200, 300));
	chatField.setColumns(10);

	chat.add(chatBox);
	chat.add(chatField);
	chat.add(bEndTurn);
	chat.add(playerList);

	bottom.add(btn8);
	bottom.add(btn9);
	bottom.add(btn10);
	bottom.add(btn11);
	bottom.add(btn12);
	bottom.add(btn13);

	this.addComponentListener(new ComponentAdapter() {
	    @Override
	    public void componentResized(ComponentEvent e) {
		System.out.println("Resized");
	    }
	});

	add(canvas, BorderLayout.CENTER);
	add(left, BorderLayout.WEST);
	add(chat, BorderLayout.EAST);
	add(bottom, BorderLayout.SOUTH);
    }

    public static void main(String args[]) {
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new view();
	    }
	});
    }
}
