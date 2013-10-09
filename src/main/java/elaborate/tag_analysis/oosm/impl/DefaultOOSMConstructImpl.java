/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import javax.xml.namespace.QName;

/**
 *
 * @author LENDLE
 */
public class DefaultOOSMConstructImpl implements OOSMConstruct{
    protected QName name=null;
    protected String description=null;

    public QName getName() {
        return name;
    }

    public void setName(QName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    
}
