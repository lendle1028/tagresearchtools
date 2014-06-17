/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.feature;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Feature {
    private double [] vector=null;
    private List<URL> urls=new ArrayList<URL>();

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }
    
}
