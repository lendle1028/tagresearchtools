/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elaborate.tag_analysis.biglabel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import elaborate.tag_analysis.Constant;
import elaborate.tag_analysis.LinkData;
import elaborate.tag_analysis.Tag;
import elaborate.tag_analysis.feature.DistanceCalculator;
import elaborate.tag_analysis.keen_means.KeenMeansCalculator;
import elaborate.tag_analysis.keen_means.impl.ClusterDistanceStdDevKeenMeansCalculatorImpl;
import elaborate.tag_analysis.kmeans.Centroid;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.KmeansNodesFactory;
import elaborate.tag_analysis.kmeans.Node;
import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;
import elaborate.tag_analysis.utils.KmeansNodesLoader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lendle
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        //System.out.println("current profile=" + Constant.PROFILE);
        //String keyword = Constant.PROFILE;
        String keyword="java";
        int numOfClusters = 10;
        //get k-means nodes
        List<Node> nodes = KmeansNodesLoader.loadNodes(keyword+".txt");
        KmeansCalculator kmeansCalculator = new DefaultKmeansCalculatorImpl();
        Centroid[] centroids = new Centroid[numOfClusters];
        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid(nodes.get(i).getFeature());
        }
        //perform k-means algorithm to get clusters
        List<Cluster> clusters = null;
        clusters = kmeansCalculator.calculate(Arrays.asList(centroids), nodes);
        KeenMeansCalculator keenMeansCalculator = new ClusterDistanceStdDevKeenMeansCalculatorImpl();
        //perform keen-means to get good clusters
        List<Cluster> goodClusters = keenMeansCalculator.calculate(clusters, kmeansCalculator);
        for(Cluster cluster : goodClusters){
            System.out.println(cluster.getTags().size());
        }
        Cluster testCluster=goodClusters.get(0);
        //System.out.println(testCluster.getTags().size());
        for(Node node : testCluster.getTags()){
            double distance=DistanceCalculator.getDistance(testCluster.getCentroid().getLocation(), node.getFeature());
            if(Math.abs(distance-testCluster.getAverageDistance())<0.3*testCluster.getStdev()){
                System.out.println(node.getValue());
            }
            //System.out.println(DistanceCalculator.getDistance(goodClusters.get(0).getCentroid().getLocation(), node.getFeature())+"/"+goodClusters.get(0).getAverageDistance());
        }
    }
    
}
