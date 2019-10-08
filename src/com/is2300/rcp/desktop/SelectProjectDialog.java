/*
 * Copyright (C) 2019 Integrity Solutions
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.is2300.rcp.desktop;

import com.is2300.rcp.StartPrinting;
import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

/**
 *
 * @author Sean Carrick &lt;sean at carricktrucking dot com&gt;
 */
public class SelectProjectDialog extends javax.swing.JDialog {
    public String projectName;
    public String lastPrinted;
    public String language;
    public boolean selected;
    
    private Map<String, String> projectPaths;
    
    /**
     * Creates new form SelectProjectDialog
     */
    public SelectProjectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        projectPaths = new HashMap<>();
        
        this.projectName = "[Type or Select Project Name]";
        
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        int left = (scrSize.width - this.getWidth() ) / 2;
        int top = (scrSize.height - this.getHeight() ) / 2;
        this.setLocation(left, top);
        
        this.getRootPane().setDefaultButton(btnSelect);
        
        loadProjects();
    }
    
    private void loadProjects() {
        lstProjects.add("[Type or Select Project Name]");
        File projectsDir = new File(StartPrinting.PROPS.getProperty(
                "project.home"));
        
        if ( projectsDir.listFiles().length > 0) {
            for ( File project : projectsDir.listFiles() ) {
                lstProjects.add(project.getName().replace("_", " "));
                
                try (BufferedReader in = new BufferedReader(
                        new FileReader(project))) {
                    String lastDate = in.readLine();
                    String projectPath = in.readLine();
                    this.language = in.readLine();
                    
                    in.close();
                    
                    StartPrinting.PROPS.setProperty(project.getName(), 
                            lastDate);
                    
                    projectPaths.put(project.getName(), projectPath);
                } catch ( IOException ex ) {
                    System.err.println(ex.getMessage());
                    ex.printStackTrace(System.err);
                }
            }
        }
    }
    
    public String getProjectPath(String projectName) {
        return projectPaths.get(projectName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancel = new JButton();
        btnSelect = new JButton();
        lstProjects = new List();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        btnCancel.setIcon(new ImageIcon(getClass().getResource("/com/is2300/rcp/desktop/Cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CancelClicked(evt);
            }
        });

        btnSelect.setIcon(new ImageIcon(getClass().getResource("/com/is2300/rcp/desktop/apply.png"))); // NOI18N
        btnSelect.setText("Select");
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SelectClicked(evt);
            }
        });

        lstProjects.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                PropertySelectionChanged(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 328, Short.MAX_VALUE)
                        .addComponent(btnSelect)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addComponent(lstProjects, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lstProjects, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSelect))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PropertySelectionChanged(ItemEvent evt) {//GEN-FIRST:event_PropertySelectionChanged
        this.projectName = lstProjects.getSelectedItem();
    }//GEN-LAST:event_PropertySelectionChanged

    private void CancelClicked(ActionEvent evt) {//GEN-FIRST:event_CancelClicked
        this.selected = false;
        this.setVisible(false);
    }//GEN-LAST:event_CancelClicked

    private void SelectClicked(ActionEvent evt) {//GEN-FIRST:event_SelectClicked
        this.selected = true;
        this.setVisible(false);
    }//GEN-LAST:event_SelectClicked

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
            java.util.logging.Logger.getLogger(SelectProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectProjectDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SelectProjectDialog dialog = new SelectProjectDialog(new javax.swing.JFrame(), true);
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
    private JButton btnCancel;
    private JButton btnSelect;
    private List lstProjects;
    // End of variables declaration//GEN-END:variables
}
