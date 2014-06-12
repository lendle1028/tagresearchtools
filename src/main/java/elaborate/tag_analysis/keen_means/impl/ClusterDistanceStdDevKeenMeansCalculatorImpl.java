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
import elaborate.tag_analysis.kmeans.RandomCentroidSelectorImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * the implementation at first calculate overall average distance, OAVG, between
 * nodes and corresponding centroid and use stdev among distance for each
 * cluster, EAVG, to capture the distance variation among clusters if (|
 * EAVG[i]-OAVG | >= threshold * stdev), || (there exists a node in i for which
 * distance between the node and its cluster >= singleNodeThreshold*stdev ) then
 * cluster[i] is a bad cluster besides, if a cluster contains less than
 * stopCalculationWhenNodesNumberLessThan of nodes, it is always considered as a
 * good cluster
 *
 * @author DELL
 */
public class ClusterDistanceStdDevKeenMeansCalculatorImpl implements KeenMeansCalculator {

    private double threshold = 1.0;
    private double singleNodeThreshold = 2.0;
    private int stopCalculationWhenNodesNumberLessThan = 3;

    /**
     *
     * @param threshold
     */
    public ClusterDistanceStdDevKeenMeansCalculatorImpl(double threshold, double singleNodeThreshold, int stopCalculationWhenNodesNumberLessThan) {
        this.threshold = threshold;
        this.stopCalculationWhenNodesNumberLessThan = stopCalculationWhenNodesNumberLessThan;
        this.singleNodeThreshold = singleNodeThreshold;
    }

    /**
     * use default thresold=1.0 and minNodesInACluster=2 and
     * singleNodeThreshold=2.0
     */
    public ClusterDistanceStdDevKeenMeansCalculatorImpl() {
    }

    @Override
    public List<Cluster> calculate(List<Cluster> clusters, KmeansCalculator kmeansCalculator) {
        kmeansCalculator.setCentroidSelector(new RandomCentroidSelectorImpl());
        ProblemSpace context=new ProblemSpace();
        context.setGoodClusters(clusters);
        int round=0;
        round: while(true && round<10){
            round++;
            this.execute(kmeansCalculator, context);
            List<Cluster> allClusters=context.getAllClusters();
            for(Cluster cluster : allClusters){
                if(cluster.getTags().size()<this.stopCalculationWhenNodesNumberLessThan){
                    //this is a small group
                    continue round;
                }
            }
            //there is no small group left
            break;
        }
        return this.mergeNodesOfSmallClusters(context.getAllClusters());
        //return context.getGoodClusters();
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
     *
     * @param clusters
     * @return
     */
    protected double getAverageDistanceAcrossClusters(List<Cluster> clusters) {
        double totalDistance = 0;//accumulate distances between nodes and their centroids across clusters, this gives overall information
        double totalNumOfNodes = 0;
        double totalAverage = 0;
        for (Cluster cluster : clusters) {
            if (cluster.getTags().isEmpty()) {
                continue;
            }
            Logger.getLogger(this.getClass().getName()).fine("cluster stdev=" + cluster.getStdev() + ", average distance=" + cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs = new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                totalNumOfNodes++;
                double distance = Math.abs(DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation()));
                totalDistance += distance;
                double stdevRatio = (distance - cluster.getAverageDistance()) / cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for (TagStdevRatioPair pair : pairs) {
                Logger.getLogger(this.getClass().getName()).fine(pair.getTag().getValue() + ":" + pair.getStdevRatio() + ":" + cluster.getDistance((Node) pair.getTag()));
            }
            Logger.getLogger(this.getClass().getName()).fine("==============================================");
        }
        Logger.getLogger(this.getClass().getName()).fine("totalNumOfNodes=" + totalNumOfNodes + ", " + totalDistance + ", " + totalAverage);
        totalAverage = totalDistance / totalNumOfNodes;
        return totalAverage;
    }

    protected boolean isBadCluster(Cluster cluster, ProblemSpace context) {
        if (Math.abs((cluster.getAverageDistance() - context.getOverallAverageDistance())) >= threshold * context.getOverallStdev2AverageDistance() && cluster.getTags().size() >= stopCalculationWhenNodesNumberLessThan) {
            return true;
        } else {
            for (Node node : cluster.getTags()) {
                if (Math.abs(cluster.getDistance(node) - context.getOverallAverageDistance()) >= this.singleNodeThreshold * context.getOverallStdev2AverageDistance() && cluster.getTags().size() >= stopCalculationWhenNodesNumberLessThan) {
                    return true;
                }
            }
            return false;
        }
    }
    /**
     * execute for a round
     * @param context 
     */
    protected void execute(KmeansCalculator kmeansCalculator, ProblemSpace context){
        List<Cluster> clusters=this.mergeNodesOfSmallClusters(context.getAllClusters());
        double overallAverage = getAverageDistanceAcrossClusters(clusters);
        Logger.getLogger(this.getClass().getName()).info("average distance across clusters=" + (overallAverage));
        //calculate stdev of average distance among clusters
        double clusterVariations = 0;
        double overallStdev = 0;
        for (Cluster cluster : clusters) {
            if (Double.isNaN(cluster.getAverageDistance())) {
                continue;
            }
            clusterVariations += Math.pow(cluster.getAverageDistance() - overallAverage, 2);
        }

        overallStdev = Math.pow(clusterVariations, 0.5) / clusters.size();
        Logger.getLogger(this.getClass().getName()).info("stdev of average distance across clusters=" + (overallStdev));
        List<Cluster> badClusters = new ArrayList<Cluster>();
        List<Cluster> goodClusters = new ArrayList<Cluster>();
        int clusterIndex = 0;
        for (Cluster cluster : clusters) {
            if (this.isBadCluster(cluster, context)) {
                //this is a bad cluster
                Logger.getLogger(this.getClass().getName()).info("bad cluster: " + clusterIndex + ":" + (cluster.getAverageDistance() - overallAverage) + ":" + (+(cluster.getAverageDistance() - overallAverage) / overallStdev));
                badClusters.add(cluster);
            } else {
                goodClusters.add(cluster);
            }
            clusterIndex++;
        }
        
        while (badClusters.isEmpty() == false) {
            Logger.getLogger(this.getClass().getName()).info("initially, # of bad clusters=" + badClusters.size() + ": # of good clusters=" + goodClusters.size());
            List<Cluster> processingClusters = new ArrayList<Cluster>(badClusters);
            badClusters.clear();
            processing:
            for (Cluster cluster : processingClusters) {
                if (cluster.getTags().size() < this.stopCalculationWhenNodesNumberLessThan) {
                    goodClusters.add(cluster);
                    continue processing;
                }
                List<Cluster> newClusters = kmeansCalculator.calculate(2, cluster);
                if (newClusters.size() < 2) {
                    //not splittable
                    goodClusters.add(cluster);
                    continue processing;
                }
                //verify
                for (Cluster newCluster : newClusters) {
                    if (this.isBadCluster(cluster, context)) {
                        Logger.getLogger(this.getClass().getName()).info("bad cluster: " + (newCluster.getAverageDistance() - overallAverage) + ":" + newCluster.getTags().size());
                        badClusters.add(newCluster);
                    } else {
                        goodClusters.add(newCluster);
                    }
                }
            }
        }
        Logger.getLogger(this.getClass().getName()).info("final numbers of clusters=" + goodClusters.size() + ", total average=" + overallAverage+", badClusters="+badClusters.size());
        context.setGoodClusters(goodClusters);
        context.setBadClusters(badClusters);
        context.setOverallAverageDistance(overallAverage);
        context.setOverallStdev2AverageDistance(overallStdev);
    }
    
    /**
     * move nodes of small clusters to the nearest cluster
     *
     * @param original
     * @return
     */
    protected List<Cluster> mergeNodesOfSmallClusters(List<Cluster> original) {
        if(1==1){
            return original;
        }
        List<Cluster> result = new ArrayList<Cluster>();
        List<Cluster> smallClusters = new ArrayList<Cluster>();
        for (Cluster cluster : original) {
            if (cluster.getTags().size() < this.stopCalculationWhenNodesNumberLessThan) {
                smallClusters.add(cluster);
            } else {
                result.add(cluster);
            }
        }
        for (Cluster cluster : smallClusters) {
            for (Node node : cluster.getTags()) {
                double minDistance = Double.MAX_VALUE;
                Cluster targetCluster = null;
                for (Cluster largerCluster : result) {
                    double distance = largerCluster.getDistance(node);
                    if (distance < minDistance) {
                        minDistance = distance;
                        targetCluster = largerCluster;
                    }
                }
                targetCluster.addTag(node);
            }
        }
        return result;
    }
}
