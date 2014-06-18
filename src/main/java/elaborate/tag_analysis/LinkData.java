/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class LinkData {

    private URL url;
    private List<Tag> tags = new ArrayList<Tag>();

    /**
     * Get the value of tags
     *
     * @return the value of tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Set the value of tags
     *
     * @param tags new value of tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(URL url) {
        this.url = url;
    }

}
