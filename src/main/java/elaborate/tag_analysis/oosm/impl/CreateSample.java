/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import com.google.gson.Gson;
import javax.xml.namespace.QName;

/**
 * create an example OOSM
 * @author LENDLE
 */
public class CreateSample {
    public static void main(String [] args) throws Exception{
        /*
        test.test1 ::= root
        root ::= a
        a ::= (b,c)
        */
        DefaultOOSMImpl impl=new DefaultOOSMImpl();
        impl.setName(new QName("test", "test1"));
        impl.setDescription("description");
        
        DefaultOOSMElementImpl root=new DefaultOOSMElementImpl();
        root.setName(new QName("test", "root"));
        impl.setRootElement(root);
        
        DefaultOOSMElementImpl a=new DefaultOOSMElementImpl();
        a.setName(new QName("test", "a"));
        
        DefaultOOSMElementImpl b=new DefaultOOSMElementImpl();
        b.setName(new QName("test", "b"));
        
        DefaultOOSMElementImpl c=new DefaultOOSMElementImpl();
        c.setName(new QName("test", "c"));
        
        //root :: =a
        DefaultOOSMRuleImpl rule1=new DefaultOOSMRuleImpl();
        rule1.setHeadingElement(root);
        rule1.getConstructs().add(a);
        impl.getRules().add(rule1);
        //a ::= (b,c)
        DefaultOOSMRuleImpl rule2=new DefaultOOSMRuleImpl();
        rule2.setHeadingElement(a);
        rule2.getConstructs().add(b);
        rule2.getConstructs().add(c);
        impl.getRules().add(rule2);
        
        Gson gson=new Gson();
        String json=gson.toJson(impl);
        System.out.println(json);
    }
}
