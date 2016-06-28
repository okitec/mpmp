package view;

import cmds.Subscribe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
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
import main.ErrCode;
import model.Model;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

public class Frame extends JFrame implements Subscribe.SubscribeErrer {

    public final ChatDisp chatDisp;
    public final PlayerDisp playerDisp;

    private Model m;

    private JPanel left;
    private JPanel chat;
    private JPanel bottom;
    private JPanel bottomMenu;
    private JTextField chatField;
    private JTextPane chatBox;
    private JTextPane playerList;
    private JButton bEndTurn;
    private JSVGCanvas gameboard;

    public Frame(Model m) {
        this.m = m;
        chatDisp = new ChatDisp();
        playerDisp = new PlayerDisp();
        createFrame();
        setVisible(true);
    }

    /**
     * Create the frame.
     */
    public void createFrame() {
        setTitle("MPMP");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameboard = new JSVGCanvas();
        gameboard.setBackground(new Color(0, 0, 0, 0));

        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("graphics/background.png")))));
            gameboard.setFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/graphics/font/SourceSansPro-Light.ttf")));

            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
            String uri = new File("graphics/svg/gameboard.svg").toURI().toString();
            org.w3c.dom.Document doc = f.createDocument(uri);
            gameboard.setDocument(doc);

        } catch (FontFormatException | IOException e) {
            //XXX Handle exception
        }

        gameboard.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
                setTitle("MPMP - Loading...");
            }

            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                setTitle("MPMP");
                SVGDocument svgd = gameboard.getSVGDocument();
                final Element element = svgd.getElementById("");
                System.out.println(gameboard.getX() + "  " + gameboard.getSize() + "  " + gameboard.getRenderRect() + "  " + gameboard.getSVGDocument().getRootElement());
                Graphics g = gameboard.getGraphics();
                g.drawOval(0, 0, 50, 50);

                gameboard.invalidate();
            }
        });

        //Set all layouts
        setLayout(new BorderLayout());
        left = new JPanel();
        chat = new JPanel();
        bottom = new JPanel();
        bottomMenu = new JPanel();
        
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        bottomMenu.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));

        //Set testing Buttons
        JButton player1 = new JButton("Player 1");
        JButton player2 = new JButton("Player 2");
        JButton player3=  new JButton("Player 3");
        JButton player4 = new JButton("Player 4");
        JButton player5 = new JButton("Player 5");
        JButton player6 = new JButton("Player 6");
        JButton trade = new JButton("Tauschen");
        JButton buyHouse = new JButton("Haus kaufen");
        JButton buyPlot = new JButton("Strasse kaufen");
        JButton surrender = new JButton("Aufgeben");
        JButton btn12 = new JButton("Current Player");
        JButton btn13 = new JButton("Me is no chat box");

        left.add(player1);
        left.add(player2);
        left.add(player3);
        left.add(player4);
        left.add(player5);
        left.add(player6);
        bottomMenu.add(trade);
        bottomMenu.add(buyHouse);
        bottomMenu.add(buyPlot);
        bottomMenu.add(surrender);
        bottom.add(bottomMenu);
        bottom.add(btn12);
        bottom.add(btn13);
        
        /**
         * Setting everything for the chat prefSize = Width of the chat-frame
         */

        int prefSize = 200;

        playerList = new JTextPane();
        playerList.setEditable(false);
        chat.add(playerList);

        chatField = new JTextField();
        chatField.setToolTipText("Enter chat here...");
        chat.add(chatField);
        chatField.setColumns(10);

        chatBox = new JTextPane();
        chatBox.setEditable(false);
        chat.add(chatBox);

        bEndTurn = new JButton("Runde beenden");
        chat.add(bEndTurn);

        bEndTurn.setPreferredSize(new Dimension(prefSize, 60));
        chatBox.setPreferredSize(new Dimension(prefSize, 300));
        playerList.setPreferredSize(new Dimension(prefSize, 300));
        chatField.setPreferredSize(new Dimension(prefSize, 300));

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameboard.invalidate();
                System.out.println("Resized");
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
        bEndTurn.addActionListener(al);
    }

    @Override
    public void subscribeErr() {
        JOptionPane.showMessageDialog(this, ErrCode.NameTaken.getMessage());
        System.exit(0);
    }

    public class ChatDisp implements Displayer {

        @Override
        public void show(String s) {
            try {
                Document doc = chatBox.getDocument();
                doc.insertString(doc.getLength(), s + "\n", null);
            } catch (BadLocationException ble) {
                ;// XXX how to handle?
            }
        }

        @Override
        public void reset() {
        }
    }

    public class PlayerDisp implements Displayer {

        @Override
        public void show(String s) {
            try {
                Document doc = playerList.getDocument();
                doc.insertString(doc.getLength(), s + "\n", null);
            } catch (BadLocationException ble) {
                ;// XXX how to handle?
            }
        }

        @Override
        public void reset() {
            playerList.setDocument(new DefaultStyledDocument());
        }
    }
}
