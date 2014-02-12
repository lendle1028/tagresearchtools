/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm;

import java.util.List;
import javax.xml.namespace.QName;

/**
 * an Object-Oriented Schema Model instance
 * an OOSM has a root and a list of rules
 * OOSM ::=(ROOT_ELEMENT, (RULE)*)
 * RULE ::= (ELEMENT, {CONSTRUCT1, CONSTRUCT 2, ……, CONSTRUCT n})
 * CONSTRUCT ::= ARRAY(ELEMENT) | ELEMENT
 * @author LENDLE
 */
public interface OOSM extends OOSMConstruct{
    public OOSMElement getRootElement();
    public List<OOSMRule> getRules();
    
    public OOSMElement createElement(QName name, String description);
    public OOSMElementList createElementList(QName name, String description);
    public OOSMRule createRule(OOSMElement headingElement);
}
