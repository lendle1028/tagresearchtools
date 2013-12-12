/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import com.google.gson.Gson;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMRule;
import javax.xml.namespace.QName;

/**
 * create an example OOSM
 * @author LENDLE
 */
public class CreateSample {
    public static void main(String [] args) throws Exception{
        DefaultOOSMSerializer serializer=new DefaultOOSMSerializer();
        /*
        test.test1 ::= root
        root ::= a
        a ::= (b,c)
        */
        DefaultOOSMImpl impl=(DefaultOOSMImpl) serializer.createNew();
        impl.setName(new QName("test", "test1"));
        impl.setDescription("description");
        
        OOSMElement root=impl.createElement(new QName("test", "root"), null);
        impl.setRootElement(root);
        
        OOSMElement a=impl.createElement(new QName("test", "a"), null);
        
        OOSMElement b=impl.createElement(new QName("test", "b"), null);
        
        OOSMElement c=impl.createElement(new QName("test", "c"), null);
        
        //root :: =a
        OOSMRule rule1=new DefaultOOSMRuleImpl(root);
        rule1.getConstructs().add(a);
        impl.getRules().add(rule1);
        //a ::= (b,c)
        OOSMRule rule2=new DefaultOOSMRuleImpl(a);
        rule2.getConstructs().add(b);
        rule2.getConstructs().add(c);
        impl.getRules().add(rule2);
        
        serializer.save(impl, System.out);
    }
}
