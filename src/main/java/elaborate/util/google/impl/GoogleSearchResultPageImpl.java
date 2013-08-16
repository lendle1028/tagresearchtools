/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import elaborate.util.google.GoogleSearchResult;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NodeList;

/**
 *
 * @author lendle
 */
public class GoogleSearchResultPageImpl implements GoogleSearchResultPage {

    private HtmlPage htmlPage;
    private WebClient webClient;
    private List<PagingElement> pagingElements = new ArrayList<PagingElement>();
    private PagingElement currentPage = null;

    public GoogleSearchResultPageImpl(WebClient webClient, HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
        this.webClient = webClient;
        DomElement navElement = this.htmlPage.getElementById("nav");
        NodeList navList = navElement.getElementsByTagName("td");
        for (int i = 0; i < navList.getLength(); i++) {
            DomElement tdElement = (DomElement) navList.item(i);
            String text = tdElement.asText().trim();
            if (text.length() > 0 && Character.isDigit(text.charAt(0))) {
                PagingElement paging = new PagingElement();
                paging.setIndex(Integer.valueOf(text));
                NodeList aList = tdElement.getElementsByTagName("a");
                if (aList.getLength() != 0) {
                    DomElement aElement = (DomElement) aList.item(0);
                    paging.setHref(aElement.getAttribute("href"));
                    paging.setAnchor((HtmlAnchor) aElement);
                    pagingElements.add(paging);
                }
                if (tdElement.asText().trim().length() > 0 && tdElement.getElementsByTagName("b").getLength() > 0) {
                    currentPage = paging;
                }
            }
        }
    }

    /**
     * Get the value of webClient
     *
     * @return the value of webClient
     */
    public WebClient getWebClient() {
        return webClient;
    }

    /**
     * Set the value of webClient
     *
     * @param webClient new value of webClient
     */
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Get the value of htmlPage
     *
     * @return the value of htmlPage
     */
    public HtmlPage getHtmlPage() {
        return htmlPage;
    }

    /**
     * Set the value of htmlPage
     *
     * @param htmlPage new value of htmlPage
     */
    public void setHtmlPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public List<GoogleSearchResult> getResults() throws Exception {
        return ResultExtractor.extract(htmlPage);
    }

    @Override
    public int getPageIndex() {
        return this.currentPage.getIndex();
    }

    @Override
    public List<Integer> getNavigatablePages() {
        List<Integer> ret = new ArrayList<Integer>();
        for (PagingElement page : this.pagingElements) {
            ret.add(page.getIndex());
        }
        return ret;
    }

    @Override
    public GoogleSearchResultPage gotoPage(int index) throws Exception {
        //System.out.println("gotoPage "+index);
        Thread.sleep(500);
        for (PagingElement page : this.pagingElements) {
            if (index == page.getIndex()) {
                HtmlAnchor anchor = page.getAnchor();
                if (anchor != null) {
                    //String newURL = this.htmlPage.getUrl() + anchor.getHrefAttribute();
                    //HtmlPage newResult = this.webClient.getPage(newURL);
                    HtmlPage newResult=anchor.click();
                    //System.out.println(newResult.asXml());
                    GoogleSearchResultPageImpl newPage = new GoogleSearchResultPageImpl(this.webClient, newResult);
                    return newPage;
                }
                else{
                    return this;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isPageNavigatable(int index) {
        for(int pageIndex : this.getNavigatablePages()){
            if(pageIndex==index){
                return true;
            }
        }
        return false;
    }
}
