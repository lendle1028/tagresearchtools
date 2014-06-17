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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
            writer.println("<html><body>");
            writer.println("<a href='#clusters'>Clusters</a> <a href='#tags'>Tags</a> <a href='#urls'>URLs</a>");
            List<Node> allTags=new ArrayList<Node>();//collect all tags for future processing
            Map<URL, List<Node>> url2TagMap=new HashMap<URL, List<Node>>();//collect all urls for future processing
            //output clusters
            writer.println("<a name='clusters'/>");
            for (Cluster cluster : clusters) {    
                List<Node> tags = sortTagsByDistance(cluster);
                allTags.addAll(tags);
                double clusterAvg = cluster.getAverageDistance();
                double stdev = cluster.getStdev();
                writer.println("<table border='1'>");
                writer.println("<tr><th>Tag</th><th>Distance</th><th>Cluster Avg</th><th>Cluster Stdev</th></tr>");
                for (int i = 0; i < tags.size(); i++) {
                    Node tag = tags.get(i);
                    for(URL url: tag.getFeature().getUrls()){
                        List<Node> urlTags=url2TagMap.get(url);
                        if(urlTags==null){
                            urlTags=new ArrayList<Node>();
                            url2TagMap.put(url, urlTags);
                        }
                        urlTags.add(tag);
                    }
                    double distance = cluster.getDistance(tag);
                    Node nextTag = ((i + 1) < tags.size()) ? tags.get(i + 1) : null;
                    writer.println(String.format("<tr><td><a href='#%s'>%s</a></td><td>%s</td><td>%s</td><td>%s</td></tr>",
                            tag.getValue(), tag.getValue(), distance, clusterAvg, stdev));
                    if (nextTag != null) {
                        double nextTagDistance = cluster.getDistance(nextTag);
                        if (nextTagDistance >= clusterAvg + 3 * stdev && distance < clusterAvg + 3 * stdev) {
                            writer.println("<tr style='background-color: red; color: yellow'><td>3 stdev</td><td>"+(clusterAvg + 3 * stdev)+"</td><td></td><td></td></tr>");
                        } else if (nextTagDistance >= clusterAvg + 2 * stdev && distance < clusterAvg + 2 * stdev) {
                            writer.println("<tr style='background-color: red; color: yellow'><td>2 stdev</td><td>"+(clusterAvg + 2 * stdev)+"</td><td></td><td></td></tr>");
                        } else if (nextTagDistance >= clusterAvg + stdev && distance < clusterAvg + stdev) {
                            writer.println("<tr style='background-color: red; color: yellow'><td>1 stdev</td><td>"+(clusterAvg + 1 * stdev)+"</td><td></td><td></td></tr>");
                        } else if (nextTagDistance >= clusterAvg && distance < clusterAvg) {
                            writer.println("<tr style='background-color: blue; color: yellow'><td>AVG</td><td></td><td></td><td></td></tr>");
                        }
                    }
                }
                writer.println("</table>");
                writer.println("<hr/>");
            }
            //output tag to urls
            writer.println("<a name='tags'/>");
            writer.println("<table border='1'>");
            writer.println("<tr><th>Tag</th><th>URLs</th></tr>");
            for(Node tag: allTags){
                List<URL> urls=tag.getFeature().getUrls();
                StringBuffer urlsList=new StringBuffer();
                for(URL url : urls){
                    urlsList.append(String.format("<li><a href='%s'>%s</a>&nbsp;&nbsp;&nbsp;<a href='#%s'>tags</a></li>", url.toString(), url.toString(), url.toString()));
                }
                writer.println(String.format("<tr><td><a name='%s'/>%s</td><td><ul>%s</ul></td></tr>", tag.getValue(), tag.getValue(), urlsList.toString()));
            }
            writer.println("</table>");
            //output url to tags
            writer.println("<a name='urls'/>");
            writer.println("<table border='1'>");
            writer.println("<tr><th>URL</th><th>Tags</th></tr>");
            for(URL url: url2TagMap.keySet()){
                StringBuffer tagsList=new StringBuffer();
                for(Node tag: url2TagMap.get(url)){
                    tagsList.append(String.format("<li>%s</li>", tag.getValue()));
                }
                writer.println(String.format("<tr><td><a name='%s'/>%s</td><td><ul>%s</ul></td></tr>", url, url, tagsList.toString()));
            }
            writer.println("</body></html>");
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
