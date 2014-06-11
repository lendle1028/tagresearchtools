/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.keen_means.impl;

import elaborate.tag_analysis.TagStdevRatioPair;
import elaborate.tag_analysis.feature.DistanceCalculator;
import elaborate.tag_analysis.keen_means.KeenMeansCalculator;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * the implementation at first calculate overall average distance, OAVG, between nodes
 * and corresponding centroid
 * and use stdev among distance for each cluster, EAVG, to capture the distance variation among clusters
 * if | EAVG[i]-OAVG | >= threshold * stdev, then cluster[i] is a bad cluster
 * besides, if a cluster contains less than stopCalculationWhenNodesNumberLessThan of nodes, it is always considered as a good
 * cluster
 * @author DELL
 */
public class ClusterDistanceStdDevKeenMeansCalculatorImpl implements KeenMeansCalculator{
    private double threshold=1.0;
    private int stopCalculationWhenNodesNumberLessThan=1;
    
    /**
     * 
     * @param threshold 
     */
    public ClusterDistanceStdDevKeenMeansCalculatorImpl(double threshold, int stopCalculationWhenNodesNumberLessThan) {
        this.threshold=threshold;
        this.stopCalculationWhenNodesNumberLessThan=stopCalculationWhenNodesNumberLessThan;
    }
    /**
     * use default thresold=1.0 and minNodesInACluster=1
     */
    public ClusterDistanceStdDevKeenMeansCalculatorImpl() {
    }
    
    @Override
    public List<Cluster> calculate(List<Cluster> clusters, KmeansCalculator kmeansCalculator) {
        //post processing, calculate average distances
        double totalAverage=getAverageDistanceAcrossClusters(clusters);
        Logger.getLogger(this.getClass().getName()).info("average distance across clusters="+(totalAverage));
        //calculate stdev of average distance among clusters
        double clusterVariations=0;
        double clusterStdev=0;
        for (Cluster cluster : clusters) {
            if(Double.isNaN(cluster.getAverageDistance())){
                continue;
            }
            clusterVariations+=Math.pow(cluster.getAverageDistance()-totalAverage, 2);
        }
        clusterStdev=Math.pow(clusterVariations, 0.5)/clusters.size();
        Logger.getLogger(this.getClass().getName()).info("stdev of average distance across clusters="+(clusterStdev));
        List<Cluster> badClusters=new ArrayList<Cluster>();
        List<Cluster> goodClusters=new ArrayList<Cluster>();
        int clusterIndex=0;
        for (Cluster cluster : clusters) {
            if(Math.abs((cluster.getAverageDistance()-totalAverage))>threshold*clusterStdev){
                //this is a bad cluster
                Logger.getLogger(this.getClass().getName()).info("bad cluster: "+clusterIndex+":"+(cluster.getAverageDistance()-totalAverage)+":"+(+(cluster.getAverageDistance()-totalAverage)/clusterStdev));
                badClusters.add(cluster);
            }
            else{
                //if there is an extraordinary far-away node, it is a bad cluster
                /*for(Node node : cluster.getTags()){
                    double nodeDistance=DistanceCalculator.getDistance(cluster.getCentroid().getLocation(), feature2)
                }*/
                goodClusters.add(cluster);
            }
            clusterIndex++;
        }
        //recalculate bad clusters
        while(badClusters.isEmpty()==false){
            Logger.getLogger(this.getClass().getName()).info("# of bad clusters="+badClusters.size());
            List<Cluster> processingClusters=new ArrayList<Cluster>(badClusters);
            badClusters.clear();
            processing: for(Cluster cluster : processingClusters){
                List<Cluster> newClusters = kmeansCalculator.calculate(2, cluster);
                //verify
                for(Cluster newCluster : newClusters){
                    if(newCluster.getTags().size()<=this.stopCalculationWhenNodesNumberLessThan){
                        goodClusters.add(cluster);
                        continue processing;
                    }
                }
                
                for(Cluster newCluster : newClusters){
                    if((newCluster.getAverageDistance()-totalAverage)>clusterStdev && newCluster.getTags().size()>0){
                        Logger.getLogger(this.getClass().getName()).info("bad cluster: "+(newCluster.getAverageDistance()-totalAverage)+":"+newCluster.getTags().size());
                        badClusters.add(newCluster);
                    }
                    else{
                        goodClusters.add(newCluster);
                    }
                }
            }
        }
        Logger.getLogger(this.getClass().getName()).info("final numbers of clusters="+goodClusters.size());
        //show result
//        for (Cluster cluster : goodClusters) {
//            Logger.getLogger(this.getClass().getName()).info("cluster stdev="+cluster.getStdev()+", average distance="+cluster.getAverageDistance());
//            List<TagStdevRatioPair> pairs=new ArrayList<TagStdevRatioPair>();
//            //calculate distance between every node and its centroid
//            for (Node node : cluster.getTags()) {
//                double distance=DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation());
//                double stdevRatio=(distance-cluster.getAverageDistance())/cluster.getStdev();
//                pairs.add(new TagStdevRatioPair(node, stdevRatio));
//            }
//            Collections.sort(pairs);
//            for(TagStdevRatioPair pair : pairs){
//                Logger.getLogger(this.getClass().getName()).info(pair.getTag().getValue()+":"+pair.getStdevRatio());
//            }
//            Logger.getLogger(this.getClass().getName()).info("==============================================");
//        }
//        totalAverage=getAverageDistanceAcrossClusters(goodClusters);
//        Logger.getLogger(this.getClass().getName()).info("average distance across clusters="+(totalAverage));
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
        double totalAverage=0;
        for (Cluster cluster : clusters) {
            if(cluster.getTags().isEmpty()){
                continue;
            }
            Logger.getLogger(this.getClass().getName()).info("cluster stdev="+cluster.getStdev()+", average distance="+cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs=new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                totalNumOfNodes++;
                double distance=Math.abs(DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation()));
                totalDistance+=distance;
                double stdevRatio=(distance-cluster.getAverageDistance())/cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for(TagStdevRatioPair pair : pairs){
                Logger.getLogger(this.getClass().getName()).info(pair.getTag().getValue()+":"+pair.getStdevRatio());
            }
            Logger.getLogger(this.getClass().getName()).info("==============================================");
        }
        Logger.getLogger(this.getClass().getName()).info("totalNumOfNodes="+totalNumOfNodes+", "+totalDistance+", "+totalAverage);
        totalAverage=totalDistance/totalNumOfNodes;
        return totalAverage;
    }
}
