/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

import elaborate.tag_analysis.feature.DistanceCalculator;
import elaborate.tag_analysis.feature.DistanceCalculatorFactory;
import elaborate.tag_analysis.utils.ClusterUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private boolean urlCalculated=false;
    //never use the variable directly
    private Map<URL, String> _urls=new HashMap<URL, String>();
    private List<Node> sortedTagList=null;

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
        double[] feature1 = tag.getFeature().getVector();
        double[] feature2 = this.centroid.getLocation();
        return DistanceCalculatorFactory.getDistanceCalculator().getDistance(feature1, feature2);
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
        sortedTagList=null;
        if(this.tags.isEmpty()){
            return;
        }
        double[] newSum = new double[this.tags.get(0).getFeature().getVector().length];
        double[] newCentroid = new double[this.tags.get(0).getFeature().getVector().length];
        for (Node tag : this.tags) {
            for (int i = 0; i < newSum.length; i++) {
                newSum[i] += tag.getFeature().getVector()[i];
            }
        }
        for (int i = 0; i < newSum.length; i++) {
            newCentroid[i]=newSum[i]/this.tags.size();
        }
        //System.out.println(Arrays.toString(newSum)+":"+newCentroid[0]+":"+this.tags.size());
        if(this.centroid==null){
            this.centroid=new Centroid();
        }
        this.centroid.setLocation(newCentroid);
        this.avg=this.calculateAverageDistance();
        this.stdev=this.calculateStdev();
        
        this.urlCalculated=false;//lazy calculation of covered urls
    }
    /**
     * return all urls covered by this cluster
     * @return 
     */
    public List<URL> getCoveredURLs(){
        if(this.urlCalculated==true){
            return new ArrayList<URL>(this._urls.keySet());
        }else{
            this._urls.clear();
            for(Node tag : this.tags){
                List<URL> urls=tag.getFeature().getUrls();
                for(URL url : urls){
                    _urls.put(url, "");
                }
            }
            this.urlCalculated=true;
            return new ArrayList<URL>(this._urls.keySet());
        }
    }
    /**
     * return % of urls of this cluster covered by the given tag
     * @param tag
     * @return 
     */
    public double getURLCoverage(Node tag){
        this.getCoveredURLs();//force calculation
        double count=0;
        for(URL url : tag.getFeature().getUrls()){
            if(this._urls.containsKey(url)){
                count++;
            }
        }
        return count/_urls.size();
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

    @Override
    public String toString() {
        if(this.getTags()==null || this.getTags().isEmpty()){
            return "empty";
        }
        return getSortedTagsAccording2Distance().get(0).getValue();
        //return this.tags.get(0).getValue();
    }
    
    public List<Node> getSortedTagsAccording2Distance(){
        if(sortedTagList==null){
            sortedTagList=ClusterUtil.sortTagsByDistance(this);
        }
        return sortedTagList;
    }
    
}
