/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import javax.xml.namespace.QName;

/**
 *
 * @author LENDLE
 */
public class CreateSample {
    public static void main(String [] args) throws Exception{
        DefaultOOSMImpl impl=new DefaultOOSMImpl();
        impl.setName(new QName("test", "test1"));
        impl.setDescription("description");
        
        DefaultOOSMElementImpl root=new DefaultOOSMElementImpl();
        root.setName(new QName("test", "root"));
        impl.setRootElement(root);
        
        DefaultOOSMElementImpl a=new DefaultOOSMElementImpl();
        a.setName(new QName("test", "a"));
        
        DefaultOOSMRuleImpl rule1=new DefaultOOSMRuleImpl();
        rule1.setHeadingElement(a);
    }
}
