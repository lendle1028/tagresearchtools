/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

import elaborate.tag_analysis.feature.DistanceCalculator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class Cluster {

    private Centroid centroid;
    private List<Node> tags = new ArrayList<Node>();
    private double avg=-1,  stdev=-1;
    private boolean avgCalculated=false;
    private boolean stdevCalculated=false;

    /**
     * Get the value of tags
     *
     * @return the value of tags
     */
    public List<Node> getTags() {
        return tags;
    }

    /**
     * Set the value of tags
     *
     * @param tags new value of tags
     */
    public void setTags(List<Node> tags) {
        this.tags = tags;
    }

    /**
     * Get the value of centroid
     *
     * @return the value of centroid
     */
    public Centroid getCentroid() {
        return centroid;
    }

    /**
     * Set the value of centroid
     *
     * @param centroid new value of centroid
     */
    public void setCentroid(Centroid centroid) {
        this.centroid = centroid;
    }

    /**
     * return the distance between the tag to the centroid of this cluster
     *
     * @param tag
     * @return
     */
    public double getDistance(Node tag) {
        double[] feature1 = tag.getFeature();
        double[] feature2 = this.centroid.getLocation();
        return DistanceCalculator.getDistance(feature1, feature2);
    }

    /**
     * return average distance between tags in this cluster and the centroid
     *
     * @return
     */
    public double getAverageDistance() {
        if(this.avgCalculated){
            return this.avg;
        }else{
            this.avg=this.calculateAverageDistance();
            avgCalculated=true;
            return this.avg;
        }
    }

    /**
     * return stdev of distance in this cluster
     *
     * @return
     */
    public double getStdev() {
        if(this.stdevCalculated){
            return this.stdev;
        }else{
            this.stdev=this.calculateStdev();
            this.stdevCalculated=true;
            return this.stdev;
        }
    }

    /**
     * reset information: centroid, average distance, stdev
     * invoke the method after tags are modified
     */
    public void reset(){
        double[] newSum = new double[this.tags.get(0).getFeature().length];
        double[] newCentroid = new double[this.tags.get(0).getFeature().length];
        for (Node tag : this.tags) {
            for (int i = 0; i < newSum.length; i++) {
                newSum[i] += tag.getFeature()[i];
            }
        }
        for (int i = 0; i < newSum.length; i++) {
            newCentroid[i]=newSum[i]/this.tags.size();
        }
        if(this.centroid==null){
            this.centroid=new Centroid();
        }
        this.centroid.setLocation(newCentroid);
        this.avg=this.calculateAverageDistance();
        this.stdev=this.calculateStdev();
    }
    
    protected double calculateAverageDistance() {
        double sum = 0;
        for (Node tag : this.tags) {
            sum += this.getDistance(tag);
        }
        return sum / tags.size();
    }
    
    protected double calculateStdev() {
        double avg = this.getAverageDistance();
        double sum = 0;
        for (Node tag : this.tags) {
            sum += Math.pow(this.getDistance(tag) - avg, 2);
        }
        sum = Math.pow(sum, 0.5);
        return sum / this.tags.size();
    }
}
