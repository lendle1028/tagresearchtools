/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

import java.util.List;

/**
 *
 * @author lendle
 */
public interface KmeansCalculator {
    public List<Cluster> calculate(List<Centroid> centroids, List<Node> tags);
    /**
     * automatically pick up centroids
     * @param numberOfCentroids
     * @param tags
     * @return 
     */
    public List<Cluster> calculate(int numberOfCentroids, List<Node> tags);
    /**
     * split a cluster
     * @param numberOfNewCentroids
     * @param cluster
     * @return 
     */
    public List<Cluster> calculate(int numberOfNewCentroids, Cluster cluster);
}
