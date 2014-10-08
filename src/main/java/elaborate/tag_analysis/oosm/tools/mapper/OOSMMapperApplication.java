/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.oosm.tools.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import elaborate.tag_analysis.oosm.OOSM;
import elaborate.tag_analysis.oosm.OOSMConstruct;
import elaborate.tag_analysis.oosm.OOSMSerializer;
import elaborate.tag_analysis.oosm.impl.DefaultOOSMSerializer;
import elaborate.tag_analysis.oosm.impl.instance.DefaultOOSMInstanceModelSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModel;
import elaborate.tag_analysis.oosm.instance.OOSMInstanceModelSerializer;
import elaborate.tag_analysis.oosm.instance.OOSMNodeInstance;
import elaborate.tag_analysis.oosm.instance.binding.Binding;
import elaborate.tag_analysis.oosm.instance.binding.EvaluatedObject;
import elaborate.tag_analysis.oosm.tools.utils.DOMTreeUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

/**
 *
 * @author DELL
 */
public class OOSMMapperApplication {

    private OOSMInstanceModel instance = null;
    private OOSM schema = null;
    private Document doc = null;
    private ProjectConfiguration projectConfiguration = null;

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

    public void open(ProjectConfiguration conf) throws Exception {
        this.projectConfiguration = conf;
        OOSMSerializer serializer = new DefaultOOSMSerializer();
        try (FileReader input = new FileReader(conf.getOosmFile())) {
            schema = serializer.createOOSM(input);
            OOSMInstanceModelSerializer ooSMInstanceModelSerializer = new DefaultOOSMInstanceModelSerializer();
            File modelFile = new File(conf.getProjectLocation(), "oosm.model");
            if (modelFile.exists()) {
                //load existing model
                try (FileInputStream modelInput = new FileInputStream(modelFile)) {
                    instance = ooSMInstanceModelSerializer.createInstanceModel(modelInput);
                }
            } else {
                //create new model
                instance = ooSMInstanceModelSerializer.createNew(schema);
            }
        }
        this.loadDocument(conf.getDocumentURL());
    }

    public ProjectConfiguration getProjectConfiguration() {
        return projectConfiguration;
    }

    public void save() throws Exception {
        OOSMInstanceModelSerializer ooSMInstanceModelSerializer = new DefaultOOSMInstanceModelSerializer();
        File modelFile = new File(this.projectConfiguration.getProjectLocation(), "oosm.model");
        try (FileOutputStream output = new FileOutputStream(modelFile)) {
            ooSMInstanceModelSerializer.save(instance, output);
        }
    }

    protected void loadDocument(URL url) throws Exception {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        try (Reader reader = new InputStreamReader(url.openStream(), "utf-8")) {
            doc = tidy.parseDOM(reader, null);
        }
    }

    /**
     * return dom nodes bound to the given instance node
     *
     * @param node
     * @return
     */
    public List<Node> getCorrespondingNodes(OOSMNodeInstance node) throws Exception {
        List<Node> ret = new ArrayList<Node>();
        List<Binding> bindings = this.instance.getBindings(node);
        if (bindings != null) {
            for (Binding binding : bindings) {
                //System.out.println(binding.getTarget());
                if (binding.getTargetNode() != null) {
                    Object _possibleNode = this.instance.evaluateTargetNode(binding, this.doc);
                    if (_possibleNode instanceof Node) {
                        Node _node = (Node) _possibleNode;
                        if (_node != null) {
                            ret.add(_node);
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * evaluate bindings, return results
     *
     * @return
     */
    /*public String evaluateBindings(){
     StringBuffer buffer=new StringBuffer();
        
     }*/
    public String exportEvaluatedBindingResult2HTML() throws Exception {
        EvaluatedObject obj = this.instance.evaluateAllBindings(doc);
        StringBuffer html = new StringBuffer();
        html.append("<html><body>");
        html.append(obj.getRoot().getName()).append(":");
        html.append(this.exportEvaluatedBindingResult2HTML(obj));
        html.append("<hr>");
        String json=obj.convert2JSON();
        Gson gson=new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Map jsonMap=gson.fromJson(json, Map.class);
        String jsonOutput=gson.toJson(jsonMap);
        html.append("<pre>");
        html.append(jsonOutput);
        html.append("</pre>");
        
        html.append("</body></html>");
        return html.toString();
    }
  
    public String exportEvaluatedBindingResult2JSON() throws Exception {
        EvaluatedObject obj = this.instance.evaluateAllBindings(doc);
        String json=obj.convert2JSON();
        //System.out.println(json);
        return json;
    }
    
    private String exportEvaluatedBindingResult2HTML(EvaluatedObject root) throws Exception {
        EvaluatedObject obj = root;
        StringBuffer html = new StringBuffer();
        html.append("[");
        List values = obj.getRootValue();
        for (int i = 0; values != null && i < values.size(); i++) {
            if (i != 0) {
                html.append(",");
            }
            Object value = values.get(i);
            if (value != null) {
                if (value instanceof Node) {
                    html.append(StringEscapeUtils.escapeHtml4(DOMTreeUtils.node2Text((Node) value)));
                } else {
                    html.append(StringEscapeUtils.escapeHtml4("" + value));
                }
            }
        }
        html.append("]");
        html.append("<ul>");

        for (OOSMConstruct name : obj.getPropertyNames()) {
            List<EvaluatedObject> children = obj.getProperty(name);
            for (int i = 0; children != null && i < children.size(); i++) {
                EvaluatedObject child = children.get(i);
                if ((child.getRootValue() == null || child.getRootValue().isEmpty()) && (child.getPropertyNames() == null || child.getPropertyNames().isEmpty())) {
                    //skip empty entry
                    continue;
                }
                html.append("<li>");
                html.append(name.getName());
                html.append(":");
                html.append(this.exportEvaluatedBindingResult2HTML(child));
                html.append("</li>");
            }
        }
        html.append("</ul>");
        return html.toString();
    }
}
