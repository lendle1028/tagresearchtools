/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.kmeans;

import java.util.List;

/**
 * implement this to control the centroid-selection behavior
 * @author lendle
 */
public interface CentroidSelector {
    public Centroid [] selectNewCentroids(Cluster cluster, int numberOfCentroids);
    public Centroid [] selectNewCentroids(List<Node> tags,  int numberOfCentroids);
}
