/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author LENDLE
 */
public class OOSMMapperTreeCellRenderer extends DefaultTreeCellRenderer {
    private OOSMInstanceModel instanceModel=null;

    public OOSMMapperTreeCellRenderer() {
    }

    public OOSMMapperTreeCellRenderer(OOSMInstanceModel instanceModel) {
        this.instanceModel=instanceModel;
    }
    
    
    
    @Override
    public Component getTreeCellRendererComponent(JTree jtree, Object o, boolean bln, boolean bln1, boolean bln2, int i, boolean bln3) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(jtree, o, bln, bln1, bln2, i, bln3); //To change body of generated methods, choose Tools | Templates.
        if (o instanceof OOSMNodeInstance) {
            OOSMNodeInstance node = (OOSMNodeInstance) o;
            if (node.getDefinition() instanceof OOSMElement) {
                label.setText(""+node.getDefinition().getName());
            } else if (node.getDefinition() instanceof OOSMElementList) {
                label.setText(""+node.getDefinition().getName());
            }
            if(instanceModel!=null && instanceModel.getBindings(node)!=null && instanceModel.getBindings(node).size()>0){
                label.setForeground(new Color(0x00, 0x99, 0x00));
                //label.setBackground(Color.GREEN);
                //label.setOpaque(true);
            }else{
                //label.setOpaque(false);
            }
        }
        return label;
    }

}
