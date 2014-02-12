/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMSerializer;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMSerializer;
import elaborate.tag_analysis.oosm.impl.instance.DefaultOOSMInstanceModelSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModelSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

/**
 *
 * @author DELL
 */
public class OOSMMapperApplication {

    private OOSMInstanceModel instance = null;
    private OOSM schema = null;
    private Document doc = null;

    public OOSMInstanceModel getInstance() {
        return instance;
    }

    public void setInstance(OOSMInstanceModel instance) {
        this.instance = instance;
    }

    public OOSM getSchema() {
        return schema;
    }

    public void setSchema(OOSM schema) {
        this.schema = schema;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    
    public void createNewInstance(File schemaFile) throws Exception {
        OOSMSerializer serializer = new DefaultOOSMSerializer();
        try (FileReader input = new FileReader(schemaFile)) {
            schema = serializer.createOOSM(input);
            OOSMInstanceModelSerializer ooSMInstanceModelSerializer = new DefaultOOSMInstanceModelSerializer();
            instance = ooSMInstanceModelSerializer.createNew(schema);
        }
    }

    public void loadDocument(URL url) throws Exception {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        try (Reader reader = new InputStreamReader(url.openStream(), "utf-8")) {
            doc = tidy.parseDOM(reader, null);
        }
    }
}
