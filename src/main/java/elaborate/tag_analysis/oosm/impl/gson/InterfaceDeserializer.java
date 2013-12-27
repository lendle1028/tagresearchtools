/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import elaborate.tag_analysis.oosm.OOSMElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author LENDLE
 */
public class InterfaceDeserializer<T> implements JsonDeserializer<T> {
    private InstanceCreator instanceCreator=null;
    
    public InterfaceDeserializer(InstanceCreator instanceCreator){
        this.instanceCreator=instanceCreator;
    }
    
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        //System.out.println(je);
        T object=(T) instanceCreator.createInstance(type);
        JsonObject jsonObject=(JsonObject) je;
        Iterator<Entry<String, JsonElement>> it=jsonObject.entrySet().iterator();
        while(it.hasNext()){
            try {
                Entry<String, JsonElement> entry=it.next();
                System.out.println(PropertyUtils.getPropertyType(object, entry.getKey()));
            } catch (IllegalAccessException ex) {
                Logger.getLogger(InterfaceDeserializer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(InterfaceDeserializer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(InterfaceDeserializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*JsonArray constructs=object.getAsJsonArray("constructs");
        for(int i=0; i<constructs.size(); i++){
            System.out.println(constructs.get(i).getAsJsonObject().get("elements"));
        }
        System.out.println(jdc.deserialize(object.get("headingElement"), OOSMElement.class));*/
        return null;
    }
    
}
