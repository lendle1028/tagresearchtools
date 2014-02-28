/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.impl.instance.DefaultOOSMInstanceModelSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import elaborate.tag_analysis.oosm.tools.utils.DOMTreeUtils;
import elaborate.tag_analysis.oosm.tools.utils.SwingUtils;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.TreePath;
import org.w3c.dom.Node;

/**
 * OOSMMapper is an application for creating bindings between an OOSM schema and
 * a target document.
 *
 * @author lendle
 */
public class OOSMMapper extends javax.swing.JFrame implements OOSMMapperPopupMenuListener {

    private OOSMMapperApplication application = new OOSMMapperApplication();
    private DOMNodeTreeCellRenderer dOMNodeTreeCellRenderer = null;
    private OOSMMapperPopupMenu oosmMapperPopupMenu = new OOSMMapperPopupMenu();
    private JFileChooser oosmFileChooser = new JFileChooser(".");
    private JFileChooser projectFileChooser = new JFileChooser(".") {

        @Override
        public boolean accept(File file) {
            return file.isDirectory() && new File(file, ".project").exists();
        }
        //a customized file chooser that only displays project folders

    };

    /**
     * Creates new form OOSMMapper
     */
    public OOSMMapper() {
        initComponents();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "OOSM files", "oosm");
        oosmFileChooser.setFileFilter(filter);
        projectFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.oosmMapperPopupMenu.addOOSMMapperPopupMenuListener(this);
        OOSMNodeInstanceTreeModel model = new OOSMNodeInstanceTreeModel();
        this.treeOOSM.setModel(model);

        //load sample data
        try {
            //load schema and create a sample instance
            //this.application.createNewInstance(new File("test.oosm"));
            //renderSchemaTree();
            //load document
//            this.application.loadDocument(new File("test.htm").toURI().toURL());
//            DOMTreeModel domTreeModel = new DOMTreeModel();
//            domTreeModel.setRootNode(this.application.getDoc().getDocumentElement());
//            this.treeDOM.setModel(domTreeModel);
//            this.treeDOM.setCellRenderer(new DOMNodeTreeCellRenderer());
//            this.treeDOM.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
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

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeOOSM = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        treeDOM = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        txStatus = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuFileExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuCreateBinding = new javax.swing.JMenuItem();
        menuBindingResult = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 500, 500));
        setMinimumSize(new java.awt.Dimension(466, 200));

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setResizeWeight(0.5);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeOOSM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeOOSM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeOOSMMouseClicked(evt);
            }
        });
        treeOOSM.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeOOSMValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeOOSM);

        jSplitPane1.setLeftComponent(jScrollPane1);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeDOM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeDOM.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeDOMValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(treeDOM);

        jSplitPane1.setRightComponent(jScrollPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        txStatus.setEditable(false);
        jPanel1.add(txStatus, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("File");

        menuFileNew.setText("New");
        menuFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileNew);

        menuFileOpen.setText("Open");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpenActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileOpen);

        menuFileSave.setText("Save");
        menuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileSaveActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileSave);
        jMenu1.add(jSeparator1);

        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        menuCreateBinding.setText("Create Binding");
        menuCreateBinding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCreateBindingActionPerformed(evt);
            }
        });
        jMenu2.add(menuCreateBinding);

        menuBindingResult.setText("Show Result");
        menuBindingResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBindingResultActionPerformed(evt);
            }
        });
        jMenu2.add(menuBindingResult);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treeOOSMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeOOSMMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == MouseEvent.BUTTON3) {
            this.treeOOSM.setSelectionPath(this.treeOOSM.getPathForLocation(evt.getX(), evt.getY()));
            OOSMNodeInstance node = (OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
            if (!(node.getDefinition() instanceof OOSMElementList)) {
                this.oosmMapperPopupMenu.getMenuInsertAfter().setEnabled(false);
                this.oosmMapperPopupMenu.getMenuInsertBefore().setEnabled(false);
            } else {
                this.oosmMapperPopupMenu.getMenuInsertAfter().setEnabled(true);
                this.oosmMapperPopupMenu.getMenuInsertBefore().setEnabled(true);
            }
            this.oosmMapperPopupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_treeOOSMMouseClicked

    private void treeDOMValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeDOMValueChanged
        // TODO add your handling code here:
        DOMTreeModel model = (DOMTreeModel) this.treeDOM.getModel();
        if (this.treeDOM.getSelectionPath() == null) {
            this.txStatus.setText("");
        } else {
            String xpath = model.treePath2Xpath(this.treeDOM.getSelectionPath());
            this.txStatus.setText(xpath);
//            XPath xpathObject=XPathFactory.newInstance().newXPath();
//            try {
//                Node xpathNode=(Node) xpathObject.evaluate(xpath, model.getRootNode(), XPathConstants.NODE);
//            } catch (XPathExpressionException ex) {
//                Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }//GEN-LAST:event_treeDOMValueChanged

    private void menuCreateBindingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCreateBindingActionPerformed
        // TODO add your handling code here:
        BindingDialog dlg = new BindingDialog(this, true);
        dlg.setLocationRelativeTo(this);
        if (this.treeOOSM.getSelectionPath() != null) {
            OOSMNodeInstance nodeInstance = (OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
            if(!(nodeInstance.getDefinition() instanceof OOSMElement)){
                //only create bindings to elements
                return;
            }
            dlg.setOOSMNodeInstance(nodeInstance);
        }
        if (this.treeDOM.getSelectionPath() != null) {
            DOMTreeModel model = (DOMTreeModel) this.treeDOM.getModel();
            String xpath = model.treePath2Xpath(this.treeDOM.getSelectionPath());
            dlg.setTarget(xpath);
        }
        dlg.setVisible(true);
        if (dlg.isOk()) {
            //replace the binding in case it already exists
            this.application.getInstance().removeBinding(dlg.getBinding());
            this.application.getInstance().addBinding(dlg.getBinding());
            SwingUtils.repaint(this.treeOOSM);
            SwingUtils.repaint(this.treeDOM);
        }
    }//GEN-LAST:event_menuCreateBindingActionPerformed

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_menuFileExitActionPerformed

    private void menuFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewActionPerformed
        // TODO add your handling code here:
        NewProjectDialog dlg = new NewProjectDialog(this, true);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            try {
                ProjectConfiguration conf = dlg.getProjectConfiguration();
                openProject(conf);
            } catch (MalformedURLException ex) {
                Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        if (oosmFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            try {
//                this.application.createNewInstance(oosmFileChooser.getSelectedFile());
//                this.renderSchemaTree();
//            } catch (Exception ex) {
//                Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
    }//GEN-LAST:event_menuFileNewActionPerformed

    private void openProject(ProjectConfiguration conf) {
        try {
            this.application.open(conf);
            this.renderSchemaTree();
            this.renderDocumentTree();
        } catch (Exception ex) {
            Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
        // TODO add your handling code here:
        if (projectFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File projectFolder = projectFileChooser.getSelectedFile();
                File projectFile = new File(projectFolder, ".project");
                ProjectConfiguration conf = ProjectConfiguration.load(projectFile);
                openProject(conf);
            } catch (IOException ex) {
                Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuFileOpenActionPerformed

    private void menuFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileSaveActionPerformed
        try {
            // TODO add your handling code here:
            this.application.save();
        } catch (Exception ex) {
            Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuFileSaveActionPerformed

    private void treeOOSMValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeOOSMValueChanged
        // TODO add your handling code here:
        OOSMNodeInstance node = (OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
        this.dOMNodeTreeCellRenderer.setSelectedInstanceNode(node);
        if (this.application.getInstance().getBindings(node) != null && this.application.getInstance().getBindings(node).isEmpty() == false) {
            try {
                //make corresponding dom node visible
                List<Node> correspondingNodes=this.application.getCorrespondingNodes(node);
                if(correspondingNodes.isEmpty()==false){
                    //convert to TreePath and make the path visible
                    for(Node domNode : correspondingNodes){
                        TreePath path=DOMTreeUtils.node2Path(domNode);
                        this.treeDOM.makeVisible(path);
                        this.treeDOM.scrollPathToVisible(path);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        SwingUtils.repaint(this.treeDOM);
    }//GEN-LAST:event_treeOOSMValueChanged

    private void menuBindingResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBindingResultActionPerformed
        try {
            // TODO add your handling code here:
            ShowResultDialog dlg=new ShowResultDialog(this, false);
            dlg.setLocationRelativeTo(this);
            dlg.setSize(800, 600);
            //System.out.println(this.application.exportEvaluatedBindingResult2HTML());
            dlg.setResultValue(this.application.exportEvaluatedBindingResult2HTML());
            dlg.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuBindingResultActionPerformed

    private void renderSchemaTree() {
        OOSMNodeInstance root = this.application.getInstance().getInstanceTree();
        OOSMNodeInstanceTreeModel model = (OOSMNodeInstanceTreeModel) this.treeOOSM.getModel();
        model.setRoot(root);
        this.treeOOSM.setCellRenderer(new OOSMMapperTreeCellRenderer(this.application.getInstance()));
        this.treeOOSM.updateUI();
    }

    private void renderDocumentTree() {
        DOMTreeModel domTreeModel = new DOMTreeModel();
        domTreeModel.setRootNode(this.application.getDoc().getDocumentElement());
        this.treeDOM.setModel(domTreeModel);
        dOMNodeTreeCellRenderer = new DOMNodeTreeCellRenderer(this.application.getInstance());
        this.treeDOM.setCellRenderer(dOMNodeTreeCellRenderer);
        this.treeDOM.updateUI();
    }

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
            java.util.logging.Logger.getLogger(OOSMMapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OOSMMapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OOSMMapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OOSMMapper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OOSMMapper mapper = new OOSMMapper();
                mapper.setLocationRelativeTo(null);
                mapper.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenuItem menuBindingResult;
    private javax.swing.JMenuItem menuCreateBinding;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JMenuItem menuFileSave;
    private javax.swing.JTree treeDOM;
    private javax.swing.JTree treeOOSM;
    private javax.swing.JTextField txStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertBeforeClicked() {
        OOSMNodeInstance node = (OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
        OOSMNodeInstance parentNode = node.getParent();
        OOSMNodeInstance newNode = DefaultOOSMInstanceModelSerializer.generateTree(this.application.getSchema(), node.getDefinition(), parentNode);
        int index = parentNode.getChildNodes().indexOf(node);
        parentNode.getChildNodes().add(index, newNode);
        this.treeOOSM.updateUI();
    }

    @Override
    public void insertAfterClicked() {
        OOSMNodeInstance node = (OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
        OOSMNodeInstance parentNode = node.getParent();
        OOSMNodeInstance newNode = DefaultOOSMInstanceModelSerializer.generateTree(this.application.getSchema(), node.getDefinition(), parentNode);
        int index = parentNode.getChildNodes().indexOf(node);
        if (index == parentNode.getChildCount() - 1) {
            //the selected target is the last node
            parentNode.getChildNodes().add(newNode);
        } else {
            parentNode.getChildNodes().add(index + 1, newNode);
        }
        this.treeOOSM.updateUI();
    }
}
