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
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModelSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.namespace.QName;

/**
 *
 * @author lendle
 */
public class DefaultOOSMInstanceModelSerializer implements OOSMInstanceModelSerializer {

    @Override
    public OOSMInstanceModel createInstanceModel(InputStream input) throws Exception {
        try(XMLDecoder decoder=new XMLDecoder(input)){
            return (OOSMInstanceModel) decoder.readObject();
        }
    }

    @Override
    public OOSMInstanceModel createNew(OOSM schema) {
        OOSMNodeInstance root=this.generateTree(schema, schema.getRootElement(), null);
        DefaultOOSMInstanceModelImpl model=new DefaultOOSMInstanceModelImpl();
        model.setSchema(schema);
        model.setInstanceTree(root);
        return model;
    }

    @Override
    public void save(OOSMInstanceModel model, OutputStream output) throws Exception {
        try (XMLEncoder encoder = new XMLEncoder(output)) {
            encoder.setPersistenceDelegate(QName.class, new PersistenceDelegate() {

                @Override
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{((QName) oldInstance).getNamespaceURI(),
                        ((QName) oldInstance).getLocalPart()});
                }
            });
            encoder.writeObject(model);
        }
    }

    public static OOSMNodeInstance generateTree(OOSM model, OOSMConstruct currentConstruct, OOSMNodeInstance parent) {
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
