/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elaborate.tag_analysis.oosm.OOSMRule;
import java.lang.reflect.Type;

/**
 *
 * @author LENDLE
 */
public class OOSMRuleDeserializer implements JsonDeserializer<OOSMRule>{

    @Override
    public OOSMRule deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject object=(JsonObject) je;
        return null;
    }
    
}
