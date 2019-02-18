package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import managers.DbManager;
import model.FavoriteList;
import model.Movie;

public class FavoriteListsForm extends javax.swing.JInternalFrame {

    public FavoriteListsForm() {
        initComponents();
    }

    private EntityManager getManager() {
        return DbManager.getManager();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        MyMoviesPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("MyMoviesPU").createEntityManager();
        favoriteListQuery = java.beans.Beans.isDesignTime() ? null : MyMoviesPUEntityManager.createQuery("SELECT f FROM FavoriteList f");
        favoriteListList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : favoriteListQuery.getResultList();
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
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, favoriteListList, favoriteListsList);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        bindingGroup.addBinding(jListBinding);

        favoriteListsList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                favoriteListsListPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(favoriteListsList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jScrollPane2.setViewportView(moviesTable);

        jSplitPane1.setRightComponent(jScrollPane2);

        jToolBar2.setRollover(true);

        createButton.setText("Δημιουργία");
        createButton.setFocusable(false);
        createButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(createButton);

        editButton.setText("Επεξεργασία");
        editButton.setFocusable(false);
        editButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(editButton);
        jToolBar2.add(jSeparator1);

        deleteButton.setText("Διαγραφή");
        deleteButton.setFocusable(false);
        deleteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed

        String name = JOptionPane.showInputDialog(
                this,
                "Δώστε όνομα νέας Λίστας Αγαπημένων Ταινιών:",
                "Δημιουργία Λίστας Αγαπημένων Ταινιών",
                JOptionPane.QUESTION_MESSAGE
        );

        if (name != null) {
            FavoriteList newList = DbManager.createFavoriteList(name);
            System.out.println(newList.toString());
        }
    }//GEN-LAST:event_createButtonActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        System.out.println("shown fired");

        favoriteListList.clear();
        favoriteListList.addAll(DbManager.getFavoriteLists());
        
        favoriteListsList.setListData((Vector<? extends String>) favoriteListList.stream().map(fav -> fav.getName()));
        
//        favoriteListsList.firePropertyChange("model", favoriteListsList.getModel(), favoriteListList.toArray());
    }//GEN-LAST:event_formComponentShown

    private void favoriteListsListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_favoriteListsListPropertyChange
        System.out.println("propertyChanged: " + evt.getPropertyName());
    }//GEN-LAST:event_favoriteListsListPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager MyMoviesPUEntityManager;
    private javax.swing.JButton createButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private java.util.List<model.FavoriteList> favoriteListList;
    private javax.persistence.Query favoriteListQuery;
    private javax.swing.JList<String> favoriteListsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable moviesTable;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
