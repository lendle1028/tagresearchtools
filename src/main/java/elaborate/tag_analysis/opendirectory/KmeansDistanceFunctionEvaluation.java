/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.opendirectory;

import elaborate.tag_analysis.kmeans.Centroid;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.Node;
import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;
import elaborate.tag_analysis.utils.KmeansNodesLoader;
import elaborate.tag_analysis.utils.StringAlgorithmUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lendle
 */
public class KmeansDistanceFunctionEvaluation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        List<URLGroup> urlGroups=URLGroupsLoader.loadURLGroups();
        
        //System.out.println(getIdentifyingStringForURLs(urlGroups) );
        
        List<Node> nodes = KmeansNodesLoader.loadNodes("opendirectory_tags.txt");
        Centroid[] centroids = new Centroid[4];
        KmeansCalculator kmeansCalculator = new DefaultKmeansCalculatorImpl();
        for (Node node : nodes) {
            if (node.getValue().equals("web")) {
                centroids[0] = new Centroid(node.getFeature().getVector());
            } else if (node.getValue().equals("photography")) {
                centroids[1] = new Centroid(node.getFeature().getVector());
            } else if (node.getValue().equals("health")) {
                centroids[2] = new Centroid(node.getFeature().getVector());
            } else if (node.getValue().equals("boulder")) {
                centroids[3] = new Centroid(node.getFeature().getVector());
            }
        }
        
        List<Cluster> clusters = kmeansCalculator.calculate(Arrays.asList(centroids), nodes);
        List<URLGroup> newURLGroups=new ArrayList<>();
        
        Map<URL, URLClusterScore> urlClusterScoreMap = new HashMap<>();
        for (int i = 0; i < clusters.size(); i++) {
            newURLGroups.add(new URLGroup());
            Cluster cluster = clusters.get(i);
            for (Node tag : cluster.getTags()) {
                for (URL url : tag.getFeature().getUrls()) {
                    
                    URLClusterScore score = urlClusterScoreMap.get(url);
                    if (score == null) {
                        score = new URLClusterScore();
                        score.url=url;
                        urlClusterScoreMap.put(url, score);
                    }

                    score.scores[i]++;
                }
            }
        }
        
        for (Map.Entry entry : urlClusterScoreMap.entrySet()) {
            URLClusterScore score = (URLClusterScore) entry.getValue();
            //System.out.println(entry.getKey() + ":" + Arrays.toString(score.scores));
            int maxScoreIndex=-1;
            double maxScore=-1;
            for(int i=0; i<score.scores.length; i++){
                if(score.scores[i]>maxScore){
                    maxScore=score.scores[i];
                    maxScoreIndex=i;
                }
            }
            newURLGroups.get(maxScoreIndex).add(score.url);
        }
        List<URL> newURLList=new ArrayList<>();
        for (URLGroup group : newURLGroups) {
            for (URL url : group.toArray(new URL[0])) {
                newURLList.add(url);
            }
        }
        for(URLGroup group : urlGroups){
            group.preserveOnly(newURLList);
        }
        ClusterPerformanceEvaluator clusterPerformanceEvaluator=new ClusterPerformanceEvaluator();
        System.out.println(clusterPerformanceEvaluator.evaluate(urlGroups, newURLGroups));
//        List<URL> newURLList=new ArrayList<>();
//        for(URLGroup group : newURLGroups){
//            for(URL url : group.toArray(new URL[0])){
//                newURLList.add(url);
//            }
//        }
//        
//        for(URLGroup group : urlGroups){
//            group.preserveOnly(newURLList);
//        }
//        String result1=URLGroupUtils.getIdentifyingStringForURLs(urlGroups);
//        String result2=URLGroupUtils.getIdentifyingStringForURLs(urlGroups, newURLList);
//        System.out.println(result1);
//        System.out.println(result2);
//        System.out.println(StringAlgorithmUtils.editDistance(result1, result2));
    }
    
    
    
    static class URLClusterScore {
        protected double scores[] = new double[4];
        protected URL url = null;
    }
}
