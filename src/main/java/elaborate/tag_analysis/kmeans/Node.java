/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

/**
 * a k-means node
 * @author lendle
 */
public class Node extends elaborate.tag_analysis.Tag{
    private double[] feature;

    public double[] getFeature() {
        return feature;
    }

    public void setFeature(double[] feature) {
        this.feature = feature;
    }
    
}
