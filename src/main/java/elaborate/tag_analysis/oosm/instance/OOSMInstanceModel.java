/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.instance;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.instance.binding.Binding;
import java.util.List;
import java.util.Map;

/**
 * T: data root type
 * @author lendle
 */
public interface OOSMInstanceModel<T> {
    /**
     * @return the instance tree in this model
     */
    public OOSMNodeInstance getInstanceTree();
    /**
     * Evaluate the given binding based on the given dataRoot
     * the default implementation assume the dataRoot to be
     * a w3c DOM node.
     * @param binding
     * @param dataRoot
     * @return
     * @throws Exception 
     */
    public Object evaluateBinding(Binding binding, T dataRoot) throws Exception;
    public List<Binding> getBindings(OOSMNodeInstance node);
    public List<Binding> getBindings();
    public Map<OOSMNodeInstance, List<Binding>> getBindingsMap();
    public void addBinding(Binding binding);
    public void removeBinding(Binding binding);
    /**
     * 
     * @return the corresponding OOSM schema
     */
    public OOSM getSchema();
}
