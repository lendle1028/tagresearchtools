/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.kmeans;

import java.util.List;

/**
 * the SimpleCentroidSelectorImpl simply select the 
 * first numberOfCentroids nodes as the new centroid
 * @author lendle
 */
public class SimpleCentroidSelectorImpl implements CentroidSelector{

    @Override
    public Centroid[] selectNewCentroids(Cluster cluster, int numberOfCentroids) {
        Centroid[] centroids = new Centroid[numberOfCentroids];
        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid(cluster.getTags().get(i).getFeature().getVector());
        }
        return centroids;
    }

    @Override
    public Centroid[] selectNewCentroids(List<Node> tags, int numberOfCentroids) {
        Centroid[] centroids = new Centroid[numberOfCentroids];
        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid(tags.get(i).getFeature().getVector());
        }
        return centroids;
    }
    
}
