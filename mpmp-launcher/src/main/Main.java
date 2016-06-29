package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * @author Klaus, Oskar
 */
public class Main extends javax.swing.JFrame {

    private JTextField addrField;
    private JLabel addrLabel;
    private JComboBox<String> color;
    private JLabel colorLabel;
    private JButton exitBtn;
    private JComboBox<String> gamemode;
    private JLabel gmLabel;
    private JButton hostBtn;
    private JButton joinBtn;
    private JLabel logolabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private static String s;

    public Main() {
	initComponents();
	pack();
    }

    private void initComponents() {
	logolabel = new javax.swing.JLabel();
	nameField = new JTextField();
	nameLabel = new javax.swing.JLabel();
	gmLabel = new javax.swing.JLabel();
	addrField = new JTextField();
	addrLabel = new javax.swing.JLabel();
	gamemode = new javax.swing.JComboBox<>();
	joinBtn = new javax.swing.JButton();
	hostBtn = new javax.swing.JButton();
	exitBtn = new javax.swing.JButton();
	colorLabel = new javax.swing.JLabel();
	color = new javax.swing.JComboBox<>();

	try {
	    Font f;
	    if (s == "comic") {
		f = new java.awt.Font("Comic Sans MS", 0, 24);
		nameLabel.setFont(f.deriveFont(1, 18));
		addrLabel.setFont(f.deriveFont(1, 18));
		gmLabel.setFont(f.deriveFont(1, 18));
		colorLabel.setFont(f.deriveFont(1, 18));
		nameField.setFont(f.deriveFont(1, 14));
		addrField.setFont(f.deriveFont(1, 14));
		gamemode.setFont(f.deriveFont(1, 14));
		color.setFont(f.deriveFont(1, 14));
		exitBtn.setFont(f.deriveFont(1, 24));
		joinBtn.setFont(f.deriveFont(1, 24));
		hostBtn.setFont(f.deriveFont(1, 24));
		hostBtn.setText("Horsten");
	    } else {
		f = Font.createFont(Font.TRUETYPE_FONT, new File("graphics/font/SourceSansPro-Light.ttf"));
		nameLabel.setFont(f.deriveFont(1, 18));
		addrLabel.setFont(f.deriveFont(1, 18));
		gmLabel.setFont(f.deriveFont(1, 18));
		colorLabel.setFont(f.deriveFont(1, 18));
		nameField.setFont(f.deriveFont(1, 14));
		addrField.setFont(f.deriveFont(1, 14));
		gamemode.setFont(f.deriveFont(1, 14));
		color.setFont(f.deriveFont(1, 14));
		exitBtn.setFont(f.deriveFont(1, 24));
		joinBtn.setFont(f.deriveFont(1, 24));
		hostBtn.setFont(f.deriveFont(1, 24));
	    }
	} catch (FontFormatException | IOException e) {
	    e.printStackTrace();
	}

	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	setTitle("MPMP Launcher");
	setBackground(new java.awt.Color(247, 247, 207));
	setLocationByPlatform(true);
	setResizable(false);

	logolabel.setIcon(new javax.swing.ImageIcon("graphics/logo-small.png"));
	nameField.setBackground(new java.awt.Color(247, 247, 207));
	nameField.setToolTipText("Gib hier deinen Spielernamen ein");
	nameLabel.setText("Spielername");
	gmLabel.setText("Spielmodus");
	addrLabel.setText("Server-Adresse");
	addrField.setBackground(new java.awt.Color(247, 247, 207));
	addrField.setToolTipText("Gib hier die Adresse des Servers ein.");
	gamemode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Spieler", "Zuschauer"}));
	gamemode.setToolTipText("Wähle hier aus, ob du zuschauen oder mitspielen willst.");
	joinBtn.setText("Beitreten");
	joinBtn.setPreferredSize(new java.awt.Dimension(109, 41));
	joinBtn.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		joinBtnActionPerformed(evt);
	    }
	});

	hostBtn.setText("Hosten");
	hostBtn.setActionCommand("");
	hostBtn.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		hostBtnActionPerformed(evt);
	    }
	});

	exitBtn.setText("Beenden");
	exitBtn.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		exitBtnActionPerformed(evt);
	    }
	});

	colorLabel.setText("Farbe");

	color.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Rot", "Gelb", "Gruen", "Blau", "Lila"}));
	color.setToolTipText("Wähle hier deine Farbe aus.");

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
					.addComponent(logolabel)
					.addGroup(layout.createSequentialGroup()
						.addComponent(gamemode, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(color, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
								.addComponent(nameField)
								.addGap(10, 10, 10))
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(nameLabel)
									.addComponent(gmLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(addrField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(addrLabel, javax.swing.GroupLayout.Alignment.TRAILING)
							.addComponent(colorLabel, javax.swing.GroupLayout.Alignment.TRAILING))))
				.addGroup(layout.createSequentialGroup()
					.addComponent(joinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(hostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(exitBtn)))
			.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	);
	layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(logolabel)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(nameLabel)
				.addComponent(addrLabel))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(addrField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(colorLabel)
				.addComponent(gmLabel))
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(gamemode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addGap(18, 18, 18)
			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
				.addComponent(hostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(joinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
			.addContainerGap(28, Short.MAX_VALUE))
	);
	pack();
    }

    private void joinBtnActionPerformed(java.awt.event.ActionEvent evt) {
	String name = nameField.getText();
	String addr = addrField.getText();
	String c = getColor(color.getSelectedIndex());
	String mode = getMode(gamemode.getSelectedIndex());

	try {
	    new ProcessBuilder("java", "-jar", "mpmp.jar", "client", addr, mode, c, name).start();
	} catch (IOException e) {
	    System.out.println(e);
	}
    }

    private void hostBtnActionPerformed(java.awt.event.ActionEvent evt) {
	String name = nameField.getText();
	String mode = getMode(gamemode.getSelectedIndex());
	String c = getColor(color.getSelectedIndex());

	try {
	    new ProcessBuilder("java", "-jar", "mpmp.jar", "server").start();
	    new ProcessBuilder("java", "-jar", "mpmp.jar", "client", "localhost", mode, c, name).start();
	} catch (IOException e) {
	    System.out.println(e);
	}
    }

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {
	System.exit(0);
    }

    public static void main(String args[]) {
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}

	if (args.length != 0) {
	    s = args[0];
	}
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		new Main().setVisible(true);
	    }
	});
    }

    public String getMode(int i) {
	switch (i) {
	    case 0:
		return "player";
	    case 1:
		return "spectator";
	    default:
		return null;
	}
    }

    public String getColor(int i) {
	switch (i) {
	    //Rot, Gelb, Gruen, Blau, Lila
	    case 0:
		return "#FF0000";
	    case 1:
		return "#FFFF00";
	    case 2:
		return "#00FF00";
	    case 3:
		return "#0000FF";
	    case 4:
		return "#8904B1";
	    default:
		return null;
	}
    }
}
