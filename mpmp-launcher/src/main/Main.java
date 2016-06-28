package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Klaus, Oskar
 */
public class Main extends javax.swing.JFrame {

	public Main() {
		initComponents();
		loadFont();
		setBackground(new java.awt.Color(247, 247, 207));
		pack();
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logolabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        gmLabel = new javax.swing.JLabel();
        addrField = new javax.swing.JTextField();
        addrLabel = new javax.swing.JLabel();
        gamemode = new javax.swing.JComboBox<>();
        joinBtn = new javax.swing.JButton();
        hostBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        colorLabel = new javax.swing.JLabel();
        color = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MPMP Launcher");
        setBackground(new java.awt.Color(247, 247, 207));
        setLocationByPlatform(true);
        setResizable(false);

        logolabel.setIcon(new javax.swing.ImageIcon("graphics/logo-small.png")); // NOI18N

        nameField.setBackground(new java.awt.Color(247, 247, 207));
        nameField.setFont(new java.awt.Font("Source Sans Pro Light", 0, 14)); // NOI18N
        nameField.setToolTipText("Gib hier deinen Spielernamen ein");

        nameLabel.setFont(new java.awt.Font("Source Sans Pro Light", 1, 18)); // NOI18N
        nameLabel.setText("Spielername");

        gmLabel.setFont(new java.awt.Font("Source Sans Pro Light", 1, 18)); // NOI18N
        gmLabel.setText("Spielmodus");

        addrField.setBackground(new java.awt.Color(247, 247, 207));
        addrField.setFont(new java.awt.Font("Source Sans Pro Light", 0, 14)); // NOI18N
        addrField.setToolTipText("Gib hier die Adresse des Servers ein.");

        addrLabel.setFont(new java.awt.Font("Source Sans Pro Light", 1, 18)); // NOI18N
        addrLabel.setText("Server-Adresse");

        gamemode.setFont(new java.awt.Font("Source Sans Pro Light", 0, 14)); // NOI18N
        gamemode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Spieler", "Zuschauer" }));
        gamemode.setToolTipText("Wähle hier aus, ob du zuschauen oder mitspielen willst.");

        joinBtn.setFont(new java.awt.Font("Source Sans Pro Light", 1, 24)); // NOI18N
        joinBtn.setText("Beitreten");
        joinBtn.setPreferredSize(new java.awt.Dimension(109, 41));
        joinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinBtnActionPerformed(evt);
            }
        });

        hostBtn.setFont(new java.awt.Font("Source Sans Pro Light", 1, 24)); // NOI18N
        hostBtn.setText("Hosten");
        hostBtn.setActionCommand("");
        hostBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostBtnActionPerformed(evt);
            }
        });

        exitBtn.setFont(new java.awt.Font("Source Sans Pro Light", 1, 24)); // NOI18N
        exitBtn.setText("Beenden");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        colorLabel.setFont(new java.awt.Font("Source Sans Pro Light", 1, 18)); // NOI18N
        colorLabel.setText("Farbe");

        color.setFont(new java.awt.Font("Source Sans Pro Light", 0, 14)); // NOI18N
        color.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rot", "Gelb", "Gruen", "Blau", "Lila" }));
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
    }// </editor-fold>//GEN-END:initComponents

	private void joinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinBtnActionPerformed
	    String name = nameField.getText();
	    String addr = addrField.getText();
		String c = getColor(color.getSelectedIndex());
	    String mode = getMode(gamemode.getSelectedIndex());
		
	    try {
			new ProcessBuilder("java", "-jar", "mpmp.jar", "client", addr, mode, c, name).start();
	    } catch (IOException ex) {
		System.out.println(ex);
	    }
	}//GEN-LAST:event_joinBtnActionPerformed

	private void hostBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostBtnActionPerformed
	    String name = nameField.getText();
		String mode = getMode(gamemode.getSelectedIndex());
		String c = getColor(color.getSelectedIndex());
		
		try {
		    new ProcessBuilder("java", "-jar", "mpmp.jar", "server").start();
		    new ProcessBuilder("java", "-jar", "mpmp.jar", "client", "localhost", mode, c, name).start();
	    } catch (IOException ex) {
		    System.out.println(ex);
	    }
	}//GEN-LAST:event_hostBtnActionPerformed

	private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
		System.exit(0);
	}//GEN-LAST:event_exitBtnActionPerformed

	private static String s;
	public static void main(String args[]) {
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
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
		//</editor-fold>
		//</editor-fold>

		if(args.length!=0){
			s = args[0];
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main().setVisible(true);
			}
		});
	}

	public void loadFont() {
	    try {
		Font f;
		if(s == "comic") {
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
	    } catch (FontFormatException | IOException ex) {
		Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    }		
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
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addrField;
    private javax.swing.JLabel addrLabel;
    private javax.swing.JComboBox<String> color;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JButton exitBtn;
    private javax.swing.JComboBox<String> gamemode;
    private javax.swing.JLabel gmLabel;
    private javax.swing.JButton hostBtn;
    private javax.swing.JButton joinBtn;
    private javax.swing.JLabel logolabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables
}
