/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import com.google.gson.Gson;
import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMSerializer;
import elaborate.tag_analysis.oosm.impl.gson.GsonFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * a json based serializer
 * @author LENDLE
 */
public class DefaultOOSMSerializer implements OOSMSerializer{

    @Override
    public OOSM createOOSM(InputStream input) throws Exception {
        Gson gson=GsonFactory.createGson();
        return gson.fromJson(new InputStreamReader(input), DefaultOOSMImpl.class);
    }

    @Override
    public OOSM createOOSM(Reader input) throws Exception {
        Gson gson=GsonFactory.createGson();
        return gson.fromJson(input, DefaultOOSMImpl.class);
    }

    @Override
    public OOSM createNew() {
        return new DefaultOOSMImpl();
    }

    @Override
    public void save(OOSM model, OutputStream output) throws Exception {
        Gson gson=GsonFactory.createGson();
        String json=gson.toJson(model);
        PrintStream print=new PrintStream(output);
        print.print(json);
        print.flush();
    }

    @Override
    public void save(OOSM model, Writer output) throws Exception {
        Gson gson=GsonFactory.createGson();
        String json=gson.toJson(model);
        PrintWriter print=new PrintWriter(output);
        print.print(json);
        print.flush();
    }
    
}
