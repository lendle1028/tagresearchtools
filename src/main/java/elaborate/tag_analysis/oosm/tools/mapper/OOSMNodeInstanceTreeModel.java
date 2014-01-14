/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author lendle
 */
public class OOSMNodeInstanceTreeModel implements TreeModel{
    private OOSMNodeInstance root=null;
    @Override
    public Object getRoot() {
        return root;
    }

    public void setRoot(OOSMNodeInstance root) {
        this.root = root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        OOSMNodeInstance node=(OOSMNodeInstance) parent;
        return node.getChildNodes().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        OOSMNodeInstance node=(OOSMNodeInstance) parent;
        return node.getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        OOSMNodeInstance _node=(OOSMNodeInstance) node;
        return _node.getChildCount()==0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        OOSMNodeInstance node=(OOSMNodeInstance) parent;
        int index=0;
        for(OOSMNodeInstance childNode : node.getChildNodes()){
            if(childNode==child){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }
    
}
