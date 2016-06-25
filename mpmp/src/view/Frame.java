package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import cmds.ChatUpdate;
import cmds.ClientlistUpdate;
import cmds.Subscribe;
import javax.swing.JOptionPane;
import main.ErrCode;
import model.Model;
import model.Player;

public class Frame extends JFrame implements Subscribe.SubscribeErrer {
	public final ChatDisp chatDisp;
	public final PlayerDisp playerDisp;

	private Model m;
	
	private JPanel contentPane;
	private JTextField chatField;
	private JTextPane chatBox;
	private JTextPane playerList; 
	private JButton bEndTurn;
	
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
		setResizable(true);
		setTitle("mpmp - Multiplayer Monopoly");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 1000));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		bEndTurn = new JButton("Runde beenden");
		bEndTurn.setPreferredSize(new Dimension(500, 30));

		chatBox = new JTextPane();
		chatBox.setEditable(false);
		chatBox.setPreferredSize(new Dimension(500, 300));

		chatField = new JTextField();
		chatField.setToolTipText("Enter chat here...");
		chatField.setPreferredSize(new Dimension(500, 10));
		chatField.setColumns(10);

		playerList = new JTextPane();
		playerList.setEditable(false);
		playerList.setPreferredSize(new Dimension(500, 300));

		contentPane.add(chatBox);
		contentPane.add(chatField);
		contentPane.add(bEndTurn);
		contentPane.add(playerList);
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
			} catch(BadLocationException ble) {
				;// XXX how to handle?
			}
		}

		@Override
		public void reset() {}
	}

	public class PlayerDisp implements Displayer {
		@Override
		public void show(String s) {
			try {
				Document doc = playerList.getDocument();
				doc.insertString(doc.getLength(), s + "\n", null);
			} catch(BadLocationException ble) {
				;// XXX how to handle?
			}
		}

		@Override
		public void reset() {
			playerList.setDocument(new DefaultStyledDocument());
		}
	}
}
