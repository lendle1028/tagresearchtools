/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.oosm.impl;

import java.net.URL;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * jtidy test
 * @author LENDLE
 */
public class JTidyTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        URL url=new URL("http://jtidy.sourceforge.net/howto.html");
        Document doc=tidy.parseDOM(url.openStream(), null);
        tidy.pprint(doc, System.out);
        XPathFactory xpf=XPathFactory.newInstance();
        XPath xpath=xpf.newXPath();
        NodeList list=(NodeList) xpath.evaluate("//div", doc, XPathConstants.NODESET);
        System.out.println(list.getLength());
        for(int i=0; i<list.getLength(); i++){
            Node node=list.item(i);
            tidy.pprint(node, System.out);
        }
    }
    
}
