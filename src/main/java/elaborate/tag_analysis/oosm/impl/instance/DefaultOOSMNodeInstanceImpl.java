/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lendle
 */
public class DefaultOOSMNodeInstanceImpl implements OOSMNodeInstance{
    private String id="-1";
    private static int lastId=0;
    private OOSMConstruct definition=null;
    private OOSMNodeInstance parent=null;
    private List<OOSMNodeInstance> childNodes=new ArrayList<OOSMNodeInstance>();

    public DefaultOOSMNodeInstanceImpl() {
        this.id=""+lastId++;
    }
    
    public OOSMConstruct getDefinition() {
        return definition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public void setDefinition(OOSMConstruct definition) {
        this.definition = definition;
    }

    public OOSMNodeInstance getParent() {
        return parent;
    }

    public void setParent(OOSMNodeInstance parent) {
        this.parent = parent;
    }

    public List<OOSMNodeInstance> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<OOSMNodeInstance> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public int getChildCount() {
        return this.childNodes.size();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultOOSMNodeInstanceImpl other = (DefaultOOSMNodeInstanceImpl) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
}
