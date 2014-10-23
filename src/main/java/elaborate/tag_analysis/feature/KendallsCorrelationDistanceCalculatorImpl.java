/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.feature;

import org.apache.commons.math3.stat.correlation.KendallsCorrelation;


/**
 *
 * @author lendle
 */
public class KendallsCorrelationDistanceCalculatorImpl implements DistanceCalculator {

    @Override
    public double getDistance(double[] feature1, double[] feature2) {
        KendallsCorrelation p=new KendallsCorrelation();
        double cor=p.correlation(feature1, feature2);
        return 1.0/(cor+2);//prevent negative value
    }
    
}
