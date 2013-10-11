/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMRule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENDLE
 */
public class DefaultOOSMRuleImpl implements OOSMRule {
    private OOSMElement headingElement=null;
    private List<OOSMConstruct> constructs=new ArrayList<OOSMConstruct>();

    public OOSMElement getHeadingElement() {
        return headingElement;
    }

    public void setHeadingElement(OOSMElement headingElement) {
        this.headingElement = headingElement;
    }

    public List<OOSMConstruct> getConstructs() {
        return constructs;
    }

    public void setConstructs(List<OOSMConstruct> constructs) {
        this.constructs = constructs;
    }
    
    
}
