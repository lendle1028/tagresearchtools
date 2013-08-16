/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans;

import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;

/**
 *
 * @author lendle
 */
public class KmeansCalculatorFactory {

    public static KmeansCalculator newCalculator() {
        return new DefaultKmeansCalculatorImpl();
    }
}
