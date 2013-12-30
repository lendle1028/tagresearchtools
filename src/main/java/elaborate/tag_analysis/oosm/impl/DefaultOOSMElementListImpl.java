/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

/**
 *
 * @author DELL
 */
public class DefaultOOSMElementListImpl extends DefaultOOSMConstructImpl implements OOSMElementList{
    private List<OOSMElement> elements=new ArrayList<OOSMElement>();
    
    public DefaultOOSMElementListImpl(QName name, String description) {
        super.setName(name);
        super.setDescription(description);
    }

    public DefaultOOSMElementListImpl() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<OOSMElement> getElements() {
        return elements;
    }

    public void setElements(List<OOSMElement> elements) {
        this.elements = elements;
    }
    
    
    
}
