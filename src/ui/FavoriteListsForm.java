package ui;

import java.util.List;
import javax.persistence.EntityManager;
import managers.DbManager;
import model.FavoriteList;

public class FavoriteListsForm extends javax.swing.JInternalFrame {

    private List<FavoriteList> lists;
    
    public FavoriteListsForm() {
        initComponents();
    }
    
    private EntityManager getManager() {
        return DbManager.getManager();
    }
    
    private FavoriteList getSelectedFavoriteList() {
        return favoriteListList.size() == 0 ? null : favoriteListList.get(0);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        favoriteListQuery = java.beans.Beans.isDesignTime() ? null : getManager().createQuery("SELECT f FROM FavoriteList f");
        favoriteListList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : favoriteListQuery.getResultList();
        movieQuery = getManager().createQuery("SELECT m FROM Movie m WHERE m.favoriteListId=:favoriteList").setParameter("favoriteList", getSelectedFavoriteList())
        ;
        movieList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : movieQuery.getResultList();
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

        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, favoriteListList, favoriteListsList);
        bindingGroup.addBinding(jListBinding);

        jScrollPane1.setViewportView(favoriteListsList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, movieList, moviesTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${title}"));
        columnBinding.setColumnName("Title");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${rating}"));
        columnBinding.setColumnName("Rating");
        columnBinding.setColumnClass(Float.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${overview}"));
        columnBinding.setColumnName("Overview");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane2.setViewportView(moviesTable);

        jSplitPane1.setRightComponent(jScrollPane2);

        jToolBar2.setRollover(true);

        createButton.setText("Δημιουργία");
        createButton.setFocusable(false);
        createButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private java.util.List<model.Movie> movieList;
    private javax.persistence.Query movieQuery;
    private javax.swing.JTable moviesTable;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
