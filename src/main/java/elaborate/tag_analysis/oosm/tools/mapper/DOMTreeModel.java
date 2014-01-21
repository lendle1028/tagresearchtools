/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.mapper;

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
    
    public String treePath2Xpath(TreePath path){
        StringBuffer buffer=new StringBuffer();
        Object [] elements=path.getPath();
        for(int i=0; i<elements.length; i++){
            Node node=(Node) elements[i];
            if(node.getNodeType()==Node.TEXT_NODE){
                buffer.append("/text()");
            }else if(node.getNodeType()==Node.COMMENT_NODE){
                buffer.append("/comment()");
            }else{
                buffer.append("/").append(node.getNodeName());
            }
            //calculate the index part
            Node parentNode=node.getParentNode();
            if(parentNode!=null){
                int index=1;
                Node sibling=node.getPreviousSibling();
                while(sibling!=null){
                    if(sibling.getNodeName().equals(node.getNodeName())){
                        index++;
                    }
                    sibling=sibling.getPreviousSibling();
                }
                buffer.append("[").append(index).append("]");
            }
        }
        return buffer.toString();
    }
}
