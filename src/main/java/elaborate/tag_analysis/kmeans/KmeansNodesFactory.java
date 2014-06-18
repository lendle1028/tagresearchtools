/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

import elaborate.tag_analysis.LinkData;
import elaborate.tag_analysis.feature.FeatureFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lendle
 */
public class KmeansNodesFactory {
    public static List<elaborate.tag_analysis.kmeans.Node> getNodes(List<LinkData> links){
        List<elaborate.tag_analysis.kmeans.Node> ret=new ArrayList<elaborate.tag_analysis.kmeans.Node>();
        List<elaborate.tag_analysis.Tag> tags=null;
        Map<elaborate.tag_analysis.Tag, String> tagMap=new HashMap<elaborate.tag_analysis.Tag, String>();
        for(LinkData link : links){
            for(elaborate.tag_analysis.Tag tag : link.getTags()){
                tagMap.put(tag, "");
            }
        }
        tags=new ArrayList<elaborate.tag_analysis.Tag>(tagMap.keySet());
        for(elaborate.tag_analysis.Tag tag : tags){
            elaborate.tag_analysis.kmeans.Node kmeansTag=new elaborate.tag_analysis.kmeans.Node();
            kmeansTag.setValue(tag.getValue());
            kmeansTag.setFeature(FeatureFactory.getFeature(tag, links));
            //kmeansTag.setFeature(FeatureFactory.getFeature(tag, links).getVector());
            ret.add(kmeansTag);
        }
        return ret;
    }
}
