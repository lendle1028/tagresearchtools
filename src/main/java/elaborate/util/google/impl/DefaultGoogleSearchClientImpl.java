/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google.impl;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import elaborate.util.google.GoogleSearchClient;
import elaborate.util.google.GoogleSearchResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class DefaultGoogleSearchClientImpl implements GoogleSearchClient{

    @Override
    public List<GoogleSearchResult> search(String q, int start, int size) throws Exception {
        List<GoogleSearchResult> result=new ArrayList<GoogleSearchResult>();
        final WebClient webClient = new WebClient();
        //final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://www.google.com.tw");
        HtmlInput query=(HtmlInput) page.getElementsByName("q").get(0);        
        HtmlInput submit=(HtmlInput) page.getElementsByName("btnG").get(0);

        query.setValueAttribute(q);

        HtmlPage htmlPage=submit.click();
        GoogleSearchResultPage googlePage=new GoogleSearchResultPageImpl(webClient, htmlPage);
        GoogleSearchResultPage currentPage=this.locateStartPage(googlePage, start);
        while(currentPage!=null && result.size()<size){
            List<GoogleSearchResult> subResults=currentPage.getResults();
            for(int i=0; subResults!=null && i<subResults.size(); i++){
                int globalIndex=(currentPage.getPageIndex()-1)*10+i;
                if(globalIndex>=start){
                    result.add(subResults.get(i));
                }
                if(result.size()==size){
                    break;
                }
            }
            if(result.size()<size && currentPage.isPageNavigatable(currentPage.getPageIndex()+1)){
                currentPage=currentPage.gotoPage(currentPage.getPageIndex()+1);
            }else{
                break;
            }
        }
        webClient.closeAllWindows();
        return result;
    }
    
    private GoogleSearchResultPage locateStartPage(GoogleSearchResultPage googlePage, int startIndex) throws Exception{
        int startPage=startIndex/10+1;
        int lastNavigablePage=-1;
        for(int pageIndex : googlePage.getNavigatablePages()){
            lastNavigablePage=pageIndex;
            if(pageIndex==startPage){
                return googlePage.gotoPage(pageIndex);
            }
        }
        //the startPage is not navigable from current page
        //System.out.println("lastNavigablePage="+lastNavigablePage+", "+Arrays.toString(googlePage.getNavigatablePages().toArray()));
        if(googlePage.getNavigatablePages().size()<=1){
            return null;
        }
        GoogleSearchResultPage lastPage=googlePage.gotoPage(lastNavigablePage);
        
        return this.locateStartPage(lastPage, startIndex);
    }
    
}
