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
    private int stopCalculationWhenNodesNumberLessThan = 5;

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
        //kmeansCalculator.setCentroidSelector(new RandomCentroidSelectorImpl());
        ProblemSpace context = new ProblemSpace();
        context.setGoodClusters(clusters);
        //@todo: the current merge algorithm has defects
//        int round = 0;
//        round:
//        while (true && round < 10) {
//            round++;
//            this.execute(kmeansCalculator, context);
//            List<Cluster> allClusters = context.getAllClusters();
//            for (Cluster cluster : allClusters) {
//                if (cluster.getTags().size() < this.stopCalculationWhenNodesNumberLessThan) {
//                    //this is a small group
//                    continue round;
//                }
//            }
//            //there is no small group left
//            break;
//        }
        
        List<Cluster> result = null;
        this.execute(kmeansCalculator, context);
        result=context.getGoodClusters();
//        for (int i = 0; i < 10; i++) {
//            this.execute(kmeansCalculator, context);
//            result = this.mergeNodesOfSmallClusters(this.splitLongTails(context.getAllClusters()));
//            context.setGoodClusters(result);
//        }
        return result;
        
        //return this.mergeNodesOfSmallClusters(result);
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
                double distance = Math.abs(DistanceCalculator.getDistance(node.getFeature().getVector(), cluster.getCentroid().getLocation()));
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
            //attack outliers
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
     *
     * @param context
     */
    protected void execute(KmeansCalculator kmeansCalculator, ProblemSpace context) {
        //List<Cluster> clusters=this.mergeNodesOfSmallClusters(context.getAllClusters());
        List<Cluster> originalClusters = context.getAllClusters();
        List<Cluster> clusters = originalClusters;
//        List<Cluster> clusters = new ArrayList<>();
//        for (Cluster cluster : originalClusters) {
//            if (cluster.getTags().size() > this.stopCalculationWhenNodesNumberLessThan) {
//                boolean longTailExists = false;
//                for (Node node : cluster.getTags()) {
//                    if (cluster.getDistance(node) > cluster.getAverageDistance() + 3 * cluster.getStdev()) {
//                        longTailExists = true;
//                        break;
//                    }
//                }
//                if (longTailExists) {
//                    Cluster newCluster1 = new Cluster();
//                    Cluster newCluster2 = new Cluster();
//                    for (Node node : cluster.getTags()) {
//                        if (cluster.getDistance(node) > cluster.getAverageDistance() + 3 * cluster.getStdev()) {
//                            newCluster2.getTags().add(node);
//                        }else{
//                            newCluster1.getTags().add(node);
//                        }
//                    }
//                    newCluster1.reset();
//                    newCluster2.reset();
//                    clusters.add(newCluster1);
//                    clusters.add(newCluster2);
//                    System.out.println("long tail: "+newCluster1.getTags().size()+":"+newCluster2.getTags().size());
//                }else{
//                    clusters.add(cluster);
//                }
//            } else {
//                clusters.add(cluster);
//            }
//        }

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
                //Logger.getLogger(this.getClass().getName()).info("bad cluster: " + clusterIndex + ":" + (cluster.getAverageDistance() - overallAverage) + ":" + (+(cluster.getAverageDistance() - overallAverage) / overallStdev));
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
                        Logger.getLogger(this.getClass().getName()).info("bad cluster: " + (newCluster.getAverageDistance() - overallAverage) + ":" + newCluster.getTags().size() + ", " + cluster.getStdev());
                        badClusters.add(newCluster);
                    } else {
                        goodClusters.add(newCluster);
                    }
                }
            }
        }

        Logger.getLogger(this.getClass().getName()).info("final numbers of clusters=" + goodClusters.size() + ", total average=" + overallAverage + ", badClusters=" + badClusters.size());
        context.setGoodClusters(goodClusters);
        context.setBadClusters(badClusters);
        context.setOverallAverageDistance(overallAverage);
        context.setOverallStdev2AverageDistance(overallStdev);
    }

    /**
     * split long tails (>3 stdev) to new clusters
     *
     * @param original
     * @return
     */
    public List<Cluster> splitLongTails(List<Cluster> original) {
        List<Cluster> result = new ArrayList<Cluster>();
        for (Cluster cluster : original) {
            Cluster centralCluster = new Cluster();
            Cluster longTail = new Cluster();
            for (Node node : cluster.getTags()) {
                double distance = cluster.getDistance(node);
                if (distance > (2 * cluster.getStdev() + cluster.getAverageDistance())) {
                    longTail.getTags().add(node);
                } else {
                    centralCluster.getTags().add(node);
                }
            }
            if (longTail.getTags().isEmpty() == false) {
                //longTail
                longTail.reset();
                result.add(longTail);
            }
            if (centralCluster.getTags().isEmpty() == false) {
                //centralCluster
                centralCluster.reset();
                result.add(centralCluster);
            }
        }
        return result;
    }

    /**
     * move nodes of small clusters to the nearest cluster
     *
     * @param original
     * @return
     */
    protected List<Cluster> mergeNodesOfSmallClusters(List<Cluster> original) {
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
            List<Node> leftNodes = new ArrayList<Node>();
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
                if (targetCluster != null) {
                    targetCluster.getTags().add(node);
                    targetCluster.reset();
                }
            }
        }
        return result;
    }
}
