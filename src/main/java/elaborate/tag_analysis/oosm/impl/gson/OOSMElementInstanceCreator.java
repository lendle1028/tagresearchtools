/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.InstanceCreator;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMElementImpl;
import java.lang.reflect.Type;

/**
 *
 * @author LENDLE
 */
public class OOSMElementInstanceCreator implements InstanceCreator<OOSMElement>{

    @Override
    public OOSMElement createInstance(Type type) {
        return new DefaultOOSMElementImpl();
    }
    
}
