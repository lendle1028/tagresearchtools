/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.kmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * the RandomCentroidSelectorImpl select random nodes as
 * centroids
 * @author lendle
 */
public class RandomCentroidSelectorImpl implements CentroidSelector{

    @Override
    public Centroid[] selectNewCentroids(Cluster cluster, int numberOfCentroids) {
        return this.selectNewCentroids(cluster.getTags(), numberOfCentroids);
    }

    @Override
    public Centroid[] selectNewCentroids(List<Node> tags, int numberOfCentroids) {
        Centroid[] centroids = new Centroid[numberOfCentroids];
        List<Node> candidates=new ArrayList<Node>(tags);
        for (int i = 0; i < centroids.length; i++) {
            int index=(int) ((Math.random()*1000*tags.size())%candidates.size());
            centroids[i] = new Centroid(candidates.get(index).getFeature());
            candidates.remove(index);
        }
        return centroids;
    }
    
}
