/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.tag_extractor.delicious;

import elaborate.tag_analysis.tag_extractor.URLTagExtractor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author lendle
 */
public class DeliciousTagExtractor implements URLTagExtractor{

    @Override
    public List<String> extractTags(URL url, String charset) throws Exception {
        if(url.toString().startsWith("http://previous.delicious.com/url/")==false){
            url=new URL("http://previous.delicious.com/url/"+URLEncoder.encode(url.getHost().toString(), "utf-8"));
        }
        //http://previous.delicious.com/url/
        URL targetURL = url;
        long startTime=System.currentTimeMillis();
        //System.out.println("loading");
        BufferedReader input = new BufferedReader(new InputStreamReader(targetURL.openStream()));
        try {
            org.cyberneko.html.parsers.DOMParser parser=new org.cyberneko.html.parsers.DOMParser();
            //System.out.println("parsing");
            //HtmlCleaner cleaner = new HtmlCleaner();
            //TagNode tagNode=cleaner.clean(input);
            //Document doc=new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            parser.parse(new InputSource(input));
            Document doc=parser.getDocument();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("div");
            List<String> tags = new ArrayList<String>();
            //System.out.println("analyzing");
            outer:
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap attrs = node.getAttributes();
                    for (int j = 0; j < attrs.getLength(); j++) {
                        Node attr = attrs.item(j);
                        if (attr.getNodeName().equals("id") && attr.getNodeValue().equals("tagcloud")) {
                            extractTags(tags, node);
                            break outer;
                        }
                    }
                }
            }
            long okTime=System.currentTimeMillis();
            //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "URL: "+targetURL+", process time: "+(okTime-startTime));
            //System.out.println("URL: "+targetURL+", process time: "+(okTime-startTime)+" with "+tags.size()+" tags");
            return tags;
        } finally {
            input.close();
        }
    }
    
    private void extractTags(List<String> tags, Node node) {
        if (node.getNodeName().toLowerCase().equals("a")) {
            String tag = node.getFirstChild().getNodeValue();
            if (tags.contains(tag) == false) {
                tags.add(tag);
            }
        } else {
            Node child = node.getFirstChild();
            while (child != null) {
                extractTags(tags, child);
                child = child.getNextSibling();
            }
        }
    }
}
