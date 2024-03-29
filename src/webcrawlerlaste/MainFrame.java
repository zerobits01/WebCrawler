package webcrawlerlaste;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        this.setResizable(false);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (d.getWidth() - this.getWidth()) / 2, (int) (d.getHeight() - this.getHeight()) / 2);
        this.setTitle("Web Crawler");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public MainFrame(String url, int depth, String rootpath, MainFrame mf) {
        this.url = url;
        this.rootpath = rootpath;
        this.depth = depth;
        initComponents();
        this.setResizable(false);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (d.getWidth() - this.getWidth()) / 2, (int) (d.getHeight() - this.getHeight()) / 2);
        this.setTitle("Web Crawler");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Main main = new Main(url, rootpath , depth);
                        
                    } catch (IOException ex) {
                    }
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
            }

            FileSystemModel1 fm = new FileSystemModel1(rootpath);
            jTree1.setModel(fm);

            set_ui();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "connection problem");
            return;
        }
        mf.dispose();
    }

    public void set_ui() {
        try {

            // jtree
            // new JTree(new FileSystemModel(this.rootpath));
            model = (DefaultTableModel) downloadList_tab.getModel();
            String a[] = new String[3];

            for (int i = 0; i < Main.downloads.size(); i++) {
                try {
                    model.addRow(a);
                    int filesize = (int) Main.downloads.get(i).getFileSize();
                    downloadList_tab.setValueAt(Main.downloads.get(i).getFileName(), i, 0);
                    downloadList_tab.setValueAt(((filesize > 0) ? "" + filesize : "Error with link"), i, 1);
                    downloadList_tab.setValueAt((Main.downloads.get(i).getStatus() ? "Downloaded" : "Not Downloaded"), i, 2);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    for (int i = 0; i < Main.downloads.size(); i++) {
                        downloadList_tab.setValueAt((Main.downloads.get(i).getStatus() ? "Downloaded" : "Not Downloaded"), i, 2);
                    }
                }
            }, 0, 1000);

            System.out.println(allnodes.size());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        downloadList_tab = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        addUrl_item = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        downloadList_tab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File Name", "Size", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        downloadList_tab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadList_tabMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(downloadList_tab);

        jScrollPane2.setViewportView(jTree1);

        addUrl_item.setText("Add URL");
        addUrl_item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addUrl_itemMouseClicked(evt);
            }
        });
        jMenuBar1.add(addUrl_item);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addUrl_itemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUrl_itemMouseClicked
        // TODO add your handling code here:
        AddUrl add = new AddUrl(this);
        add.setVisible(true);
    }//GEN-LAST:event_addUrl_itemMouseClicked

    private void downloadList_tabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadList_tabMouseClicked
        // TODO add your handling code here:
        if (Main.downloads == null) {
            return;
        }
        Downloader dnld = Main.downloads.get(downloadList_tab.getSelectedRow());
        DownloadPage dp = new DownloadPage(dnld , this.jTree1 , this.rootpath);
        dp.setVisible(true);
    }//GEN-LAST:event_downloadList_tabMouseClicked

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
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });

    }

    private DefaultMutableTreeNode root1;
    private DefaultTreeModel treeModel;
    private JTree tree1;
    private String url;
    private String rootpath;
    private int depth;
    private Node root = null;
    private DefaultTableModel model;
    protected static ArrayList<Node> allnodes = new ArrayList<>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu addUrl_item;
    private javax.swing.JTable downloadList_tab;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
