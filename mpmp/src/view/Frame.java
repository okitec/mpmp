package view;

import clientmodel.Model;
import java.awt.event.KeyAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

public class Frame extends JFrame implements cmds.ChatUpdate.ChatAdder {
	private JPanel contentPane;
	private JTextField chatField;
	private Model m;
	private JTextPane chatBox;
	
	public Frame(Model m) {
		this.m = m;
		createFrame();
		setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public void createFrame() {
		setResizable(false);
		setTitle("mpmp - Multiplayer Monopoly");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton bEndTurn = new JButton("Runde beenden");
		bEndTurn.setBounds(471, 345, 159, 34);
		contentPane.add(bEndTurn);

		chatBox = new JTextPane();
		chatBox.setEditable(false);
		chatBox.setBounds(471, 11, 159, 294);
		contentPane.add(chatBox);

		chatField = new JTextField();
		chatField.setToolTipText("Enter chat here...");
		chatField.setBounds(471, 316, 159, 20);
		contentPane.add(chatField);
		chatField.setColumns(10);
	}
	
	public void addChatListener(KeyAdapter k) {
		chatField.addKeyListener(k);
	}

	public String getChat() {
		String chat = chatField.getText();
		chatField.setText("");
		return chat;
	}

	@Override
	public void addChat(String s) {
		try {
			Document doc = chatBox.getDocument();
			doc.insertString(doc.getLength(), s + "\n", null);
		} catch(BadLocationException ble) {
			// XXX how to handle?
			;
		}
	}
}
