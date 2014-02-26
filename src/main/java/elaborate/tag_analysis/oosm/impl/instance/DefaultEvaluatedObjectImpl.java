/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.instance.binding.EvaluatedObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class DefaultEvaluatedObjectImpl implements EvaluatedObject{
    private OOSMElement root=null;
    private Map<OOSMElement, Object> properties=new HashMap<>();

    public OOSMElement getRoot() {
        return root;
    }

    public void setRoot(OOSMElement root) {
        this.root = root;
    }

    public Map<OOSMElement, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<OOSMElement, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Object getProperty(OOSMElement name) {
        return this.properties.get(name);
    }

    @Override
    public List<OOSMElement> getPropertyNames() {
        return new ArrayList<OOSMElement>(this.properties.keySet());
    }

    @Override
    public Object getRootValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
