/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMElement;
import elaborate.tag_analysis.oosm.OOSMElementList;
import elaborate.tag_analysis.oosm.OOSMRule;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMRuleImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class OOSMRuleInterfaceDeserializer extends BaseInterfaceDeserializer<OOSMRule> {

    public OOSMRuleInterfaceDeserializer() {
        super(DefaultOOSMRuleImpl.class);
    }

    @Override
    protected Object processArrayElement(String name, JsonArray element, Field field, JsonDeserializationContext jdc) throws JsonParseException {
        if ("constructs".equals(name)) {
            List constructsList = new ArrayList();
            JsonArray constructsJsonArray = element;
            for (int i = 0; i < constructsJsonArray.size(); i++) {
                JsonObject constructJsonObject = constructsJsonArray.get(i).getAsJsonObject();
                if (constructJsonObject.has("elements")) {
                    constructsList.add((OOSMConstruct) jdc.deserialize(constructJsonObject, OOSMElementList.class));
                } else {
                    constructsList.add((OOSMConstruct) jdc.deserialize(constructJsonObject, OOSMElement.class));
                }
            }
            return constructsList;
        } else {
            return super.processArrayElement(name, element, field, jdc); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
