package elaborate.tag_analysis.opendirectory;

import elaborate.tag_analysis.kmeans.Centroid;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.Node;
import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;
import elaborate.tag_analysis.utils.KmeansNodesLoader;
import elaborate.tag_analysis.utils.reporting.ClusterReporter;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * read tag data, perform k-means calculation
 *
 */
public class OpendirectoryKmeansApp {

    public static void main(String[] args) throws Exception {
        execute("opendirectory_tags");
    }

    private static void execute(String keyword) throws Exception {
        //CentroidSelector selector=new RandomCentroidSelectorImpl();
        //int numOfClusters = 10;
        //int numOfClusters = 4;
        //get k-means nodes
        List<Node> nodes = KmeansNodesLoader.loadNodes(keyword + ".txt");
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
        //Centroid[] centroids=selector.selectNewCentroids(nodes, numOfClusters);
        //perform k-means algorithm to get clusters
        List<Cluster> clusters = null;
        clusters = kmeansCalculator.calculate(Arrays.asList(centroids), nodes);
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
        File reportFolder = new File("report", "kmeans");
        reportFolder.mkdirs();
        File kmeansReportFile = new File(reportFolder, keyword + "_" + formater.format(new Date()) + ".html");
        //ClusterReporter.generateSingleHTMLReport(clusters, kmeansReportFile);

        //categorize urls according to the clustering result
        Map<URL, URLClusterScore> urlClusterScoreMap = new HashMap<>();
        for (int i = 0; i < clusters.size(); i++) {
            Cluster cluster = clusters.get(i);
            for (Node tag : cluster.getTags()) {
                for (URL url : tag.getFeature().getUrls()) {
                    URLClusterScore score = urlClusterScoreMap.get(url);
                    if (score == null) {
                        score = new URLClusterScore();
                        urlClusterScoreMap.put(url, score);
                    }

                    score.scores[i]++;
                }
            }
        }
        for (Map.Entry entry : urlClusterScoreMap.entrySet()) {
            URLClusterScore score = (URLClusterScore) entry.getValue();
            System.out.println(entry.getKey() + ":" + Arrays.toString(score.scores));
        }
    }

    static class URLClusterScore {

        protected double scores[] = new double[4];
        protected URL url = null;
    }
}
