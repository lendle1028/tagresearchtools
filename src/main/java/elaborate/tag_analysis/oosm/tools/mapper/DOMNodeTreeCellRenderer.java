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
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import org.w3c.dom.Node;

/**
 *
 * @author lendle
 */
public class DOMNodeTreeCellRenderer extends DefaultTreeCellRenderer {

    private OOSMInstanceModel instanceModel = null;
    private OOSMNodeInstance selectedInstanceNode = null;

    public DOMNodeTreeCellRenderer() {
    }

    public DOMNodeTreeCellRenderer(OOSMInstanceModel instanceModel) {
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
            for (Binding binding : bindings) {
                //System.out.println(binding.getTarget()+" / "+xpath);
                if (this.selectedInstanceNode != null && binding.getInstanceNode()==this.selectedInstanceNode && xpath.equals(binding.getTarget())) {
                    //highlight the corresponding nodes
                    label.setOpaque(true);
                    label.setBackground(Color.YELLOW);
                }
                if (xpath.equals(binding.getTarget())) {
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
