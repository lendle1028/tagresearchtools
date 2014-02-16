/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.tools.utils.DOMTreeUtils;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.w3c.dom.Node;

/**
 *
 * @author lendle
 */
public class DOMTreeModel implements TreeModel{
    private Node rootNode=null;

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }
    
    @Override
    public Object getRoot() {
        return this.rootNode;
    }

    @Override
    public Object getChild(Object o, int i) {
        Node node=(Node) o;
        return node.getChildNodes().item(i);
    }

    @Override
    public int getChildCount(Object o) {
        Node node=(Node) o;
        if(node.getChildNodes()!=null){
            return node.getChildNodes().getLength();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isLeaf(Object o) {
        Node node=(Node) o;
        return !node.hasChildNodes();
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o) {
        
    }

    @Override
    public int getIndexOfChild(Object o, Object o1) {
        Node node=(Node) o;
        for(int i=0; i<node.getChildNodes().getLength(); i++){
            if(node.getChildNodes().item(i)==o1){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener tl) {
        
    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl) {
        
    }
    
    public TreePath node2Path(Node node){
        return DOMTreeUtils.node2Path(node);
    }
    
    public String treePath2Xpath(TreePath path){
        return DOMTreeUtils.treePath2Xpath(path);
    }
}
