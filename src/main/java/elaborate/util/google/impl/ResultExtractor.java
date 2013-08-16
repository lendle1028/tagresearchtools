/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google.impl;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import elaborate.util.google.GoogleSearchResult;
import java.util.ArrayList;
import java.util.List;

/**
 * extract GoogleSearchResult for a given page
 * @author lendle
 */
public class ResultExtractor {
    public static List<GoogleSearchResult> extract(HtmlPage htmlPage) throws Exception{
        List<GoogleSearchResult> ret=new ArrayList<GoogleSearchResult>();
        List list=htmlPage.getByXPath("//h3[@class='r']");
        for(int i=0; i<list.size(); i++){
            GoogleSearchResult result=new GoogleSearchResult();
            DomElement element=(DomElement) list.get(i);
            DomElement parentElement=(DomElement) element.getParentNode();
            //filter out image results
            if(parentElement.getElementsByTagName("img").getLength()>0){
                continue;
            }
            HtmlAnchor anchor=(HtmlAnchor) element.getElementsByTagName("a").item(0);
            result.setAnchorText(anchor.getTextContent());
            result.setAnchorHref(hrefExtractor(anchor.getHrefAttribute()));
            //System.out.println(parentElement.asXml());
            List stList=parentElement.getByXPath("descendant::*[@class='st']");
            if(stList.size()>0){
                DomElement stElement=(DomElement) stList.get(0);
                result.setAbstractText(stElement.asText());
            }
            ret.add(result);
        }
        return ret;
    }
    
    protected static String hrefExtractor(String href){
        href=href.substring(7);
        int index=href.indexOf("&");
        return href.substring(0, index);
    }
}
