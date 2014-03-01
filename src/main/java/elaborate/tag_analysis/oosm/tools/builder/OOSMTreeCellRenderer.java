/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.builder;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMRule;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author LENDLE
 */
public class OOSMTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree jtree, Object o, boolean bln, boolean bln1, boolean bln2, int i, boolean bln3) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(jtree, o, bln, bln1, bln2, i, bln3); //To change body of generated methods, choose Tools | Templates.
        try {
            if (o instanceof OOSMConstruct) {
                OOSMConstruct construct = (OOSMConstruct) o;
                label.setText(construct.getName().toString());
            } else if (o instanceof OOSMRule) {
                OOSMRule rule = (OOSMRule) o;
                label.setText("Rule: " + rule.getHeadingElement().getName().toString());
            }
        }catch(Exception e){}
        return label;
    }

}
