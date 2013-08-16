/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.util.List;
import org.w3c.dom.NodeList;

/**
 *
 * @author lendle
 */
public class HTMLUnit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        final WebClient webClient = new WebClient();
        //final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://www.google.com.tw");
        HtmlInput query=(HtmlInput) page.getElementsByName("q").get(0);        
        HtmlInput submit=(HtmlInput) page.getElementsByName("btnG").get(0);
        System.out.println(page.asXml());

        query.setValueAttribute("test");
        
        HtmlPage result=submit.click();
        List list=result.getByXPath("//h3[@class='r']");
        HtmlAnchor lastAnchor=null;
        /*for(int i=0; i<list.size(); i++){
            DomElement element=(DomElement) list.get(i);
            DomElement parentElement=(DomElement) element.getParentNode();
            HtmlAnchor anchor=(HtmlAnchor) element.getElementsByTagName("a").item(0);
            lastAnchor=anchor;
            System.out.println(anchor.getTextContent()+":"+anchor.getHrefAttribute());
            //System.out.println(parentElement.asXml());
            List stList=parentElement.getByXPath("descendant::*[@class='st']");
            DomElement stElement=(DomElement) stList.get(0);
            System.out.println("\t"+stElement.asText());
        }*/
        //example getting the content of a page
        /*String newURL=result.getUrl()+lastAnchor.getHrefAttribute();
        result=webClient.getPage(newURL);
        //System.out.println(result.asXml());
        webClient.closeAllWindows();*/
        DomElement navElement=result.getElementById("nav");
        System.out.println(navElement.asXml());
        NodeList navList=navElement.getElementsByTagName("td");
        //System.out.println("navList.size()="+navList.getLength());
        for(int i=0; i<navList.getLength(); i++){
            DomElement tdElement=(DomElement) navList.item(i);
            //System.out.println("tdElement.getAttribute(\"class\")="+tdElement.getAttribute("class"));
            if(tdElement.asText().trim().length()>0 && tdElement.getElementsByTagName("b").getLength()>0){
                System.out.println("current="+tdElement.asText()+", "+i);
            }
            if(tdElement.getAttribute("class").equals("b navend")){
                continue;
            }
            else if(tdElement.getAttribute("class").equals("cur")){
                System.out.println("current="+tdElement.asText());
                continue;
            }
            else{
                NodeList aList=tdElement.getElementsByTagName("a");
                if(aList.getLength()==0){
                    continue;
                }
                DomElement aElement=(DomElement) aList.item(0);
                String aText=aElement.asText();
                if(Character.isDigit(aText.charAt(0))){
                    System.out.println(aText.trim()+":"+aElement.getAttribute("href"));
                }
            }
        }
        webClient.closeAllWindows();
        
        /*final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
        final HtmlPage page = webClient.getPage("http://previous.delicious.com/url/www.google.com.tw");
        DomElement tagCloud=page.getElementById("tagcloud");
        DomNodeList nodeList=tagCloud.getElementsByTagName("a");
        DomNode node=(DomNode) nodeList.item(0);
        HtmlAnchor anchor=(HtmlAnchor) node;
        HtmlPage newPage=anchor.click();
        System.out.println(newPage.asXml());
        webClient.closeAllWindows();*/
    }
}
