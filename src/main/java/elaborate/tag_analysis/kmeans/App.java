package elaborate.tag_analysis.kmeans;

import elaborate.tag_analysis.kmeans.impl.DefaultKmeansCalculatorImpl;
import elaborate.tag_analysis.utils.KmeansNodesLoader;
import elaborate.tag_analysis.utils.reporting.ClusterReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * read tag data, perform k-means calculation
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        execute("facebook");
        execute("apple");
        execute("java");
    }
    
    private static void execute(String keyword) throws Exception{
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
        SimpleDateFormat formater=new SimpleDateFormat("yyyyMMddHHmmss");
        File reportFolder=new File("report", "kmeans");
        reportFolder.mkdirs();
        File kmeansReportFile=new File(reportFolder, keyword+"_"+formater.format(new Date())+".html");
        ClusterReporter.generateSingleHTMLReport(clusters, kmeansReportFile);
    }
}
