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
import elaborate.tag_analysis.oosm.impl.DefaultOOSMElementListImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class OOSMElementListInterfaceDeserializer extends BaseInterfaceDeserializer<OOSMElementList> {

    public OOSMElementListInterfaceDeserializer() {
        super(DefaultOOSMElementListImpl.class);
    }

    @Override
    protected Object processArrayElement(String name, JsonArray element, Field field, JsonDeserializationContext jdc) throws JsonParseException {
        if ("elements".equals(name)) {
            List constructsList = new ArrayList();
            for (int i = 0; i < element.size(); i++) {
                JsonObject constructJsonObject = element.get(i).getAsJsonObject();
                constructsList.add((OOSMElement) jdc.deserialize(constructJsonObject, OOSMElement.class));
            }
            return constructsList;
        } else {
            return super.processArrayElement(name, element, field, jdc); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
