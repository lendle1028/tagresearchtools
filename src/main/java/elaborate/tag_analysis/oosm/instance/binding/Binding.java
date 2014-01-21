/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance.binding;

import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;

/**
 *
 * @author lendle
 */
public class Binding {
    private OOSMNodeInstance instanceNode=null;
    private String bindingDescription=null;

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
    
}
