/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.OOSMRule;
import elaborate.tag_analysis.oosm.OOSMSerializer;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMSerializer;
import elaborate.tag_analysis.oosm.impl.instance.DefaultOOSMNodeInstanceImpl;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

/**
 *
 * @author lendle
 */
public class OOSMMapper extends javax.swing.JFrame implements OOSMMapperPopupMenuListener{
    private OOSMMapperPopupMenu oosmMapperPopupMenu=new OOSMMapperPopupMenu();
    private OOSM oosm=null;
    /**
     * Creates new form OOSMMapper
     */
    public OOSMMapper() {
        initComponents();
        this.oosmMapperPopupMenu.addOOSMMapperPopupMenuListener(this);
        this.treeOOSM.setCellRenderer(new OOSMMapperTreeCellRenderer());
        OOSMNodeInstanceTreeModel model = new OOSMNodeInstanceTreeModel();
        this.treeOOSM.setModel(model);
        OOSMSerializer serializer = new DefaultOOSMSerializer();
        try {
            FileReader input = new FileReader("test.oosm");
            oosm = serializer.createOOSM(input);
            OOSMNodeInstance root=this.generateTree(oosm, oosm.getRootElement(), null);
            model.setRoot(root);
            input.close();
            this.treeOOSM.updateUI();
            
            Tidy tidy=new Tidy();
            tidy.setXHTML(true);
            try(Reader reader=new InputStreamReader(new FileInputStream("test.htm"), "utf-8")){
                Document doc=tidy.parseDOM(reader, null);
                DOMTreeModel domTreeModel=new DOMTreeModel();
                domTreeModel.setRootNode(doc.getDocumentElement());
                this.treeDOM.setModel(domTreeModel);
                this.treeDOM.setCellRenderer(new DOMNodeTreeCellRenderer());
                this.treeDOM.updateUI();
            }catch(Exception e){throw e;}
            
            this.pack();
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
        jMenu2 = new javax.swing.JMenu();
        menuCreateBinding = new javax.swing.JMenuItem();

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
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        menuCreateBinding.setText("Create Binding");
        jMenu2.add(menuCreateBinding);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treeOOSMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeOOSMMouseClicked
        // TODO add your handling code here:
        if(evt.getButton()==MouseEvent.BUTTON3){
            this.treeOOSM.setSelectionPath(this.treeOOSM.getPathForLocation(evt.getX(), evt.getY()));
            OOSMNodeInstance node=(OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
            if(!(node.getDefinition() instanceof OOSMElementList)){
                this.oosmMapperPopupMenu.getMenuInsertAfter().setEnabled(false);
                this.oosmMapperPopupMenu.getMenuInsertBefore().setEnabled(false);
            }else{
                this.oosmMapperPopupMenu.getMenuInsertAfter().setEnabled(true);
                this.oosmMapperPopupMenu.getMenuInsertBefore().setEnabled(true);
            }
            this.oosmMapperPopupMenu.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_treeOOSMMouseClicked

    private void treeDOMValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeDOMValueChanged
        // TODO add your handling code here:
        DOMTreeModel model=(DOMTreeModel) this.treeDOM.getModel();
        if(this.treeDOM.getSelectionPath()==null){
            this.txStatus.setText("");
        }else{
            String xpath=model.treePath2Xpath(this.treeDOM.getSelectionPath());
            this.txStatus.setText(xpath);
            XPath xpathObject=XPathFactory.newInstance().newXPath();
            try {
                Node xpathNode=(Node) xpathObject.evaluate(xpath, model.getRootNode(), XPathConstants.NODE);
                System.out.println(xpathNode+":"+xpathNode.getNodeValue());
            } catch (XPathExpressionException ex) {
                Logger.getLogger(OOSMMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_treeDOMValueChanged

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
                new OOSMMapper().setVisible(true);
            }
        });
    }

    protected OOSMNodeInstance generateTree(OOSM model, OOSMConstruct currentConstruct, OOSMNodeInstance parent) {
        DefaultOOSMNodeInstanceImpl root = new DefaultOOSMNodeInstanceImpl();
        root.setParent(parent);
        root.setDefinition(currentConstruct);
        //root.setData(currentConstruct.getName());
        if (currentConstruct instanceof OOSMElement) {
            OOSMRule rule = null;

            for (OOSMRule _rule : model.getRules()) {
                if (_rule.getHeadingElement().equals(currentConstruct)) {
                    rule = _rule;
                    break;
                }
            }

            if (rule != null) {
                //generate tree based on the selected rule
                for (OOSMConstruct construct : rule.getConstructs()) {
                        root.getChildNodes().add(this.generateTree(model, construct, root));
                }
            }
        }else if (currentConstruct instanceof OOSMElementList){
            OOSMElementList elementList=(OOSMElementList) currentConstruct;
            for(OOSMConstruct subConstructs : elementList.getElements()){
                root.getChildNodes().add(this.generateTree(model, subConstructs, root));
            }
        }else{
            return null;
        }
        return root;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenuItem menuCreateBinding;
    private javax.swing.JTree treeDOM;
    private javax.swing.JTree treeOOSM;
    private javax.swing.JTextField txStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertBeforeClicked() {
        OOSMNodeInstance node=(OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
        OOSMNodeInstance parentNode=node.getParent();
        OOSMNodeInstance newNode=this.generateTree(oosm, node.getDefinition(), parentNode);
        int index=parentNode.getChildNodes().indexOf(node);
        parentNode.getChildNodes().add(index, newNode);
        this.treeOOSM.updateUI();
    }

    @Override
    public void insertAfterClicked() {
        OOSMNodeInstance node=(OOSMNodeInstance) this.treeOOSM.getSelectionPath().getLastPathComponent();
        OOSMNodeInstance parentNode=node.getParent();
        OOSMNodeInstance newNode=this.generateTree(oosm, node.getDefinition(), parentNode);
        int index=parentNode.getChildNodes().indexOf(node);
        if(index==parentNode.getChildCount()-1){
            //the selected target is the last node
            parentNode.getChildNodes().add(newNode);
        }
        else{
            parentNode.getChildNodes().add(index+1, newNode);
        }
        this.treeOOSM.updateUI();
    }
}