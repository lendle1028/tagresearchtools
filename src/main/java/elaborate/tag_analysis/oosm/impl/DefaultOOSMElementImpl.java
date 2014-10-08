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
    protected int type=OOSMElement.TYPE_STRING;
    public DefaultOOSMElementImpl() {
    }
    
    public DefaultOOSMElementImpl(QName name, String description) {
        super.setName(name);
        super.setDescription(description);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof OOSMElement){
            return this.getName().equals(( (OOSMElement)obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
}
