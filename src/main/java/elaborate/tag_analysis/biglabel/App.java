/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.biglabel;

import elaborate.tag_analysis.feature.DistanceCalculator;
import elaborate.tag_analysis.feature.DistanceCalculatorFactory;
import elaborate.tag_analysis.keen_means.KeenMeansCalculator;
import elaborate.tag_analysis.keen_means.impl.ClusterDistanceStdDevKeenMeansCalculatorImpl;
import elaborate.tag_analysis.kmeans.Centroid;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.Node;
import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;
import elaborate.tag_analysis.utils.KmeansNodesLoader;
import elaborate.tag_analysis.utils.reporting.ClusterReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lendle
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //System.out.println("current profile=" + Constant.PROFILE);
        //String keyword = Constant.PROFILE;
        String keyword = "test";
        int numOfClusters = 10;
        //get k-means nodes
        List<Node> nodes = KmeansNodesLoader.loadNodes(keyword + ".txt");
        KmeansCalculator kmeansCalculator = new DefaultKmeansCalculatorImpl();
        Centroid[] centroids = new Centroid[numOfClusters];
        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid(nodes.get(i).getFeature().getVector());
        }
        //perform k-means algorithm to get clusters
        List<Cluster> clusters = null;
        clusters = kmeansCalculator.calculate(Arrays.asList(centroids), nodes);
        KeenMeansCalculator keenMeansCalculator = new ClusterDistanceStdDevKeenMeansCalculatorImpl();
        //perform keen-means to get good clusters
        List<Cluster> goodClusters = keenMeansCalculator.calculate(clusters, kmeansCalculator);
        System.out.println("=======================================================");
        for (Cluster cluster : goodClusters) {
            System.out.println(cluster.getTags().size() + ":" + cluster.getStdev() + ":" + cluster.getAverageDistance());
            int count=0;
            for (Node node : cluster.getTags()) {
                double distance = DistanceCalculatorFactory.getDistanceCalculator().getDistance(cluster.getCentroid().getLocation(), node.getFeature().getVector());
                //System.out.println(node.getValue()+":"+Math.abs(distance - cluster.getAverageDistance())+":"+(Math.abs(distance - cluster.getAverageDistance()) < 1.0 * cluster.getStdev())+":"+(cluster.getDistance(node)<=cluster.getAverageDistance()));
                //if(cluster.getDistance(node)<=cluster.getAverageDistance()-2*cluster.getStdev()){
                    count++;
                    System.out.println(node.getValue()+","+cluster.getDistance(node)+","+cluster.getAverageDistance()+","+cluster.getStdev()+","+count);
                //}
                //System.out.println(DistanceCalculator.getDistance(goodClusters.get(0).getCentroid().getLocation(), node.getFeature())+"/"+goodClusters.get(0).getAverageDistance());
            }  
            System.out.println("=======================================================");
        }
        SimpleDateFormat formater=new SimpleDateFormat("yyyyMMddHHmmss");
        File reportFolder=new File("report", formater.format(new Date()));
        reportFolder.mkdirs();
        ClusterReporter.generateSingleHTMLReport(goodClusters, new File(reportFolder, "report.html"));
        
//        Cluster testCluster = goodClusters.get(3);
//        System.out.println(testCluster.getTags().size() + ":" + testCluster.getStdev() + ":" + testCluster.getAverageDistance());
//        for (Node node : testCluster.getTags()) {
//            double distance = DistanceCalculator.getDistance(testCluster.getCentroid().getLocation(), node.getFeature());
//            System.out.println(Math.abs(distance - testCluster.getAverageDistance()));
//            if (Math.abs(distance - testCluster.getAverageDistance()) < 1.0 * testCluster.getStdev()) {
//                System.out.println(node.getValue());
//            }
//            //System.out.println(DistanceCalculator.getDistance(goodClusters.get(0).getCentroid().getLocation(), node.getFeature())+"/"+goodClusters.get(0).getAverageDistance());
//        }
    }

}
