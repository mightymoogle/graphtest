/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Mighty
 */
public class GraphEditor extends javax.swing.JDialog {
     
     private GraphEditorPanel x;    
     private boolean done = false;
     private GraphEditorPanel.Arrange arrangeType = GraphEditorPanel.Arrange.CIRCLE;     
     Matrix input;    
        
    /**
     * Creates new form GraphEditor
     */
    public GraphEditor(java.awt.Frame parent, boolean modal, Matrix matr, boolean readOnly) {
        super(parent, modal);
        initComponents();
        
        input = matr;
              
        x= new GraphEditorPanel(input,readOnly);  
                
        try {
            Image i = ImageIO.read(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/lightbulb.png"));
            setIconImage(i);
        } catch (IOException ex) {            
        }
        
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(x,BorderLayout.CENTER);  
        x.repaint();
                        
        //AUTOARRANGE
        autoArrangeButton.doClick();
        
        if (readOnly) x.setMode(GraphEditorPanel.Mode.READ_ONLY);
          if (readOnly) {
            //jToolBar1.setVisible(false);
              
              handToggleButton.setVisible(false);            
              arrowToggleButton.setVisible(false);
              
              insertButton.setVisible(false);
              autoArrangeButton.setVisible(false);
              finishButton.setVisible(false);
              
            this.setTitle(this.getTitle()+" [READ-ONLY]");
        }
        
        x.setVisible(true);
        
        
    }    
    
    
    //Call after SetArrange() if it was read_only. Fixes bug
    public void fixReadOnly() {
        x.setMode(GraphEditorPanel.Mode.READ_ONLY);
    }

    public void updateTitle(String text) {
        this.setTitle(this.getTitle()+" - "+text);        
    }
    
    public void setArrange(GraphEditorPanel.Arrange m) {
        arrangeType = m;
        autoArrangeButton.doClick();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        topToolBar = new javax.swing.JToolBar();
        handToggleButton = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        arrowToggleButton = new javax.swing.JToggleButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        insertButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        autoArrangeButton = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        zoomOutButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        zoomInButton = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(10, 32767));
        finishButton = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        mainScroll = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GraphEditor");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(300, 200));
        setModal(true);

        topToolBar.setFloatable(false);
        topToolBar.setRollover(true);
        topToolBar.setMinimumSize(new java.awt.Dimension(100, 25));

        buttonGroup1.add(handToggleButton);
        handToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/cursor_hand.png"))); // NOI18N
        handToggleButton.setSelected(true);
        handToggleButton.setText("Hand");
        handToggleButton.setFocusable(false);
        handToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        handToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        handToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handToggleButtonActionPerformed(evt);
            }
        });
        topToolBar.add(handToggleButton);
        topToolBar.add(filler1);

        buttonGroup1.add(arrowToggleButton);
        arrowToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/right_arrow.png"))); // NOI18N
        arrowToggleButton.setText("Arrow");
        arrowToggleButton.setFocusable(false);
        arrowToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        arrowToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arrowToggleButtonActionPerformed(evt);
            }
        });
        topToolBar.add(arrowToggleButton);
        topToolBar.add(filler2);

        insertButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/playback_rec.png"))); // NOI18N
        insertButton.setText("Insert Block");
        insertButton.setFocusable(false);
        insertButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        insertButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        insertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });
        topToolBar.add(insertButton);
        topToolBar.add(filler3);

        autoArrangeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/cog.png"))); // NOI18N
        autoArrangeButton.setText("Auto Arrange");
        autoArrangeButton.setToolTipText("");
        autoArrangeButton.setFocusable(false);
        autoArrangeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        autoArrangeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        autoArrangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoArrangeButtonActionPerformed(evt);
            }
        });
        topToolBar.add(autoArrangeButton);
        topToolBar.add(filler6);

        zoomOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/zoom-out.png"))); // NOI18N
        zoomOutButton.setText("Zoom Out");
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        topToolBar.add(zoomOutButton);
        topToolBar.add(filler4);

        zoomInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/zoom-in.png"))); // NOI18N
        zoomInButton.setText("Zoom In");
        zoomInButton.setHideActionText(true);
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        topToolBar.add(zoomInButton);
        topToolBar.add(filler7);

        finishButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        finishButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/chaosdragon/graphtest/gui/icons/checkmark.png"))); // NOI18N
        finishButton.setText("Finish");
        finishButton.setFocusable(false);
        finishButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        finishButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        finishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishButtonActionPerformed(evt);
            }
        });
        topToolBar.add(finishButton);
        topToolBar.add(filler5);

        getContentPane().add(topToolBar, java.awt.BorderLayout.NORTH);

        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 9));
        mainPanel.setMaximumSize(new java.awt.Dimension(550, 550));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 847, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        mainScroll.setViewportView(mainPanel);

        getContentPane().add(mainScroll, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void handToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handToggleButtonActionPerformed
        x.setMode(GraphEditorPanel.Mode.HAND);
    }//GEN-LAST:event_handToggleButtonActionPerformed

    private void arrowToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrowToggleButtonActionPerformed
        x.setMode(GraphEditorPanel.Mode.ARROW);
    }//GEN-LAST:event_arrowToggleButtonActionPerformed

    private void autoArrangeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoArrangeButtonActionPerformed
        handToggleButton.doClick();
                
        x.autoArrange(arrangeType);
    }//GEN-LAST:event_autoArrangeButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        x.zoom(-1);        
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        x.zoom(+1);
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertButtonActionPerformed
         
        x.addVertice();
        handToggleButton.doClick();
                
    }//GEN-LAST:event_insertButtonActionPerformed

    private void finishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishButtonActionPerformed
    
        int validationResult = x.validateMatrix();
        
        if (validationResult==0) {
         x.updateMatrix();               
        this.dispose();
    } else {
        
        switch (validationResult) {
            
            case -1:                 
                JOptionPane.showMessageDialog(this, "Two vertices can't have the same names.","Warning",JOptionPane.WARNING_MESSAGE);
                break;        
            
        }
        
    }
    }//GEN-LAST:event_finishButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(GraphEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraphEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraphEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GraphEditor dialog = new GraphEditor(new javax.swing.JFrame(), true,null, false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton arrowToggleButton;
    private javax.swing.JButton autoArrangeButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JButton finishButton;
    private javax.swing.JToggleButton handToggleButton;
    private javax.swing.JButton insertButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScroll;
    private javax.swing.JToolBar topToolBar;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    // End of variables declaration//GEN-END:variables
}
