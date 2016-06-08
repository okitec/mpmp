package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Klaus, Oskar
 */
public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
        System.out.println("Launcher showed.");
        logolabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/logo_small.png"))); // NOI18N
        loadFont();
        System.out.println("Sucessfully loaded font");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logolabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        gameHost = new javax.swing.JLabel();
        gameJoin = new javax.swing.JLabel();
        gameHostName = new javax.swing.JTextField();
        gameHostPlayerName = new javax.swing.JLabel();
        gameJoinPlayerName = new javax.swing.JLabel();
        gameJoinName = new javax.swing.JTextField();
        gameJoinMode = new javax.swing.JLabel();
        gameJoinAdressText = new javax.swing.JTextField();
        gameJoinAdress = new javax.swing.JLabel();
        gameJoinChoose = new javax.swing.JComboBox<>();
        gameJoinBtn = new javax.swing.JButton();
        gameHostBtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        exitBtn = new javax.swing.JButton();
        version = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MPMP Launcher");
        setBackground(new java.awt.Color(247, 247, 207));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 420, 604));
        setMaximumSize(new java.awt.Dimension(420, 604));
        setMinimumSize(new java.awt.Dimension(420, 604));
        setResizable(false);

        logolabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/logo_small.png"))); // NOI18N

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        gameHost.setFont(new java.awt.Font("Monopoly", 1, 24)); // NOI18N
        gameHost.setText("Spiel hosten");

        gameJoin.setFont(new java.awt.Font("Monopoly", 1, 24)); // NOI18N
        gameJoin.setText("Spiel beitreten");

        gameHostName.setBackground(new java.awt.Color(247, 247, 207));
        gameHostName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gameHostName.setToolTipText("Gib hier deinen Spielernamen ein");

        gameHostPlayerName.setFont(new java.awt.Font("Monopoly", 0, 18)); // NOI18N
        gameHostPlayerName.setText("Spielername");

        gameJoinPlayerName.setFont(new java.awt.Font("Monopoly", 0, 18)); // NOI18N
        gameJoinPlayerName.setText("Spielername");

        gameJoinName.setBackground(new java.awt.Color(247, 247, 207));
        gameJoinName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gameJoinName.setToolTipText("Gib hier deinen Spielernamen ein");

        gameJoinMode.setFont(new java.awt.Font("Monopoly", 0, 18)); // NOI18N
        gameJoinMode.setText("Spielmodus");

        gameJoinAdressText.setBackground(new java.awt.Color(247, 247, 207));
        gameJoinAdressText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gameJoinAdressText.setToolTipText("Gib hier die Adresse des Servers ein.");

        gameJoinAdress.setFont(new java.awt.Font("Monopoly", 0, 18)); // NOI18N
        gameJoinAdress.setText("Server Adresse");

        gameJoinChoose.setFont(new java.awt.Font("Monopoly", 0, 18)); // NOI18N
        gameJoinChoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Spieler", "Zuschauer" }));
        gameJoinChoose.setToolTipText("Wähle hier aus, ob du zuschauen oder mitspielen willst.");

        gameJoinBtn.setFont(new java.awt.Font("Monopoly", 0, 24)); // NOI18N
        gameJoinBtn.setText("Beitreten");
        gameJoinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameJoinBtnActionPerformed(evt);
            }
        });

        gameHostBtn.setFont(new java.awt.Font("Monopoly", 0, 24)); // NOI18N
        gameHostBtn.setText("Hosten");
        gameHostBtn.setActionCommand("");
        gameHostBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameHostBtnActionPerformed(evt);
            }
        });

        exitBtn.setFont(new java.awt.Font("Monopoly", 0, 24)); // NOI18N
        exitBtn.setText("Beenden");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        version.setFont(new java.awt.Font("Monopoly", 0, 12)); // NOI18N
        version.setText("Version 0.1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logolabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gameHostName)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gameHost)
                                    .addComponent(gameHostPlayerName))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(gameHostBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gameJoinName)
                            .addComponent(gameJoinChoose, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gameJoinAdressText, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gameJoin)
                                    .addComponent(gameJoinPlayerName)
                                    .addComponent(gameJoinAdress)
                                    .addComponent(gameJoinMode))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(gameJoinBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(version)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logolabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gameHost)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameHostPlayerName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(gameHostName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gameJoinName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gameJoin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameJoinPlayerName)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameJoinAdress)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameJoinAdressText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameJoinMode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameJoinChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameJoinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(gameHostBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(version))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gameJoinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameJoinBtnActionPerformed
        String name = gameJoinName.getText();
        String adress = gameJoinAdressText.getText();
        int gamemode = gameJoinChoose.getSelectedIndex(); //0: Spieler, 1: Zuschauer
        try {
            ProcessBuilder pb = new ProcessBuilder("java -jar mpmp.jar server");
            Process p = pb.start();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_gameJoinBtnActionPerformed

    private void gameHostBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameHostBtnActionPerformed
        try {
            ProcessBuilder pb = new ProcessBuilder("java -jar mpmp.jar client");
            Process p = pb.start();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_gameHostBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitBtnActionPerformed

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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    public void loadFont() {
        try {
            Font f;
            f = Font.createFont(Font.TRUETYPE_FONT,
                    this.getClass().getResourceAsStream("/res/Monopoly.ttf") );
       
            gameHost.setFont(f.deriveFont(Font.TRUETYPE_FONT, 24));
            gameJoin.setFont(f.deriveFont(Font.TRUETYPE_FONT, 24));
            gameHostPlayerName.setFont(f.deriveFont(Font.TRUETYPE_FONT, 18));
            gameJoinAdress.setFont(f.deriveFont(Font.TRUETYPE_FONT, 18));
            gameJoinMode.setFont(f.deriveFont(Font.TRUETYPE_FONT, 18));
            gameJoinPlayerName.setFont(f.deriveFont(Font.TRUETYPE_FONT, 18));
            gameJoinBtn.setFont(f.deriveFont(Font.TRUETYPE_FONT, 24));
            gameHostBtn.setFont(f.deriveFont(Font.TRUETYPE_FONT, 24));
            version.setFont(f.deriveFont(Font.TRUETYPE_FONT, 12));
        
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitBtn;
    private javax.swing.JLabel gameHost;
    private javax.swing.JButton gameHostBtn;
    private javax.swing.JTextField gameHostName;
    private javax.swing.JLabel gameHostPlayerName;
    private javax.swing.JLabel gameJoin;
    private javax.swing.JLabel gameJoinAdress;
    private javax.swing.JTextField gameJoinAdressText;
    private javax.swing.JButton gameJoinBtn;
    private javax.swing.JComboBox<String> gameJoinChoose;
    private javax.swing.JLabel gameJoinMode;
    private javax.swing.JTextField gameJoinName;
    private javax.swing.JLabel gameJoinPlayerName;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel logolabel;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}
