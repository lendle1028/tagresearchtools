/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.builder;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMRule;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author DELL
 */
public class OOSMTreeModel implements TreeModel{
    private OOSM oosm=null;

    public OOSMTreeModel(OOSM oosm) {
        this.oosm=oosm;
    }

    public OOSMTreeModel() {
    }

    public OOSM getOosm() {
        return oosm;
    }

    public void setOosm(OOSM oosm) {
        this.oosm = oosm;
    }
    
    @Override
    public Object getRoot() {
        return this.oosm;
    }

    @Override
    public Object getChild(Object o, int i) {
        if(o instanceof OOSM){
            List<OOSMRule> rules=this.oosm.getRules();
            return rules.get(i);
        }else if(o instanceof OOSMRule){
            OOSMRule rule=(OOSMRule) o;
            return rule.getConstructs().get(i);
        }else{
            return null;
        }
    }

    @Override
    public int getChildCount(Object o) {
        if(o instanceof OOSM){
            List<OOSMRule> rules=this.oosm.getRules();
            return rules.size();
        }else if(o instanceof OOSMRule){
            OOSMRule rule=(OOSMRule) o;
            return rule.getConstructs().size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isLeaf(Object o) {
        if(o instanceof OOSM){
            List<OOSMRule> rules=this.oosm.getRules();
            return rules.size()>0;
        }else if(o instanceof OOSMRule){
            OOSMRule rule=(OOSMRule) o;
            return rule.getConstructs().size()>0;
        }else{
            return true;
        }
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIndexOfChild(Object o, Object o1) {
        if(o instanceof OOSM){
            List<OOSMRule> rules=this.oosm.getRules();
            return rules.indexOf(o1);
        }else if(o instanceof OOSMRule){
            OOSMRule rule=(OOSMRule) o;
            return rule.getConstructs().indexOf(o1);
        }else{
            return -1;
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener tl) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
