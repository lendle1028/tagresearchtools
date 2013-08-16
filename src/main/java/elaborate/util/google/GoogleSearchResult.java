/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google;

/**
 * wrap a result from google search
 * 
 * anchorText: the label displayed on link
 * anchorHref: the url
 * abstractText: the description text about the result
 * @author lendle
 */
public class GoogleSearchResult {
    private String anchorText=null;
    private String anchorHref=null;
    private String abstractText=null;

    public GoogleSearchResult() {
    }

    public GoogleSearchResult(String anchorText, String anchorHref, String abstractText) {
        this.anchorText=anchorText;
        this.anchorHref=anchorHref;
        this.abstractText=abstractText;
    }
    
    public String getAnchorText() {
        return anchorText;
    }

    public void setAnchorText(String anchorText) {
        this.anchorText = anchorText;
    }

    public String getAnchorHref() {
        return anchorHref;
    }

    public void setAnchorHref(String anchorHref) {
        this.anchorHref = anchorHref;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
    
    
}
