/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMRule;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author LENDLE
 */
public class LoadSample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        DefaultOOSMSerializer serializer=new DefaultOOSMSerializer();
        try(InputStreamReader input=new InputStreamReader(new FileInputStream("test.oosm"), "utf-8" )){
            OOSM oosm=serializer.createOOSM(input);
            System.out.println(oosm.getRootElement());
            OOSMRule rule=oosm.getRules().get(0);
            System.out.println(oosm.getRules().size());
            System.out.println(rule.getConstructs().get(0).getName());
            System.out.println(rule.getHeadingElement());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
