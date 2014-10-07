package elaborate.tag_analysis;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * read tag data, perform k-means calculation
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("current profile=" + Constant.PROFILE);
        String keyword = Constant.PROFILE;
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
        //show result
        for (Cluster cluster : goodClusters) {
            System.out.println("cluster stdev=" + cluster.getStdev() + ", average distance=" + cluster.getAverageDistance());
            List<TagStdevRatioPair> pairs = new ArrayList<TagStdevRatioPair>();
            //calculate distance between every node and its centroid
            for (Node node : cluster.getTags()) {
                double distance = DistanceCalculatorFactory.getDistanceCalculator().getDistance(node.getFeature().getVector(), cluster.getCentroid().getLocation());
                double stdevRatio = (distance - cluster.getAverageDistance()) / cluster.getStdev();
                pairs.add(new TagStdevRatioPair(node, stdevRatio));
            }
            Collections.sort(pairs);
            for (TagStdevRatioPair pair : pairs) {
                System.out.println(pair.getTag().getValue() + ":" + pair.getStdevRatio()+":"+cluster.getDistance((Node) pair.getTag()));
            }
            System.out.println("==============================================");
        }

        //report for distance between tags and all centroids
        System.out.println("==============================================");
        for (Node node : nodes) {
            System.out.print(node.getValue() + ",");
            boolean firstPrint = true;
            for (Cluster cluster : goodClusters) {
                Centroid centroid = cluster.getCentroid();
                double distance = DistanceCalculatorFactory.getDistanceCalculator().getDistance(node.getFeature().getVector(), centroid.getLocation());
                if (!firstPrint) {
                    System.out.print(",");
                }
                System.out.print(distance);
                firstPrint = false;
            }
            System.out.println("");
        }

    }

}
