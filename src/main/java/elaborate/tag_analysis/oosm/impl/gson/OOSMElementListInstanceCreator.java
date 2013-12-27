/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.InstanceCreator;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMElementListImpl;
import java.lang.reflect.Type;

/**
 *
 * @author LENDLE
 */
public class OOSMElementListInstanceCreator implements InstanceCreator<OOSMElementList>{

    @Override
    public OOSMElementList createInstance(Type type) {
        return new DefaultOOSMElementListImpl();
    }
    
}
