package ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// Φόρμα "Σχετικά"
public class AboutForm extends javax.swing.JInternalFrame {

    private BufferedImage backgroundImage;

    public AboutForm() {
        initComponents();

        backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("src/images/movies2_text.jpg"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        resizeBackground();
    }

    private void resizeBackground() {
        background.setSize(this.getSize());
        Image dimg = backgroundImage.getScaledInstance(background.getWidth(), background.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        background.setIcon(imageIcon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setTitle("Σχετικά...");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/script.png"))); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        resizeBackground();
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    // End of variables declaration//GEN-END:variables
}
