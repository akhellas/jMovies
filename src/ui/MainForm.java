package ui;

import java.awt.Color;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import javax.swing.SwingWorker;
import javax.swing.UIManager;
import managers.ApiManager;
import managers.DbManager;
import managers.JsonDeserializer;
import managers.UIHelper;
import model.Genre;
import model.Movie;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

// Βασική Φόρμα Εφαρμογής
public class MainForm extends javax.swing.JFrame {

    private static final String API_GENERAL_ERROR = "Αποτυχία σύνδεσης με το API (api.themoviedb.org)";
    private static final String DB_INITIALIZATION_SUCCESS = "Η ανάκτηση των δεδομένων ολοκληρώθηκε";
    private static final String SYSTEM_EXIT = "Είστε σίγουροι ότι επιθυμείτε την οριστική έξοδο από την εφαρμογή MyMovies;";
    
    private final WelcomeForm welcomeForm = new WelcomeForm();
    private final FavoriteListsForm favoriteListsForm = new FavoriteListsForm();
    private final SearchForm searchForm = new SearchForm();
    private final StatisticsForm statisticsForm = new StatisticsForm();
    private final AboutForm aboutForm = new AboutForm();

    public MainForm() throws IOException {
        initComponents();

        Image i = ImageIO.read(getClass().getResource("/icons/cinema.png"));
        setIconImage(i);

        showForm(welcomeForm, true);
    }

    // μέθοδος για την εμφάνιση των διαφόρων φορμών της εφαρμογής
    private void showForm(JInternalFrame form, Boolean isMaximized) {
        if (!form.isVisible()) {
            desktop.add(form);
            if (isMaximized) {
                try {
                    form.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            form.setVisible(true);
        } else {
            form.toFront();
        }
    }

    // SwingWorker για την εκτέλεση της αρχικοποίησης της ΒΔ
    // σε άλλο thread για να μην "παγώνει" το UI
    class TaskInitialize extends SwingWorker<Void, Void> {

        private List<Genre> genres = new ArrayList<>();
        private List<Movie> movies = new ArrayList<>();

        @Override
        protected Void doInBackground() {
            try {
                JSONArray genresJson = ApiManager.getGenres();

                // κάνουμε deserialize από json σε λίστα από Genre τα genres και
                // τα φιλτράρουμε σύμφωνα με τις απαιτήσεις της εργασίας
                genres = (List<Genre>) JsonDeserializer.genresFromJson(genresJson)
                        .stream()
                        .filter(g -> JsonDeserializer.isAcceptable(g.getId()))
                        .collect(Collectors.toList());

                JSONArray moviesJson = ApiManager.getMovies();

                // κάνουμε deserialize το json με τις ταινίες και επειδή μερικές φορές
                // το API μας επιστρέφει την ίδια ταινία σε παραπάνω από μία σελίδες, 
                // τις φιλτράρουμε με το id τους
                movies = JsonDeserializer.moviesFromJson(moviesJson, genres)
                        .stream()
                        .collect(Collectors.toMap(Movie::getId, m -> m, (m, q) -> m))
                        .values()
                        .stream()
                        .collect(Collectors.toList());
                DbManager.initializeDb(genres, movies);

                SwingUtilities.invokeLater(() -> {
                    UIHelper.showInfo(null, DB_INITIALIZATION_SUCCESS, "");
                });
            } catch (IOException | ParseException apiException) {
                UIHelper.showError(null, API_GENERAL_ERROR);
            } catch (Exception dbException) {
                UIHelper.showError(null, dbException.getMessage());
            }
            return null;
        }

        @Override
        protected void done() {
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
        welcomeMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        initializeMenuItem = new javax.swing.JMenuItem();
        favoriteListsMenuItem = new javax.swing.JMenuItem();
        searchMenuItem = new javax.swing.JMenuItem();
        statisticsMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

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

        buttonInitialize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/initialize_mini.png"))); // NOI18N
        buttonInitialize.setText("Αρχικοποίηση");
        buttonInitialize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInitializeActionPerformed(evt);
            }
        });

        buttonCancelInitialize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel_mini.png"))); // NOI18N
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

        welcomeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK));
        welcomeMenuItem.setText("Αρχική");
        welcomeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                welcomeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(welcomeMenuItem);
        fileMenu.add(jSeparator1);

        initializeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK));
        initializeMenuItem.setText("Ανάκτηση και Αποθήκευση Δεδομένων Ταινιών");
        initializeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initializeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(initializeMenuItem);

        favoriteListsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK));
        favoriteListsMenuItem.setText("Διαχείριση Λιστών Αγαπημένων Ταινιών");
        favoriteListsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoriteListsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(favoriteListsMenuItem);

        searchMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK));
        searchMenuItem.setText("Αναζήτηση Ταινιών");
        searchMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(searchMenuItem);

        statisticsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK));
        statisticsMenuItem.setText("Στατιστικά");
        statisticsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(statisticsMenuItem);
        fileMenu.add(jSeparator2);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.SHIFT_MASK));
        exitMenuItem.setText("Έξοδος");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        aboutMenu.setText("Σχετικά");

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK));
        aboutMenuItem.setText("Σχετικά με το MyMovies");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        aboutMenu.add(aboutMenuItem);

        jMenuBar1.add(aboutMenu);

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

    // Μενού "Αρχείο -> Ανάκτηση και Αποθήκευση Δεδομένων Ταινιών"
    private void initializeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initializeMenuItemActionPerformed
        progressInitialize.setVisible(false);
        dialogInitialize.pack();
        dialogInitialize.setLocationRelativeTo(this);
        dialogInitialize.getRootPane().setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

        dialogInitialize.setVisible(true);
    }//GEN-LAST:event_initializeMenuItemActionPerformed

    // Κουμπί "Αρχικοποίηση" του πλαισίου διαλόγου για την αρχικοποίηση της ΒΔ
    private void buttonInitializeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInitializeActionPerformed
        progressInitialize.setVisible(true);
        new TaskInitialize().execute();
    }//GEN-LAST:event_buttonInitializeActionPerformed

    // Κουμπί "Ακύρωση" του πλαισίου διαλόγου για την αρχικοποίηση της ΒΔ
    private void buttonCancelInitializeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelInitializeActionPerformed
        dialogInitialize.setVisible(false);
    }//GEN-LAST:event_buttonCancelInitializeActionPerformed

    // Μενού "Αρχείο -> Έξοδος"
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        if (UIHelper.showConfirmation(this, SYSTEM_EXIT, "Έξοδος")) {
            System.exit(0);
        }       
    }//GEN-LAST:event_exitMenuItemActionPerformed

    // Μενού "Αρχείο -> Διαχείριση Λιστών Αγαπημένων Ταινιών"
    private void favoriteListsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoriteListsMenuItemActionPerformed
        showForm(favoriteListsForm, false);
    }//GEN-LAST:event_favoriteListsMenuItemActionPerformed

    // Μενού "Αρχείο -> Αρχική"
    private void welcomeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_welcomeMenuItemActionPerformed
        showForm(welcomeForm, true);
    }//GEN-LAST:event_welcomeMenuItemActionPerformed

    // Μενού "Αρχείο -> Αναζήτηση Ταινιών"
    private void searchMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMenuItemActionPerformed
        showForm(searchForm, false);
    }//GEN-LAST:event_searchMenuItemActionPerformed

    // Μενού "Αρχείο -> Στατιστικά"
    private void statisticsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsMenuItemActionPerformed
        showForm(statisticsForm, false);
    }//GEN-LAST:event_statisticsMenuItemActionPerformed

    // Μενού "Σχετικά -> Σχετικά με το MyMovies"
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        showForm(aboutForm, true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    // η main της εφαρμογής
    public static void main(String args[]) {
        // Αρχικοποίηση του θέματος "Dark Nimbus" για την εμφάνιση του UI
        UIManager.put("control", new Color(128, 128, 128));
        UIManager.put("info", new Color(128, 128, 128));
        UIManager.put("nimbusBase", new Color(18, 30, 49));
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(80, 80, 80));
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
            Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MainForm().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem aboutMenuItem;
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
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelInitialize;
    private javax.swing.JProgressBar progressInitialize;
    private javax.swing.JMenuItem searchMenuItem;
    private javax.swing.JMenuItem statisticsMenuItem;
    private javax.swing.JMenuItem welcomeMenuItem;
    // End of variables declaration//GEN-END:variables
}
