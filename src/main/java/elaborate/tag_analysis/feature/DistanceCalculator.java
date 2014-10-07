/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.feature;

import java.util.Arrays;

/**
 * calculate distance between two features
 * @author lendle
 */
public interface DistanceCalculator {
    public double getDistance(double [] feature1, double [] feature2);
}
