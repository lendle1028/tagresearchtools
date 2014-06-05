/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.tools.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.TreePath;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/**
 *
 * @author lendle
 */
public class DOMTreeUtils {
    public static TreePath node2Path(Node node){
        List<Node> list=new ArrayList<>();
        list.add(node);
        Node parentNode=node.getParentNode();
        while(parentNode!=null && parentNode.getNodeType()!=Node.DOCUMENT_NODE){
            list.add(0, parentNode);
            parentNode=parentNode.getParentNode();
        }
        return new TreePath(list.toArray());
    }
    
    public static String treePath2Xpath(TreePath path){
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
    
    public static String node2Text(Node node){
        if(node.getNodeType()==Node.TEXT_NODE){
            return node.getNodeValue();
        }else if(node.getNodeType()==Node.ATTRIBUTE_NODE){
            return node.getNodeValue();
        }else{
            try {
                TransformerFactory tf=TransformerFactory.newInstance();
                Transformer t=tf.newTransformer();
                t.setOutputProperty("omit-xml-declaration", "yes");
                StringWriter output=new StringWriter();
                t.transform(new DOMSource(node), new StreamResult(output));
                return output.toString();
            } catch (Exception ex) {
                return null;
            }
        }
        
    }
}
