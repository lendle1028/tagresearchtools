/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.utils.reporting;

import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.Node;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author lendle
 */
public class ClusterReporter {

    public static void generateCSVReport(Cluster cluster, Writer writer) throws Exception {
        PrintWriter output = new PrintWriter(writer);
        output.println("Tag,Distance,Cluster Avg, Cluster Stdev");
        List<Node> tags = sortTagsByDistance(cluster);
        for (Node node : tags) {
            output.println(String.format("%s,%s,%s,%s", node.getValue(), cluster.getDistance(node), cluster.getAverageDistance(), cluster.getStdev()));
        }
    }

    /**
     * generate 1.csv, 2.csv, 3.csv...... in the specified folder
     *
     * @param clusters
     * @param folder
     * @throws Exception
     */
    public static void generateCSVReport(List<Cluster> clusters, File folder) throws Exception {
        int index = 1;
        for (Cluster cluster : clusters) {
            File file = new File(folder, "" + index + ".csv");
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
                generateCSVReport(cluster, writer);
            }
            index++;
        }
    }

    /**
     * generate report in the specified html file the report will sort tags
     * according to their distances to centroids also, avg, 1 stdev, 2 stdev, 3
     * stdev will be marked
     *
     * @param clusters
     * @param folder
     * @throws Exception
     */
    public static void generateSingleHTMLReport(List<Cluster> clusters, File file) throws Exception {
        int index = 1;

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
            writer.println("<html><body>");
            for (Cluster cluster : clusters) {    
                List<Node> tags = sortTagsByDistance(cluster);
                double clusterAvg = cluster.getAverageDistance();
                double stdev = cluster.getStdev();
                writer.println("<table border='1'>");
                writer.println("<tr><th>Tag</th><th>Distance</th><th>Cluster Avg</th><th>Cluster Stdev</th></tr>");
                for (int i = 0; i < tags.size(); i++) {
                    Node tag = tags.get(i);
                    double distance = cluster.getDistance(tag);
                    Node nextTag = ((i + 1) < tags.size()) ? tags.get(i + 1) : null;
                    writer.println(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                            tag.getValue(), distance, clusterAvg, stdev));
                    if (nextTag != null) {
                        double nextTagDistance = cluster.getDistance(nextTag);
                        if (nextTagDistance >= clusterAvg + 3 * stdev && distance < clusterAvg + 3 * stdev) {
                            writer.println("<tr style='background-color: red; color: yellow'><td>3 stdev</td><td></td><td></td><td></td></tr>");
                        } else if (nextTagDistance >= clusterAvg + 2 * stdev && distance < clusterAvg + 2 * stdev) {
                            writer.println("<tr style='background-color: red; color: yellow'><td>2 stdev</td><td></td><td></td><td></td></tr>");
                        } else if (nextTagDistance >= clusterAvg + stdev && distance < clusterAvg + stdev) {
                            writer.println("<tr style='background-color: red; color: yellow'><td>1 stdev</td><td></td><td></td><td></td></tr>");
                        } else if (nextTagDistance >= clusterAvg && distance < clusterAvg) {
                            writer.println("<tr style='background-color: blue; color: yellow'><td>AVG</td><td></td><td></td><td></td></tr>");
                        }
                    }
                }
                writer.println("</table>");
                writer.println("<hr/>");
            }
             writer.println("</body></html>");
            index++;
        }
    }

    private static List<Node> sortTagsByDistance(final Cluster cluster) {
        List<Node> tags = new ArrayList<Node>(cluster.getTags());
        Collections.sort(tags, new Comparator<Node>() {

            @Override
            public int compare(Node o1, Node o2) {
                double distance1 = cluster.getDistance(o1);
                double distance2 = cluster.getDistance(o2);
                if (distance1 == distance2) {
                    return 0;
                } else if (distance1 > distance2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return tags;
    }
}
