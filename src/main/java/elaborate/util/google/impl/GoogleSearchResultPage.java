/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google.impl;

import elaborate.util.google.GoogleSearchResult;
import java.util.List;

/**
 *
 * @author lendle
 */
public interface GoogleSearchResultPage {
    public List<GoogleSearchResult> getResults() throws Exception;
    public int getPageIndex();
    public List<Integer> getNavigatablePages();
    public GoogleSearchResultPage gotoPage(int index) throws Exception;
    public boolean isPageNavigatable(int index);
}
