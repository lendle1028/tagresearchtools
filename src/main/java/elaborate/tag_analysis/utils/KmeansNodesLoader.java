/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.utils;

import elaborate.tag_analysis.LinkData;
import elaborate.tag_analysis.Tag;
import elaborate.tag_analysis.kmeans.KmeansNodesFactory;
import elaborate.tag_analysis.kmeans.Node;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lendle
 */
public class KmeansNodesLoader {
    public static List<Node> loadNodes(String filePath) throws Exception{
        List<LinkData> list = LinkDataLoader.load(filePath);
        Map<String, String> tags = new HashMap<String, String>();

        for (LinkData linkData : list) {
            //System.out.println(linkData.getUrl());
            for (Tag tag : linkData.getTags()) {
                String tagValue = tag.getValue();
                tags.put(tagValue, "");
            }
        }
        //System.out.println(tags.size() + "/" + list.size());
        //get k-means nodes
        List<Node> nodes = KmeansNodesFactory.getNodes(list);
        return nodes;
    }
}
