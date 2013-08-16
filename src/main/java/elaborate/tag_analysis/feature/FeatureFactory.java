/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.feature;

import elaborate.tag_analysis.LinkData;
import elaborate.tag_analysis.Tag;
import java.util.List;

/**
 *
 * @author lendle
 */
public class FeatureFactory {
    public static double [] getFeature(Tag tag, List<LinkData> links){
        double [] feature=new double[links.size()];
        for(int i=0; i<links.size(); i++){
            LinkData link=links.get(i);
            if(link.getTags().contains(tag)){
                feature[i]+=1;
            }
        }
        return feature;
    }
}
