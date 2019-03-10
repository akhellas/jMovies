package ui;

import javax.swing.table.DefaultTableModel;
import managers.DbManager;

// Φόρμα "Στατιστικά"
public class StatisticsForm extends javax.swing.JInternalFrame {

    public StatisticsForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bestMoviesByListButton = new javax.swing.JToggleButton();
        bestMoviesByRatingButton = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        moviesTable = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Στατιστικά");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/statistics.png"))); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        bestMoviesByListButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/best_seller_mini.png"))); // NOI18N
        bestMoviesByListButton.setText("Οι Καλύτερες Ταινίες ανά Λίστα");
        bestMoviesByListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestMoviesByListButtonActionPerformed(evt);
            }
        });

        bestMoviesByRatingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/leaderboard_mini.png"))); // NOI18N
        bestMoviesByRatingButton.setText("Οι Καλύτερες 10 Ταινίες");
        bestMoviesByRatingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestMoviesByRatingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bestMoviesByRatingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                .addComponent(bestMoviesByListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bestMoviesByRatingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bestMoviesByListButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        moviesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        moviesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        moviesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(moviesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Κουμπί "Οι Καλύτερες 10 Ταινίες"
    private void bestMoviesByRatingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bestMoviesByRatingButtonActionPerformed
        bestMoviesByListButton.setSelected(false);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Όνομα Ταινίας");
        tableModel.addColumn("Βαθμολογία");
        tableModel.setRowCount(0);

        DbManager.getBestMoviesByRating(10)
                .forEach(movie -> {
                    Object[] rowData = new Object[2];
                    rowData[0] = movie.getTitle();
                    rowData[1] = movie.getRating();
                    tableModel.addRow(rowData);
                });

        moviesTable.setModel(tableModel);
    }//GEN-LAST:event_bestMoviesByRatingButtonActionPerformed

    // Κουμπί "Οι Καλύτερες Ταινίες ανά Λίστα"
    private void bestMoviesByListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bestMoviesByListButtonActionPerformed
        bestMoviesByRatingButton.setSelected(false);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Όνομα Ταινίας");
        tableModel.setRowCount(0);

        DbManager.getBestMoviesByList()
                .forEach(movie -> {
                    Object[] rowData = new Object[1];
                    rowData[0] = movie.getTitle();
                    tableModel.addRow(rowData);
                });

        moviesTable.setModel(tableModel);
    }//GEN-LAST:event_bestMoviesByListButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        moviesTable.setModel(new DefaultTableModel());
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton bestMoviesByListButton;
    private javax.swing.JToggleButton bestMoviesByRatingButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable moviesTable;
    // End of variables declaration//GEN-END:variables
}
