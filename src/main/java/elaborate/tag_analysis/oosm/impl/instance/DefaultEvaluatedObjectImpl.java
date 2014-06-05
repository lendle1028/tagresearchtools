/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.instance;

import com.google.gson.Gson;
import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.impl.gson.GsonFactory;
import elaborate.tag_analysis.oosm.instance.binding.EvaluatedObject;
import elaborate.tag_analysis.oosm.tools.utils.DOMTreeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;

/**
 * the default implementation of EvaluatedObject
 * assumes all values are DOM nodes
 * @author DELL
 */
public class DefaultEvaluatedObjectImpl implements EvaluatedObject{
    private OOSMConstruct root=null;
    private Map<OOSMConstruct, List<EvaluatedObject>> properties=new HashMap<>();
    private List rootValue=null;

    public OOSMConstruct getRoot() {
        return root;
    }

    public void setRoot(OOSMConstruct root) {
        this.root = root;
    }

    public Map<OOSMConstruct, List<EvaluatedObject>> getProperties() {
        return properties;
    }

    public void setProperties(Map<OOSMConstruct, List<EvaluatedObject>> properties) {
        this.properties = properties;
    }

    @Override
    public List<EvaluatedObject> getProperty(OOSMConstruct name) {
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
    
    public void addValue(OOSMConstruct name, EvaluatedObject value){
        List<EvaluatedObject> values=this.properties.get(name);
        if(values==null){
            values=new ArrayList<EvaluatedObject>();
            this.properties.put(name, values);
        }
        values.add(value);
    }

    @Override
    public String convert2JSON() {
        Gson gson=GsonFactory.createGson();
        Map map=new HashMap();
        map.put("__construct__", this.root.getName().toString());
        List retRootValues=new ArrayList();
        for(int i=0; this.rootValue!=null && i<this.rootValue.size(); i++){
            retRootValues.add(DOMTreeUtils.node2Text((Node)this.rootValue.get(i)));
        }
        map.put("__rootValue__", retRootValues);
        //Map properties=new HashMap();
        //map.put("properties", properties);
        for(OOSMConstruct construct : this.properties.keySet()){
            List objectValues=new ArrayList();
            List<EvaluatedObject> evaluatedChildObjects=this.properties.get(construct);
            for(EvaluatedObject evaluatedChildObject : evaluatedChildObjects){
                //System.out.println(evaluatedChildObject.convert2JSON());
                objectValues.add(evaluatedChildObject.convert2JSON());
                //map.put(construct.getName().toString(), evaluatedChildObject.convert2JSON());
            }
           map.put(construct.getName().toString(), objectValues);
        }
        
        return gson.toJson(map);
    }
}
