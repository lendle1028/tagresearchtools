/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance.binding;

import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.util.Objects;

/**
 *
 * @author lendle
 */
public class Binding {
    private OOSMNodeInstance instanceNode=null;
    private String bindingDescription=null;
    private String target=null;

    public OOSMNodeInstance getInstanceNode() {
        return instanceNode;
    }

    public void setInstanceNode(OOSMNodeInstance instanceNode) {
        this.instanceNode = instanceNode;
    }

    public String getBindingDescription() {
        return bindingDescription;
    }

    public void setBindingDescription(String bindingDescription) {
        this.bindingDescription = bindingDescription;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.instanceNode);
        hash = 79 * hash + Objects.hashCode(this.target);
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
        final Binding other = (Binding) obj;
        if (!Objects.equals(this.instanceNode, other.instanceNode)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        return true;
    }
    
    
    
}
