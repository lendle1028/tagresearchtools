/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.OOSMRule;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

/**
 *
 * @author LENDLE
 */
public class DefaultOOSMImpl extends DefaultOOSMConstructImpl implements OOSM{
    protected OOSMElement rootElement=null;
    protected List<OOSMRule> rules=new ArrayList<OOSMRule>();

    public OOSMElement getRootElement() {
        return rootElement;
    }

    public void setRootElement(OOSMElement rootElement) {
        this.rootElement = rootElement;
    }

    public List<OOSMRule> getRules() {
        return rules;
    }

    @Override
    public OOSMElement createElement(QName name, String description) {
        return new DefaultOOSMElementImpl(name, description);
    }

    @Override
    public OOSMElementList createElementList(QName name, String description) {
        return new DefaultOOSMElementListImpl(name, description);
    }

    @Override
    public OOSMRule createRule(OOSMElement headingElement) {
        return new DefaultOOSMRuleImpl(headingElement);
    }
}
