/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.OOSMRule;

/**
 *
 * @author LENDLE
 */
public class GsonFactory {
    public static Gson createGson(){
        GsonBuilder builder=new GsonBuilder();
        builder.registerTypeAdapter(OOSMElement.class, new OOSMElementInstanceCreator());
        //builder.registerTypeAdapter(OOSMRule.class, new OOSMRuleInstanceCreator());
        builder.registerTypeAdapter(OOSMRule.class, new InterfaceDeserializer<OOSMRule>(new OOSMRuleInstanceCreator()));
        builder.registerTypeAdapter(OOSMElementList.class, new OOSMElementListInstanceCreator());
        return builder.create();
    }
}
