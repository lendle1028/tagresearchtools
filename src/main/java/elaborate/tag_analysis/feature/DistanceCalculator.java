/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.feature;

/**
 * calculate distance between two features
 * @author lendle
 */
public class DistanceCalculator {
    public static double getDistance(double [] feature1, double [] feature2){
        double sum = 0;
        for (int i = 0; i < feature1.length; i++) {
            sum += Math.pow(feature1[i] - feature2[i], 2);
        }
        double result = Math.pow(sum, 0.5);
        return result;
    }
}
