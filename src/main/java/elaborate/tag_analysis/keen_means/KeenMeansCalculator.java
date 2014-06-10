/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.keen_means;

import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface KeenMeansCalculator {

    /**
     * re-categorize existing clusters using the keen-means algorithm
     *
     * @param clusters
     * @param badClusterDetector
     * @param kmeansCalculator
     * @return
     */
    public List<Cluster> calculate(List<Cluster> clusters, BadClusterDetector badClusterDetector, KmeansCalculator kmeansCalculator);

    public static interface BadClusterDetector {

        /**
         * return true if the given cluster is considered as bad
         *
         * @param cluster
         * @return
         */
        public boolean isBadCluster(Cluster cluster);
    }

}
