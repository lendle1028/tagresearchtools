/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.utils;

import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.Node;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author lendle
 */
public class ClusterUtil {
    /**
     * ratio=intersect/union urls
     * @param cluster1
     * @param cluster2
     * @return 
     */
    public static double calculateURLOverlapRatio(Cluster cluster1, Cluster cluster2) {
        Set<URL> setA=new HashSet<URL>(cluster1.getCoveredURLs());
        Set<URL> setB = new HashSet<URL>(cluster2.getCoveredURLs());
        Set<URL> diffAB = new HashSet<URL>(setA);
        diffAB.removeAll(setB);
        Set<URL> intersectAB = new HashSet<URL>(setA);
        intersectAB.removeAll(diffAB);
        Set<URL> unionAB = new HashSet<URL>(setA);
        unionAB.addAll(setB);
        return (double)intersectAB.size()/(double)unionAB.size();
    }
    /**
     * merge the two clusters
     * @param cluster1
     * @param cluster2
     * @return 
     */
    public static Cluster mergeCluster(Cluster cluster1, Cluster cluster2){
        Cluster newCluster=new Cluster();
        for(Node tag : cluster1.getTags()){
            newCluster.getTags().add(tag);
        }
        for(Node tag : cluster2.getTags()){
            newCluster.getTags().add(tag);
        }
        newCluster.reset();
        return newCluster;
    }
}
