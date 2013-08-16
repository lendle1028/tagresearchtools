/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google;

import java.util.List;

/**
 * a client tool to execute google search
 * @author lendle
 */
public interface GoogleSearchClient {
    /**
     * 
     * @param q the search string
     * @param start the start index
     * @param size how many results to be returned
     * @return
     * @throws Exception 
     */
    public List<GoogleSearchResult> search(String q, int start, int size) throws Exception;
}
