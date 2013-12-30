/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.impl.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * extend this class and override createInstance, processNormalElement, and
 * processArrayElement to customize an InterfaceDeserializer for gson
 *
 * @author LENDLE
 */
public class BaseInterfaceDeserializer<T> implements JsonDeserializer<T> {

    private Class<? extends T> instanceClass = null;

    public BaseInterfaceDeserializer(Class<? extends T> instanceClass) {
        this.instanceClass = instanceClass;
    }

    protected T createInstance() throws InstantiationException, IllegalAccessException {
        return instanceClass.newInstance();
    }

    protected Object processNormalElement(String name, JsonElement element, Field field, JsonDeserializationContext jdc) throws JsonParseException {
        return this.processNormalElement(name, element, field.getType(), jdc);
    }

    protected Object processNormalElement(String name, JsonElement element, Class clz, JsonDeserializationContext jdc) throws JsonParseException {
        return jdc.deserialize(element, clz);
    }

    protected Object processArrayElement(String name, JsonArray element, Field field, JsonDeserializationContext jdc) throws JsonParseException {
        List list = new ArrayList();
        Class arrayElementType = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        for (int i = 0; i < element.size(); i++) {
            JsonObject jsonObject = element.get(i).getAsJsonObject();
            list.add(this.processNormalElement(null, jsonObject, arrayElementType, jdc));
        }
        return list;
    }

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        try {
            T object = (T) this.createInstance();
            JsonObject jsonObject = (JsonObject) je;
            Iterator<Entry<String, JsonElement>> it = jsonObject.entrySet().iterator();
            //iterate through class fields
            while (it.hasNext()) {
                try {
                    Entry<String, JsonElement> entry = it.next();
                    JsonElement jsonElement = entry.getValue();
                    Field field = getField(entry.getKey());
                    if (jsonElement.isJsonArray()) {
                        Object arrayValue = this.processArrayElement(entry.getKey(), jsonElement.getAsJsonArray(), field, jdc);
                        PropertyUtils.setProperty(object, entry.getKey(), arrayValue);
                    } else {
                        Object normalValue = this.processNormalElement(entry.getKey(), jsonElement, field, jdc);
                        PropertyUtils.setProperty(object, entry.getKey(), normalValue);
                    }
                } catch (Exception ex) {
                    //System.out.println(object.getClass());
                    Logger.getLogger(BaseInterfaceDeserializer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return object;
        } catch (Exception ex) {
            Logger.getLogger(BaseInterfaceDeserializer.class.getName()).log(Level.SEVERE, null, ex);
            throw new JsonParseException(ex);
        }
    }

    protected Field getField(String fieldName) {
        return this.getField(this.instanceClass, fieldName);
    }
    
    private Field getField(Class clz, String fieldName){
        Field field=null;
        try {
            field=clz.getDeclaredField(fieldName);
        } catch (Exception ex) {
        }
        if(field==null && clz.getSuperclass()!=null){
            return this.getField(clz.getSuperclass(), fieldName);
        }
        return field;
    }
}
