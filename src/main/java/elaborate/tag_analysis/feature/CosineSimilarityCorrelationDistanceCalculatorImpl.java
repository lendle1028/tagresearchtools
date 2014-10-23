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
public class CosineSimilarityCorrelationDistanceCalculatorImpl implements DistanceCalculator {

    @Override
    public double getDistance(double[] feature1, double[] feature2) {
        double dotProduct=0;
        for(int i=0; i<feature1.length; i++){
            dotProduct+=feature1[i]*feature2[i];
        }
        double [] baseVector=new double[feature1.length];
        EuclidanDistanceCalculatorImpl ed=new EuclidanDistanceCalculatorImpl();
        
        double distance1=ed.getDistance(feature1, baseVector);
        double distance2=ed.getDistance(feature2, baseVector);
        
        double cor=dotProduct/(distance1*distance2);
        return 1.0/(cor+2);//prevent negative value
    }
    
}
