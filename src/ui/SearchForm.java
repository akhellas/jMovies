package ui;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import managers.DbManager;
import managers.UIHelper;

import model.FavoriteList;
import model.Genre;
import model.Movie;

// Φόρμα "Αναζήτηση Ταινιών"
public class SearchForm extends javax.swing.JInternalFrame {

    private List<Genre> genres;
    private List<Movie> movies;
    private List<FavoriteList> lists;

    public SearchForm() {
        initComponents();

        // προσθήκη listener για τις αλλαγές του πλαισίου κειμένου για το έτος κυκλοφορίας
        UIHelper.addChangeListener(yearTextField, e -> updateSearchButtonState());
        moviesTable.setAutoCreateRowSorter(true);
        DefaultRowSorter sorter = (DefaultRowSorter) moviesTable.getRowSorter();
        sorter.setSortable(0, false);
        sorter.setSortable(2, false);
        sorter.setSortsOnUpdates(true);

        ListSelectionModel selectionModel = moviesTable.getSelectionModel();
        // προσθήκη listener για την παρακολούθηση των αλλαγών στην επιλογή ταινίας του πίνακα
        selectionModel.addListSelectionListener((ListSelectionEvent lse) -> {
            if (!lse.getValueIsAdjusting()) {
                Movie selected = getSelectedMovie();
                if (selected == null) {
                    return;
                }

                int index = selected.getFavoriteListId() == null
                        ? -1
                        : lists.indexOf(lists.stream().filter(fl -> fl.getId().equals(selected.getFavoriteListId().getId())).findFirst().get());

                SwingUtilities.invokeLater(() -> {
                    // επιλογή της λίστας στην οποία ανήκει η ταινία (αν ανήκει σε κάποια)
                    listsComboBox.setSelectedIndex(index + 1);
                });

            }
        });
    }

    private Genre getSelectedGenre() {
        int index = genreComboBox.getSelectedIndex();
        return index > 0 ? genres.get(index - 1) : null;
    }

    private FavoriteList getSelectedFavoriteList() {
        int index = listsComboBox.getSelectedIndex();
        return index > 0 ? lists.get(index - 1) : null;
    }

    private Movie getSelectedMovie() {
        int row = moviesTable.getSelectedRow();
        return row > -1 ? movies.get(moviesTable.getRowSorter().convertRowIndexToModel(row)) : null;

    }

    private void getGenres() {
        if (genres == null) {
            genres = DbManager.getGenres();
            DefaultComboBoxModel model = new DefaultComboBoxModel(genres.stream().map(genre -> genre.getName()).toArray());
            model.insertElementAt("<Επιλέξτε Είδος>", 0);
            genreComboBox.setModel(model);
        }
        genreComboBox.setSelectedIndex(0);
    }

    private void getFavoriteLists() {
        lists = DbManager.getFavoriteLists();
        DefaultComboBoxModel model = new DefaultComboBoxModel(lists.stream().map(list -> list.getName()).toArray());
        model.insertElementAt("<Επιλέξτε Λίστα Αγαπημένων Ταινιών>", 0);
        listsComboBox.setModel(model);
        listsComboBox.setSelectedIndex(0);
    }

    private void getMovies() {
        DefaultTableModel tableModel = (DefaultTableModel) moviesTable.getModel();
        Movie selected = getSelectedMovie();
        tableModel.setRowCount(0);

        movies = DbManager.getMoviesByGenreAndYear(getSelectedGenre(), Integer.parseInt(yearTextField.getText()));
        movies.forEach(movie -> {
            Object[] rowData = new Object[3];
            rowData[0] = movie.getTitle();
            rowData[1] = movie.getRating();
            rowData[2] = movie.getOverview();
            tableModel.addRow(rowData);
        });

        moviesTable.setModel(tableModel);
        // αν στον πίνακα με τις ταινίες ήταν κάποια επιλεγμένη, την επιλέγει ξανά
        if (selected != null) {
            Optional<Movie> movie = movies.stream().filter(mv -> mv.getId().equals(selected.getId())).findFirst();
            if (movie.isPresent()) {
                int rowIndex = movies.indexOf(movie.get());
                moviesTable.setRowSelectionInterval(rowIndex, rowIndex);
            }
        }

        listsComboBox.setSelectedIndex(0);
    }

    private void setSelectedMovieFavoriteList(FavoriteList list) {
        Movie movie = getSelectedMovie();

        if (movie == null || movie.getFavoriteListId() == list) {
            return;
        }
        movie.setFavoriteListId(list);
        DbManager.updateMovie(movie);
        getMovies();
    }

    // Ενεργοποίηση/Απενεργοποίηση κουμπιού "Αναζήτηση" ανάλογα
    // με το αν έχουν συμπληρωθεί τα απαραίτητα κριτήρια
    private void updateSearchButtonState() {
        try {
            int year = Integer.parseInt(yearTextField.getText());
            boolean isValidYear = year >= 2000 && year <= Calendar.getInstance().get(Calendar.YEAR);
            boolean hasGenre = getSelectedGenre() != null;
            searchButton.setEnabled(isValidYear && hasGenre);
        } catch (NumberFormatException exception) {
            searchButton.setEnabled(false);
        }
    }

    // Ενεργοποίηση/Απενεργοποίηση κουμπιού "Αφαίρεση από Λίστα" ανάλογα
    // με το αν η επιλεγμένη ταινία έχει προστεθεί ή όχι σε λίστα
    private void updateRemoveButtonState() {
        removeButton.setEnabled(listsComboBox.getSelectedIndex() > 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        genreComboBox = new javax.swing.JComboBox<>();
        searchButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        yearTextField = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        moviesTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        listsComboBox = new javax.swing.JComboBox<>();
        removeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Αναζήτηση Ταινιών");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        genreComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genreComboBoxActionPerformed(evt);
            }
        });

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search_mini.png"))); // NOI18N
        searchButton.setText("Αναζήτηση");
        searchButton.setEnabled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clean_mini.png"))); // NOI18N
        clearButton.setText("Καθαρισμός Κριτηρίων");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Είδος Ταινίας:");

        jLabel2.setText("Έτος Κυκλοφορίας (2000 - Σήμερα):");

        try {
            yearTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        yearTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(genreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(genreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(searchButton)
                    .addComponent(clearButton)
                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        moviesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Τίτλος Ταινίας", "Βαθμολογία", "Περιγραφή"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        moviesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        moviesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(moviesTable);
        if (moviesTable.getColumnModel().getColumnCount() > 0) {
            moviesTable.getColumnModel().getColumn(1).setMinWidth(100);
            moviesTable.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        listsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listsComboBoxActionPerformed(evt);
            }
        });

        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/recycle_mini.png"))); // NOI18N
        removeButton.setText("Αφαίρεση από Λίστα");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Προσθήκη σε Λίστα:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeButton)
                    .addComponent(jLabel3))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Κουμπί "Αφαίρεση από Λίστα"
    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        setSelectedMovieFavoriteList(null);
    }//GEN-LAST:event_removeButtonActionPerformed

    // Κουμπί "Αναζήτηση"
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        getMovies();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        yearTextField.setText("");
        ((DefaultTableModel) moviesTable.getModel()).setRowCount(0);
        getGenres();
        getFavoriteLists();
        updateSearchButtonState();
    }//GEN-LAST:event_formComponentShown

    // Κουμπί "Καθαρισμός Κριτηρίων"
    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        genreComboBox.setSelectedIndex(0);
        yearTextField.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void genreComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genreComboBoxActionPerformed
        updateSearchButtonState();
    }//GEN-LAST:event_genreComboBoxActionPerformed

    private void listsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listsComboBoxActionPerformed
        updateRemoveButtonState();
        setSelectedMovieFavoriteList(getSelectedFavoriteList());
    }//GEN-LAST:event_listsComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox<String> genreComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> listsComboBox;
    private javax.swing.JTable moviesTable;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JFormattedTextField yearTextField;
    // End of variables declaration//GEN-END:variables
}
