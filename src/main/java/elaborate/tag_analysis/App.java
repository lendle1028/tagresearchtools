package elaborate.tag_analysis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import elaborate.tag_analysis.feature.DistanceCalculator;
import elaborate.tag_analysis.kmeans.Centroid;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.KmeansNodesFactory;
import elaborate.tag_analysis.kmeans.Node;
import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * read tag data, perform k-means calculation
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("current profile="+Constant.PROFILE);
        String keyword = Constant.PROFILE;
        int numOfClusters = 10;
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(keyword + ".txt"), "utf-8"));
        String json = input.readLine();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<LinkData>>() {
        }.getType();
        List<LinkData> list = new ArrayList<LinkData>();
        Map<String, String> tags = new HashMap<String, String>();
        list = gson.fromJson(json, listType);
        input.close();

        for (LinkData linkData : list) {
            //System.out.println(linkData.getUrl());
            for (Tag tag : linkData.getTags()) {
                String tagValue = tag.getValue();
                tags.put(tagValue, "");
            }
        }
        System.out.println(tags.size() + "/" + list.size());
        //get k-means nodes
        List<Node> nodes = KmeansNodesFactory.getNodes(list);
        KmeansCalculator kmeansCalculator = new DefaultKmeansCalculatorImpl();
        Centroid[] centroids = new Centroid[numOfClusters];
        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid(nodes.get(i).getFeature());
        }
        //perform k-means algorithm to get clusters
        List<Cluster> clusters = null;
        for (int i = 0; i < 30; i++) {
            clusters = kmeansCalculator.calculate(Arrays.asList(centroids), nodes);
            int index = 0;
            for (Cluster cluster : clusters) {
                double[] newCentroid = new double[list.size()];
                List<Node> clusterNodes = cluster.getTags();
                for (Node node : clusterNodes) {
                    for (int j = 0; j < newCentroid.length; j++) {
                        newCentroid[j] += node.getFeature()[j];
                    }
                }
                for (int j = 0; j < newCentroid.length; j++) {
                    newCentroid[j] /= list.size();
                }
                centroids[index++] = new Centroid(newCentroid);
            }
        }
        //post processing, calculate average distances
        double totalAvergae=getAverageDistanceAcrossClusters(clusters);
        System.out.println("average distance across clusters="+(totalAvergae));
        //calculate stdev of average distance among clusters
        double clusterVariations=0;
        double clusterStdev=0;
        for (Cluster cluster : clusters) {
            if(Double.isNaN(cluster.getAverageDistance())){
                continue;
            }
            clusterVariations+=Math.pow(cluster.getAverageDistance()-totalAvergae, 2);
        }
        clusterStdev=Math.pow(clusterVariations, 0.5)/clusters.size();
        System.out.println("stdev of average distance across clusters="+(clusterStdev));
        List<Cluster> badClusters=new ArrayList<Cluster>();
        List<Cluster> goodClusters=new ArrayList<Cluster>();
        int clusterIndex=0;
        for (Cluster cluster : clusters) {
            if((cluster.getAverageDistance()-totalAvergae)>clusterStdev){
                //this is a bad cluster
                System.out.println("bad cluster: "+clusterIndex+":"+(cluster.getAverageDistance()-totalAvergae)+":"+(+(cluster.getAverageDistance()-totalAvergae)/clusterStdev));
                badClusters.add(cluster);
            }
            else{
                goodClusters.add(cluster);
            }
            clusterIndex++;
        }
        //recalculate bad clusters
        while(badClusters.isEmpty()==false){
            System.out.println("# of bad clusters="+badClusters.size());
            List<Cluster> processingClusters=new ArrayList<Cluster>(badClusters);
            badClusters.clear();
            processing: for(Cluster cluster : processingClusters){
                List<Cluster> newClusters = kmeansCalculator.calculate(2, cluster);
                //verify
                for(Cluster newCluster : newClusters){
                    if(newCluster.getTags().size()<=1){
                        goodClusters.add(cluster);
                        continue processing;
                    }
                }
                
                for(Cluster newCluster : newClusters){
                    if((newCluster.getAverageDistance()-totalAvergae)>clusterStdev && newCluster.getTags().size()>0){
                        System.out.println("bad cluster: "+(newCluster.getAverageDistance()-totalAvergae)+":"+newCluster.getTags().size());
                        badClusters.add(newCluster);
                    }
                    else{
                        goodClusters.add(newCluster);
                    }
                }
            }
        }
        System.out.println("final numbers of clusters="+goodClusters.size());
        //show result
        for (Cluster cluster : goodClusters) {
            System.out.println("cluster stdev="+cluster.getStdev()+", average distance="+cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs=new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                double distance=DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation());
                double stdevRatio=(distance-cluster.getAverageDistance())/cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for(TagStdevRatioPair pair : pairs){
                System.out.println(pair.getTag().getValue()+":"+pair.getStdevRatio());
            }
            System.out.println("==============================================");
        }
        totalAvergae=getAverageDistanceAcrossClusters(goodClusters);
        System.out.println("average distance across clusters="+(totalAvergae));
        //report for distance between tags and all centroids
        System.out.println("==============================================");
        for(Node node : nodes){
            System.out.print(node.getValue()+",");
            boolean firstPrint=true;
            for(Cluster cluster : goodClusters){
                Centroid centroid=cluster.getCentroid();
                double distance=DistanceCalculator.getDistance(node.getFeature(), centroid.getLocation());
                if(!firstPrint){
                    System.out.print(",");
                }
                System.out.print(distance);
                firstPrint=false;
            }
            System.out.println("");
        }
    }
    
    public static double getAverageDistanceAcrossClusters(List<Cluster> clusters){
        double totalDistance=0;//accumulate distances between nodes and their centroids across clusters, this gives overall information
        double totalNumOfNodes=0;
        double totalAvergae=0;
        for (Cluster cluster : clusters) {
            System.out.println("cluster stdev="+cluster.getStdev()+", average distance="+cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs=new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                totalNumOfNodes++;
                double distance=DistanceCalculator.getDistance(node.getFeature(), cluster.getCentroid().getLocation());
                totalDistance+=distance;
                double stdevRatio=(distance-cluster.getAverageDistance())/cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for(TagStdevRatioPair pair : pairs){
                System.out.println(pair.getTag().getValue()+":"+pair.getStdevRatio());
            }
            System.out.println("==============================================");
        }
        totalAvergae=totalDistance/totalNumOfNodes;
        return totalAvergae;
    }
}
