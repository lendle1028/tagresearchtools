/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class DefaultOOSMNodeInstanceImpl implements OOSMNodeInstance{
    private int id=-1;
    private static int lastId=0;
    private OOSMConstruct definition=null;
    private Object data=null;
    private OOSMNodeInstance parent=null;
    private List<OOSMNodeInstance> childNodes=new ArrayList<OOSMNodeInstance>();

    public DefaultOOSMNodeInstanceImpl() {
        this.id=lastId++;
    }
    
    public OOSMConstruct getDefinition() {
        return definition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public void setDefinition(OOSMConstruct definition) {
        this.definition = definition;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public OOSMNodeInstance getParent() {
        return parent;
    }

    public void setParent(OOSMNodeInstance parent) {
        this.parent = parent;
    }

    public List<OOSMNodeInstance> getChildNodes() {
        return childNodes;
    }

    @Override
    public int getChildCount() {
        return this.childNodes.size();
    }
}
