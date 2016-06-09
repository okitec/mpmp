package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.JTextPane;

public class Mainframe extends JFrame {
	private JPanel contentPane;
	private JTextField chatField;

	/**
	 * Create the frame.
	 */
	public Mainframe() {
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

		JTextPane chatBox = new JTextPane();
		chatBox.setEditable(false);
		chatBox.setBounds(471, 11, 159, 294);
		contentPane.add(chatBox);

		chatField = new JTextField();
		chatField.setToolTipText("Enter chat here...");
		// XXX move to controller
		chatField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				Document doc = chatBox.getDocument();
				try {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						// XXX add scrolling to the chat box
						String line = chatField.getText();
						if(line.length() == 0)
							return;
						
						doc.insertString(doc.getLength(), chatField.getText() + "\n", null);
						chatField.setText("");
					}
				} catch (Exception e) {
					; // XXX do something?
				}
			}
		});
		chatField.setBounds(471, 316, 159, 20);
		contentPane.add(chatField);
		chatField.setColumns(10);
	}
}
