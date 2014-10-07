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
public class DistanceCalculatorFactory {
    private static DistanceCalculator instance=null;
    
    public static DistanceCalculator getDistanceCalculator(){
        if(instance==null){
            //instance=new EuclidanDistanceCalculatorImpl();
            instance=new PearsonCorrelationDistanceCalculatorImpl();
        }
        return instance;
    }
}
