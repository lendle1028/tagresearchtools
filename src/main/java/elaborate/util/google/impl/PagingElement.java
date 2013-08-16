/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google.impl;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;

/**
 *
 * @author lendle
 */
public class PagingElement {

    private int index;
    private String href;
    private HtmlAnchor anchor;

    /**
     * Get the value of href
     *
     * @return the value of href
     */
    public String getHref() {
        return href;
    }

    /**
     * Set the value of href
     *
     * @param href new value of href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Get the value of index
     *
     * @return the value of index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the value of index
     *
     * @param index new value of index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    public HtmlAnchor getAnchor() {
        return anchor;
    }

    public void setAnchor(HtmlAnchor anchor) {
        this.anchor = anchor;
    }
}
