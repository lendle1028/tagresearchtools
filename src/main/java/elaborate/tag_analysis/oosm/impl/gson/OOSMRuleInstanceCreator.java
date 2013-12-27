/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.InstanceCreator;
import elaborate.tag_analysis.oosm.OOSMRule;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMRuleImpl;
import java.lang.reflect.Type;

/**
 *
 * @author LENDLE
 */
public class OOSMRuleInstanceCreator implements InstanceCreator<OOSMRule>{

    @Override
    public OOSMRule createInstance(Type type) {
        return new DefaultOOSMRuleImpl();
    }
    
}
