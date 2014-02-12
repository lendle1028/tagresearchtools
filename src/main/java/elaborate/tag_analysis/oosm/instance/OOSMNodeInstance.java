/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import java.util.List;

/**
 * An OOSMNodeInstance is a node in an OOSM instance tree.
 * An OOSM instance tree is a tree generated from OOSM schema
 * by traversing rules from the root element.
 * Note OOSMElements are repeatable.
 * @author lendle
 */
public interface OOSMNodeInstance {
    public String getId();
    /**
     * 
     * @return link to its definition
     */
    public OOSMConstruct getDefinition();
    /**
     * 
     * @return the data mapped by this node
     */
    public OOSMNodeInstance getParent();
    public List<OOSMNodeInstance> getChildNodes();
    public int getChildCount();
}
