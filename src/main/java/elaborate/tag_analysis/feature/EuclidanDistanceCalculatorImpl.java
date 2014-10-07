/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.feature;

/**
 *
 * @author lendle
 */
public class EuclidanDistanceCalculatorImpl implements DistanceCalculator{
    public  double getDistance(double [] feature1, double [] feature2){
        double sum = 0;
        for (int i = 0; i < feature1.length; i++) {
            sum += Math.pow(feature1[i] - feature2[i], 2);
        }
        double result = Math.pow(sum, 0.5);
        return result;
    }
}
