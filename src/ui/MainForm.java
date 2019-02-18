package ui;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import managers.DbManager;

public class MainForm extends javax.swing.JFrame {

    private final WelcomeForm welcomeForm = new WelcomeForm();
    private final FavoriteListsForm favoriteListsForm = new FavoriteListsForm();

    public MainForm() {
        initComponents();

        showWelcome();
    }

    private void showWelcome() {
        if (!welcomeForm.isVisible()) {
            desktop.add(welcomeForm);
            try {
                welcomeForm.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            welcomeForm.setVisible(true);
        }
    }

    private void showFavoriteLists() {
        if (!favoriteListsForm.isVisible()) {
            desktop.add(favoriteListsForm);
            favoriteListsForm.setVisible(true);
        }
    }

    class TaskInitialize extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            DbManager.initializeDb();
            return null;
        }

        @Override
        protected void done() {
            progressInitialize.setVisible(false);
            dialogInitialize.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogInitialize = new javax.swing.JDialog();
        progressInitialize = new javax.swing.JProgressBar();
        labelInitialize = new javax.swing.JLabel();
        buttonInitialize = new javax.swing.JButton();
        buttonCancelInitialize = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        desktop = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        initializeMenuItem = new javax.swing.JMenuItem();
        favoriteListsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();

        dialogInitialize.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogInitialize.setTitle("Ανάκτηση και Αποθήκευση Δεδομένων");
        dialogInitialize.setAlwaysOnTop(true);
        dialogInitialize.setModal(true);
        dialogInitialize.setUndecorated(true);
        dialogInitialize.setResizable(false);
        dialogInitialize.setSize(new java.awt.Dimension(600, 400));

        progressInitialize.setFocusable(false);
        progressInitialize.setIndeterminate(true);

        labelInitialize.setText("Επιθυμείτε να γίνει Ανάκτηση και Αποθήκευση Δεδομένων Ταινιών;");

        buttonInitialize.setText("Αρχικοποίηση");
        buttonInitialize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInitializeActionPerformed(evt);
            }
        });

        buttonCancelInitialize.setText("Άκυρο");
        buttonCancelInitialize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelInitializeActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(3);
        jTextArea1.setText("Προσοχή: \nΜε την αρχικοποίηση θα γίνει διαγραφή της Βάσης Δεδομένων\nκαι θα χαθούν οριστικά οι Λίστες Αγαπημένων Ταινιών!");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout dialogInitializeLayout = new javax.swing.GroupLayout(dialogInitialize.getContentPane());
        dialogInitialize.getContentPane().setLayout(dialogInitializeLayout);
        dialogInitializeLayout.setHorizontalGroup(
            dialogInitializeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInitializeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogInitializeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelInitialize, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                    .addComponent(progressInitialize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogInitializeLayout.createSequentialGroup()
                        .addComponent(buttonInitialize, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCancelInitialize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogInitializeLayout.setVerticalGroup(
            dialogInitializeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogInitializeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInitialize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressInitialize, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogInitializeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonInitialize)
                    .addComponent(buttonCancelInitialize))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyMovies");
        setSize(new java.awt.Dimension(600, 400));

        desktop.setAutoscrolls(true);

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 997, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );

        fileMenu.setText("Αρχείο");

        initializeMenuItem.setText("Ανάκτηση και Αποθήκευση Δεδομένων Ταινιών");
        initializeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initializeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(initializeMenuItem);

        favoriteListsMenuItem.setText("Διαχείριση Λιστών Αγαπημένων Ταινιών");
        favoriteListsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoriteListsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(favoriteListsMenuItem);
        fileMenu.add(jSeparator1);

        exitMenuItem.setText("Έξοδος");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initializeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initializeMenuItemActionPerformed

        progressInitialize.setVisible(false);
        dialogInitialize.pack();
        dialogInitialize.setLocationRelativeTo(this);

        dialogInitialize.setVisible(true);
    }//GEN-LAST:event_initializeMenuItemActionPerformed

    private void buttonInitializeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInitializeActionPerformed

        progressInitialize.setVisible(true);
        new TaskInitialize().execute();

    }//GEN-LAST:event_buttonInitializeActionPerformed

    private void buttonCancelInitializeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelInitializeActionPerformed
        dialogInitialize.setVisible(false);
    }//GEN-LAST:event_buttonCancelInitializeActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void favoriteListsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoriteListsMenuItemActionPerformed
        showFavoriteLists();
    }//GEN-LAST:event_favoriteListsMenuItemActionPerformed

    public static void main(String args[]) {
        UIManager.put("control", new Color(128, 128, 128));
        UIManager.put("info", new Color(128, 128, 128));
        UIManager.put("nimbusBase", new Color(18, 30, 49));
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
        UIManager.put("nimbusGreen", new Color(176, 179, 50));
        UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
        UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
        UIManager.put("nimbusOrange", new Color(191, 98, 4));
        UIManager.put("nimbusRed", new Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
        UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
        UIManager.put("text", new Color(230, 230, 230));

        UIManager.put("OptionPane.cancelButtonText", "Ακύρωση");
        UIManager.put("OptionPane.okButtonText", "ΟΚ");

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancelInitialize;
    private javax.swing.JButton buttonInitialize;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JDialog dialogInitialize;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem favoriteListsMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem initializeMenuItem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelInitialize;
    private javax.swing.JProgressBar progressInitialize;
    // End of variables declaration//GEN-END:variables
}
