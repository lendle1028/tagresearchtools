/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.w3c.dom.Node;

/**
 *
 * @author lendle
 */
public class DOMNodeTreeCellRenderer extends DefaultTreeCellRenderer{
    private OOSMInstanceModel instanceModel=null;

    public DOMNodeTreeCellRenderer() {
    }

    public DOMNodeTreeCellRenderer(OOSMInstanceModel instanceModel) {
        this.instanceModel=instanceModel;
    }
    @Override
    public Component getTreeCellRendererComponent(JTree jtree, Object o, boolean bln, boolean bln1, boolean bln2, int i, boolean bln3) {
        JLabel label=(JLabel) super.getTreeCellRendererComponent(jtree, o, bln, bln1, bln2, i, bln3); //To change body of generated methods, choose Tools | Templates.
        if(o instanceof Node){
            Node node=(Node) o;
            if(node.getNodeType()==Node.TEXT_NODE){
                label.setText(node.getNodeValue());
            }else{
                label.setText(node.getNodeName());
            }
//            if(instanceModel!=null && instanceModel.getBindings(node)!=null){
//                label.setBackground(Color.GREEN);
//            }
        }
        return label;
    }
    
}
