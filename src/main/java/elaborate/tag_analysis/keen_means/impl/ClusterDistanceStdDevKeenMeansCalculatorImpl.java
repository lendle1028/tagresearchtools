/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.keen_means.impl;

import static elaborate.tag_analysis.App.getAverageDistanceAcrossClusters;
import elaborate.tag_analysis.TagStdevRatioPair;
import elaborate.tag_analysis.feature.DistanceCalculator;
import elaborate.tag_analysis.keen_means.KeenMeansCalculator;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the implementation at first calculate overall average distance, OAVG, between nodes
 * and corresponding centroid
 * and use stdev among distance for each cluster, EAVG, to capture the distance variation among clusters
 * if | EAVG[i]-OAVG | >= x * stdev, then cluster[i] is a bad cluster
 * 
 * @author DELL
 */
public class ClusterDistanceStdDevKeenMeansCalculatorImpl implements KeenMeansCalculator{

    @Override
    public List<Cluster> calculate(List<Cluster> clusters, BadClusterDetector badClusterDetector, KmeansCalculator kmeansCalculator) {
        //post processing, calculate average distances
        double totalAvergae=getAverageDistanceAcrossClusters(clusters);
        System.out.println("average distance across clusters="+(totalAvergae));
        //calculate stdev of average distance among clusters
        double clusterVariations=0;
        double clusterStdev=0;
        for (Cluster cluster : clusters) {
            if(Double.isNaN(cluster.getAverageDistance())){
                continue;
            }
            clusterVariations+=Math.pow(cluster.getAverageDistance()-totalAvergae, 2);
        }
        clusterStdev=Math.pow(clusterVariations, 0.5)/clusters.size();
        System.out.println("stdev of average distance across clusters="+(clusterStdev));
        List<Cluster> badClusters=new ArrayList<Cluster>();
        List<Cluster> goodClusters=new ArrayList<Cluster>();
        int clusterIndex=0;
        for (Cluster cluster : clusters) {
            if((cluster.getAverageDistance()-totalAvergae)>clusterStdev){
                //this is a bad cluster
                System.out.println("bad cluster: "+clusterIndex+":"+(cluster.getAverageDistance()-totalAvergae)+":"+(+(cluster.getAverageDistance()-totalAvergae)/clusterStdev));
                badClusters.add(cluster);
            }
            else{
                goodClusters.add(cluster);
            }
            clusterIndex++;
        }
        //recalculate bad clusters
        while(badClusters.isEmpty()==false){
            System.out.println("# of bad clusters="+badClusters.size());
            List<Cluster> processingClusters=new ArrayList<Cluster>(badClusters);
            badClusters.clear();
            processing: for(Cluster cluster : processingClusters){
                List<Cluster> newClusters = kmeansCalculator.calculate(2, cluster);
                //verify
                for(Cluster newCluster : newClusters){
                    if(newCluster.getTags().size()<=1){
                        goodClusters.add(cluster);
                        continue processing;
                    }
                }
                
                for(Cluster newCluster : newClusters){
                    if((newCluster.getAverageDistance()-totalAvergae)>clusterStdev && newCluster.getTags().size()>0){
                        System.out.println("bad cluster: "+(newCluster.getAverageDistance()-totalAvergae)+":"+newCluster.getTags().size());
                        badClusters.add(newCluster);
                    }
                    else{
                        goodClusters.add(newCluster);
                    }
                }
            }
        }
        System.out.println("final numbers of clusters="+goodClusters.size());
        //show result
        for (Cluster cluster : goodClusters) {
            System.out.println("cluster stdev="+cluster.getStdev()+", average distance="+cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs=new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                double distance=DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation());
                double stdevRatio=(distance-cluster.getAverageDistance())/cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for(TagStdevRatioPair pair : pairs){
                System.out.println(pair.getTag().getValue()+":"+pair.getStdevRatio());
            }
            System.out.println("==============================================");
        }
        totalAvergae=getAverageDistanceAcrossClusters(goodClusters);
        System.out.println("average distance across clusters="+(totalAvergae));
        return goodClusters;
        //report for distance between tags and all centroids
//        System.out.println("==============================================");
//        for(Node node : nodes){
//            System.out.print(node.getValue()+",");
//            boolean firstPrint=true;
//            for(Cluster cluster : goodClusters){
//                Centroid centroid=cluster.getCentroid();
//                double distance=DistanceCalculator.getDistance(node.getFeature(), centroid.getLocation());
//                if(!firstPrint){
//                    System.out.print(",");
//                }
//                System.out.print(distance);
//                firstPrint=false;
//            }
//            System.out.println("");
//        }
    }
    /**
     * calculate average distance across all clusters
     * @param clusters
     * @return 
     */
    protected double getAverageDistanceAcrossClusters(List<Cluster> clusters){
        double totalDistance=0;//accumulate distances between nodes and their centroids across clusters, this gives overall information
        double totalNumOfNodes=0;
        double totalAvergae=0;
        for (Cluster cluster : clusters) {
            System.out.println("cluster stdev="+cluster.getStdev()+", average distance="+cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs=new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                totalNumOfNodes++;
                double distance=DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation());
                totalDistance+=distance;
                double stdevRatio=(distance-cluster.getAverageDistance())/cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for(TagStdevRatioPair pair : pairs){
                System.out.println(pair.getTag().getValue()+":"+pair.getStdevRatio());
            }
            System.out.println("==============================================");
        }
        totalAvergae=totalDistance/totalNumOfNodes;
        return totalAvergae;
    }
}
