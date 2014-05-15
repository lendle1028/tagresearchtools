/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance.binding;

import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.util.Objects;

/**
 * A Binding describes the relationship between an OOSMNodeInstance node
 * and its target.
 * A targetNode is a string representation of how to locate a specific
 * node on the target document to be annotated by OOSM.
 * The default implementation assumes a targetNode to be an xpath expression.
 * @author lendle
 */
public class Binding {
    private OOSMNodeInstance instanceNode=null;
    private String bindingDescription=null;
    private String expression=null;
    private String targetNode=null;

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

    public String getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(String targetNode) {
        this.targetNode = targetNode;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.instanceNode);
        hash = 79 * hash + Objects.hashCode(this.expression);
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
        if (!Objects.equals(this.expression, other.expression)) {
            return false;
        }
        return true;
    }
    
    
    
}
