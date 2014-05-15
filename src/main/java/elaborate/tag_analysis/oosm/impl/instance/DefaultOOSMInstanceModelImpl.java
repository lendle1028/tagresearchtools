/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import elaborate.tag_analysis.oosm.instance.binding.Binding;
import elaborate.tag_analysis.oosm.instance.binding.EvaluatedObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
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
    private OOSM schema = null;

    public OOSM getSchema() {
        return schema;
    }

    public void setSchema(OOSM schema) {
        this.schema = schema;
    }

    public OOSMNodeInstance getInstanceTree() {
        return instanceTree;
    }

    public void setInstanceTree(OOSMNodeInstance instanceTree) {
        this.instanceTree = instanceTree;
    }

    @Override
    public Object evaluateBinding(Binding binding, Node dataRoot) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expression=xpath.compile(binding.getExpression());
        Object ret=null;
        try{
            ret=expression.evaluate(dataRoot, XPathConstants.NODE);
        }catch(Exception e){
            //if cannot be evaluated as a Node
            ret=expression.evaluate(dataRoot, XPathConstants.STRING);
        }
        return ret;
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

    public void setBindingsMap(Map<OOSMNodeInstance, List<Binding>> bindingsMap) {
        this.bindings.clear();
        this.bindings.putAll(bindingsMap);
    }

    @Override
    public void addBinding(Binding binding) {
        List<Binding> bindings = this.getBindings(binding.getInstanceNode());
        if (bindings == null) {
            bindings = new ArrayList<Binding>();
            this.bindings.put(binding.getInstanceNode(), bindings);
        }
        bindings.add(binding);
    }

    @Override
    public void removeBinding(Binding binding) {
        List<Binding> bindings = this.getBindings(binding.getInstanceNode());
        if (bindings != null) {
            bindings.remove(binding);
        }
    }

    @Override
    public EvaluatedObject evaluateAllBindings(Node dataRoot) throws Exception {
        if (this.instanceTree == null) {
            return null;
        }
        EvaluatedObject obj=this.evaluateAllBindings(instanceTree, dataRoot);
        return obj;
    }
    
    private EvaluatedObject evaluateAllBindings(OOSMNodeInstance node, Node dataRoot) throws Exception{
        DefaultEvaluatedObjectImpl root = new DefaultEvaluatedObjectImpl();
        OOSMConstruct definition=node.getDefinition();
        root.setRoot(definition);
        List<Binding> bindings=this.getBindings(node);
        if(bindings!=null && bindings.isEmpty()==false){
            //System.out.println(bindings.size());
            //get root values: the values obtained by
            //evaluating bindings found on this node
            List values=new ArrayList();
            for(Binding binding : bindings){
                values.add(this.evaluateBinding(binding, dataRoot));
                //System.out.println(this.evaluateBinding(binding, dataRoot));
            }
            root.setRootValue(values);
        }
        List<OOSMNodeInstance> childNodes=node.getChildNodes();
        if(childNodes!=null && childNodes.isEmpty()==false){
            for(OOSMNodeInstance childNode : childNodes){
                EvaluatedObject childResult=this.evaluateAllBindings(childNode, dataRoot);
                if(childResult.getRootValue()==null && (childResult.getPropertyNames()==null || childResult.getPropertyNames().isEmpty())){
                    continue;//skip empty entry
                }
                //check for multiple instances of the same construct (OOSMElementList)
                root.addValue(childNode.getDefinition(), childResult);
            }
        }
        return root;
    }

    @Override
    public Object evaluateTargetNode(Binding binding, Node dataRoot) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expression=xpath.compile(binding.getTargetNode());
        Object ret=null;
        try{
            ret=expression.evaluate(dataRoot, XPathConstants.NODE);
        }catch(Exception e){
            //if cannot be evaluated as a Node
            ret=expression.evaluate(dataRoot, XPathConstants.STRING);
        }
        return ret;
    }
    
    
}
