/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import elaborate.tag_analysis.oosm.instance.binding.Binding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

/**
 * bindingDescription must be an xpath returning a Node instance
 *
 * @author lendle
 */
public class DefaultOOSMInstanceModelImpl implements OOSMInstanceModel<Node> {

    private OOSMNodeInstance instanceTree = null;
    private Map<OOSMNodeInstance, List<Binding>> bindings = new HashMap<OOSMNodeInstance, List<Binding>>();

    public OOSMNodeInstance getInstanceTree() {
        return instanceTree;
    }

    public void setInstanceTree(OOSMNodeInstance instanceTree) {
        this.instanceTree = instanceTree;
    }

    @Override
    public Object evaluateBinding(Binding binding, Node dataRoot) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        return xpath.evaluate(binding.getBindingDescription(), dataRoot, XPathConstants.NODE);
    }

    @Override
    public List<Binding> getBindings(OOSMNodeInstance node) {
        return (List<Binding>) this.bindings.get(node);
    }

    @Override
    public List<Binding> getBindings() {
        List<Binding> allBindings = new ArrayList<Binding>();
        for (List<Binding> _bindings : this.bindings.values()) {
            allBindings.addAll(_bindings);
        }
        return allBindings;
    }

    @Override
    public Map<OOSMNodeInstance, List<Binding>> getBindingsMap() {
        return new HashMap<OOSMNodeInstance, List<Binding>>(this.bindings);
    }

}
