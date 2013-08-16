/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

/**
 *
 * @author lendle
 */
public class Centroid {

    private double[] location;

    public Centroid(double[] location) {
        this.location = location;
    }

    public Centroid() {
    }
    
    

    /**
     * Get the value of location
     *
     * @return the value of location
     */
    public double[] getLocation() {
        return location;
    }

    /**
     * Set the value of location
     *
     * @param location new value of location
     */
    public void setLocation(double[] location) {
        this.location = location;
    }

}
