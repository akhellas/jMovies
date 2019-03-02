package ui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import managers.DbManager;
import managers.UIHelper;
import model.FavoriteList;
import model.Movie;

// Φόρμα "Διαχείριση Λιστών Αγαπημένων Ταινιών"
public class FavoriteListsForm extends javax.swing.JInternalFrame {

    private static final String REMOVE_LIST_CONFIRMATION_MESSAGE = "Είστε σίγουροι ότι επιθυμείτε την οριστική διαγραφή των επιλεγμένων Λιστών;";

    private List<FavoriteList> lists;
    private List<Movie> movies;

    public FavoriteListsForm() {
        initComponents();
    }

    public List<FavoriteList> getSelectedLists() {
        return Arrays.stream(favoriteListsList.getSelectedIndices()).boxed()
                .map(idx -> lists.get(idx))
                .collect(Collectors.toList());
    }

    public FavoriteList getSelectedList() {
        int index = favoriteListsList.getSelectedIndex();
        if (index == -1) {
            return null;
        }
        return lists.get(index);
    }

    // ενεργοποιεί/απενεργοποιεί τα κουμπία "Διαγραφή" και "Επεξεργασία"
    // ανάλογα με το πόσες λίστες είναι επιλεγμένες
    private void updateButtons() {
        editButton.setEnabled(favoriteListsList.getSelectedIndices().length == 1);
        deleteButton.setEnabled(favoriteListsList.getSelectedIndices().length > 0);
    }

    private void getFavoriteLists() {
        DefaultListModel listModel = new DefaultListModel();

        lists = DbManager.getFavoriteLists();
        lists.forEach(list -> listModel.addElement(list.getName()));

        favoriteListsList.setModel(listModel);

        updateButtons();
    }

    private void getMoviesBySelectedList() {
        FavoriteList selected = getSelectedList();
        DefaultTableModel tableModel = (DefaultTableModel) moviesTable.getModel();
        tableModel.setRowCount(0);

        if (selected == null) {
            return;
        }

        movies = DbManager.getMoviesByFavoriteList(selected);

        movies.forEach(movie -> {
            Object[] rowData = new Object[3];
            rowData[0] = movie.getTitle();
            rowData[1] = movie.getRating();
            rowData[2] = movie.getOverview();
            tableModel.addRow(rowData);
        });

        moviesTable.setModel(tableModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        favoriteListsList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        moviesTable = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        createButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        deleteButton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Διαχείριση Λιστών Αγαπημένων Ταινιών");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/wish_list.png"))); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        favoriteListsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                favoriteListsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(favoriteListsList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        moviesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Τίτλος Ταινίας", "Βαθμολογία", "Περιγραφή"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        moviesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(moviesTable);
        if (moviesTable.getColumnModel().getColumnCount() > 0) {
            moviesTable.getColumnModel().getColumn(1).setPreferredWidth(80);
            moviesTable.getColumnModel().getColumn(1).setMaxWidth(80);
        }

        jSplitPane1.setRightComponent(jScrollPane2);

        jToolBar2.setRollover(true);

        createButton.setText("Δημιουργία...");
        createButton.setFocusable(false);
        createButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(createButton);

        editButton.setText("Επεξεργασία...");
        editButton.setFocusable(false);
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(editButton);
        jToolBar2.add(jSeparator1);

        deleteButton.setText("Διαγραφή");
        deleteButton.setFocusable(false);
        deleteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(deleteButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Κουμπί "Δημιουργία"
    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed

        UIManager.put("OptionPane.okButtonText", "Αποθήκευση");
        String name = JOptionPane.showInputDialog(
                this,
                "Δώστε όνομα νέας Λίστας Αγαπημένων Ταινιών:",
                "Δημιουργία Λίστας Αγαπημένων Ταινιών",
                JOptionPane.QUESTION_MESSAGE
        );
        UIManager.put("OptionPane.okButtonText", "ΟΚ");
        if (name != null) {
            FavoriteList newList = DbManager.createFavoriteList(name);
            getFavoriteLists();
        }
    }//GEN-LAST:event_createButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        getFavoriteLists();
        getMoviesBySelectedList();
    }//GEN-LAST:event_formComponentShown

    // Κουμπί "Διαγραφή"
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (UIHelper.showConfirmation(this, REMOVE_LIST_CONFIRMATION_MESSAGE, "Επιβεβαίωση Διαγραφής")) {
            DbManager.deleteFavoriteLists(getSelectedLists());
        }
        getFavoriteLists();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void favoriteListsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_favoriteListsListValueChanged
        if (evt.getValueIsAdjusting()) {
            updateButtons();
            getMoviesBySelectedList();
        }
    }//GEN-LAST:event_favoriteListsListValueChanged

    // Κουμπί "Επεξεργασία"
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        FavoriteList list = getSelectedList();
        UIManager.put("OptionPane.okButtonText", "Αποθήκευση");
        String name = (String) JOptionPane.showInputDialog(
                this,
                "Δώστε νέο όνομα Λίστας Αγαπημένων Ταινιών:",
                "Επεξεργασία Λίστας Αγαπημένων Ταινιών",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                list.getName()
        );
        UIManager.put("OptionPane.okButtonText", "ΟΚ");
        if (name != null) {
            list.setName(name);
            DbManager.updateFavoriteList(list);
            getFavoriteLists();
        }
    }//GEN-LAST:event_editButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JList<String> favoriteListsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable moviesTable;
    // End of variables declaration//GEN-END:variables
}
