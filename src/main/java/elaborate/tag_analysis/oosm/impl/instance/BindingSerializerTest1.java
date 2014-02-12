/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.instance;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.OOSMRule;
import elaborate.tag_analysis.oosm.OOSMSerializer;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModelSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import elaborate.tag_analysis.oosm.instance.binding.Binding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;

/**
 *
 * @author lendle
 */
public class BindingSerializerTest1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        DefaultOOSMInstanceModelImpl model=new DefaultOOSMInstanceModelImpl();
        FileReader input = new FileReader("test.oosm");
        OOSMSerializer serializer = new DefaultOOSMSerializer();
        OOSM    oosm = serializer.createOOSM(input);
        model.setSchema(oosm);
        OOSMNodeInstance root=generateTree(oosm, oosm.getRootElement(), null);
        model.setInstanceTree(root);
        System.out.println(root.getChildNodes().get(0).getChildNodes().get(0).getDefinition().getName());
        Binding binding1=new Binding();
        binding1.setInstanceNode(root.getChildNodes().get(0).getChildNodes().get(0));
        binding1.setTarget("//b");
        model.addBinding(binding1);
        
        Binding binding2=new Binding();
        binding2.setInstanceNode(root.getChildNodes().get(0).getChildNodes().get(1));
        binding2.setTarget("//c");
        model.addBinding(binding2);
        
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        OOSMInstanceModelSerializer instanceSerializer=new DefaultOOSMInstanceModelSerializer();
        instanceSerializer.save(model, buffer);
        buffer.close();
        
        ByteArrayInputStream inputBuffer=new ByteArrayInputStream(buffer.toByteArray());
        model=(DefaultOOSMInstanceModelImpl) instanceSerializer.createInstanceModel(inputBuffer);
        inputBuffer.close();
        
        instanceSerializer.save(model, System.out);
    }
    
     protected static OOSMNodeInstance generateTree(OOSM model, OOSMConstruct currentConstruct, OOSMNodeInstance parent) {
        DefaultOOSMNodeInstanceImpl root = new DefaultOOSMNodeInstanceImpl();
        root.setParent(parent);
        root.setDefinition(currentConstruct);
        //root.setData(currentConstruct.getName());
        if (currentConstruct instanceof OOSMElement) {
            OOSMRule rule = null;

            for (OOSMRule _rule : model.getRules()) {
                if (_rule.getHeadingElement().equals(currentConstruct)) {
                    rule = _rule;
                    break;
                }
            }

            if (rule != null) {
                //generate tree based on the selected rule
                for (OOSMConstruct construct : rule.getConstructs()) {
                        root.getChildNodes().add(generateTree(model, construct, root));
                }
            }
        }else if (currentConstruct instanceof OOSMElementList){
            OOSMElementList elementList=(OOSMElementList) currentConstruct;
            for(OOSMConstruct subConstructs : elementList.getElements()){
                root.getChildNodes().add(generateTree(model, subConstructs, root));
            }
        }else{
            return null;
        }
        return root;
    }
}
