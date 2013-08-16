/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.tag_extractor;

import java.net.URL;
import java.util.List;

/**
 * extract tags from a given url
 * @author lendle
 */
public interface URLTagExtractor {
    public List<String> extractTags(URL url, String charset) throws Exception;
}
