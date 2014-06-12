/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.keen_means.impl;

import elaborate.tag_analysis.kmeans.Cluster;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class ProblemSpace {

    private List<Cluster> goodClusters=new ArrayList<Cluster>();

    private List<Cluster> badClusters=new ArrayList<Cluster>();

    private double overallAverageDistance;

    private double overallStdev2AverageDistance;

    public ProblemSpace(List<Cluster> goodClusters, List<Cluster> badClusters, double overallAverageDistance, double overallStdev2AverageDistance) {
        this.goodClusters = goodClusters;
        this.badClusters = badClusters;
        this.overallAverageDistance = overallAverageDistance;
        this.overallStdev2AverageDistance = overallStdev2AverageDistance;
    }

    public ProblemSpace() {
    }
    
    

    /**
     * Get the value of overallStdev2AverageDistance
     *
     * @return the value of overallStdev2AverageDistance
     */
    public double getOverallStdev2AverageDistance() {
        return overallStdev2AverageDistance;
    }

    /**
     * Set the value of overallStdev2AverageDistance
     *
     * @param overallStdev2AverageDistance new value of
     * overallStdev2AverageDistance
     */
    public void setOverallStdev2AverageDistance(double overallStdev2AverageDistance) {
        this.overallStdev2AverageDistance = overallStdev2AverageDistance;
    }

    /**
     * Get the value of overallAverageDistance
     *
     * @return the value of overallAverageDistance
     */
    public double getOverallAverageDistance() {
        return overallAverageDistance;
    }

    /**
     * Set the value of overallAverageDistance
     *
     * @param overallAverageDistance new value of overallAverageDistance
     */
    public void setOverallAverageDistance(double overallAverageDistance) {
        this.overallAverageDistance = overallAverageDistance;
    }

    /**
     * Get the value of badClusters
     *
     * @return the value of badClusters
     */
    public List<Cluster> getBadClusters() {
        return badClusters;
    }

    /**
     * Set the value of badClusters
     *
     * @param badClusters new value of badClusters
     */
    public void setBadClusters(List<Cluster> badClusters) {
        this.badClusters = badClusters;
    }

    /**
     * Get the value of goodClusters
     *
     * @return the value of goodClusters
     */
    public List<Cluster> getGoodClusters() {
        return goodClusters;
    }

    /**
     * Set the value of goodClusters
     *
     * @param goodClusters new value of goodClusters
     */
    public void setGoodClusters(List<Cluster> goodClusters) {
        this.goodClusters = goodClusters;
    }

    public List<Cluster> getAllClusters(){
        List<Cluster> clusters=new ArrayList<Cluster>();
        clusters.addAll(this.goodClusters);
        clusters.addAll(this.badClusters);
        return clusters;
    }
}
