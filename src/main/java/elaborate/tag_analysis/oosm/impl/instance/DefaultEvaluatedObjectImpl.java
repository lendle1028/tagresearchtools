/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.OOSMConstruct;
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
    private OOSMConstruct root=null;
    private Map<OOSMConstruct, EvaluatedObject> properties=new HashMap<>();
    private List rootValue=null;

    public OOSMConstruct getRoot() {
        return root;
    }

    public void setRoot(OOSMConstruct root) {
        this.root = root;
    }

    public Map<OOSMConstruct, EvaluatedObject> getProperties() {
        return properties;
    }

    public void setProperties(Map<OOSMConstruct, EvaluatedObject> properties) {
        this.properties = properties;
    }

    @Override
    public EvaluatedObject getProperty(OOSMConstruct name) {
        return this.properties.get(name);
    }

    @Override
    public List<OOSMConstruct> getPropertyNames() {
        return new ArrayList<OOSMConstruct>(this.properties.keySet());
    }

    @Override
    public List getRootValue() {
        return this.rootValue;
    }
    
    public void setRootValue(List list){
        this.rootValue=list;
    }
}
