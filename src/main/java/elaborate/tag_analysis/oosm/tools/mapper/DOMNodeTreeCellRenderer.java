/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import elaborate.tag_analysis.oosm.instance.binding.Binding;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author lendle
 */
public class DOMNodeTreeCellRenderer extends DefaultTreeCellRenderer {

    private OOSMInstanceModel instanceModel = null;
    private OOSMNodeInstance selectedInstanceNode = null;
    private XPath xpath = null;

    public DOMNodeTreeCellRenderer() {
        xpath = XPathFactory.newInstance().newXPath();
    }

    public DOMNodeTreeCellRenderer(OOSMInstanceModel instanceModel) {
        this();
        this.instanceModel = instanceModel;
    }

    public void setSelectedInstanceNode(OOSMNodeInstance selectedInstanceNode) {
        this.selectedInstanceNode = selectedInstanceNode;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree jtree, Object o, boolean bln, boolean bln1, boolean bln2, int i, boolean bln3) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(jtree, o, bln, bln1, bln2, i, bln3); //To change body of generated methods, choose Tools | Templates.
        if (o instanceof Node) {
            Node node = (Node) o;
            if (node.getNodeType() == Node.TEXT_NODE) {
                label.setText(node.getNodeValue());
            } else {
                label.setText(node.getNodeName());
            }

            DOMTreeModel model = (DOMTreeModel) jtree.getModel();
            TreePath path = model.node2Path(node);
            String xpath = model.treePath2Xpath(path);
            List<Binding> bindings = instanceModel.getBindings();

            label.setOpaque(false);
            if (o != null && node.getNodeType()==Node.ELEMENT_NODE && this.xpath != null) {
                try {
                    Boolean textNodesList = (Boolean) this.xpath.evaluate("descendant-or-self::*[string-length(text())>0]", o, XPathConstants.BOOLEAN);
                    if (textNodesList != null && textNodesList.booleanValue()) {
                        Boolean script=(Boolean) this.xpath.evaluate("ancestor-or-self::*[local-name()='script']", o, XPathConstants.BOOLEAN);
                        if(script){
                            label.setForeground(Color.LIGHT_GRAY);
                        }else{
                            label.setForeground(Color.BLACK);
                        }
                    } else {
                        label.setForeground(Color.LIGHT_GRAY);
                    }
                } catch (XPathExpressionException ex) {
                    Logger.getLogger(DOMNodeTreeCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Binding binding : bindings) {
                //System.out.println(binding.getTarget()+" / "+xpath);
                if (this.selectedInstanceNode != null && binding.getInstanceNode() == this.selectedInstanceNode && xpath.equals(binding.getTargetNode())) {
                    if (jtree.getSelectionPath() == null) {
                        //automatically select the fist matched node
                        jtree.setSelectionPath(path);
                    }
                    //highlight the corresponding nodes
                    label.setOpaque(true);
                    label.setBackground(Color.YELLOW);
                }
                if (xpath.equals(binding.getExpression())) {
                    label.setForeground(new Color(0x00, 0x99, 0x00));
                    //label.setOpaque(true);
                    //label.setBackground(Color.GREEN);
                    break;
                }
            }
        }
        return label;
    }

}
