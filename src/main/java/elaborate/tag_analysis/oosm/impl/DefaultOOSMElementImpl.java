/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSMElement;
import javax.xml.namespace.QName;

/**
 *
 * @author LENDLE
 */
public class DefaultOOSMElementImpl extends DefaultOOSMConstructImpl implements OOSMElement{

    public DefaultOOSMElementImpl() {
    }
    
    public DefaultOOSMElementImpl(QName name, String description) {
        super.setName(name);
        super.setDescription(description);
    }

}
