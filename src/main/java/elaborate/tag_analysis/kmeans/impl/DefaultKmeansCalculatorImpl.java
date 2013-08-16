/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.kmeans.impl;

import elaborate.tag_analysis.kmeans.Centroid;
import elaborate.tag_analysis.kmeans.Cluster;
import elaborate.tag_analysis.kmeans.KmeansCalculator;
import elaborate.tag_analysis.kmeans.Node;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author lendle
 */
public class DefaultKmeansCalculatorImpl implements KmeansCalculator{

    @Override
    public List<Cluster> calculate(List<Centroid> centroids, List<Node> tags) {
        Cluster [] clusters=new Cluster[centroids.size()];
        for(int i=0; i<clusters.length; i++){
            clusters[i]=new Cluster();
            clusters[i].setCentroid(centroids.get(i));
        }
        for (Node tag : tags) {
            double minDistance = Double.MAX_VALUE;
            int indexNearestCluster = -1;
            for (int index = 0; index < clusters.length; index++) {
                Cluster c = clusters[index];
                double result = c.getDistance(tag);

                if (result < minDistance) {
                    minDistance = result;
                    indexNearestCluster = index;
                }
            }
            //System.out.println("\t"+indexNearestCluster+":"+minDistance);
            if (indexNearestCluster == -1) {
                //System.out.println(tag.getValue() + ":" + minDistance);
                continue;
            }
            clusters[indexNearestCluster].getTags().add(tag);
        }
        //configure new centroid
        for (int index = 0; index < clusters.length; index++) {
            Cluster c = clusters[index];
            //System.out.println(index);
            double[] newSum = new double[c.getCentroid().getLocation().length];
            for (Node tag : c.getTags()) {
                //System.out.println("\t"+tag.getValue());
                for (int i = 0; i < newSum.length; i++) {
                    newSum[i] += tag.getFeature()[i];
                }
            }
            for (int i = 0; i < newSum.length; i++) {
                c.getCentroid().getLocation()[i] = newSum[i] / c.getTags().size();
            }
        }
        return Arrays.asList(clusters);
    }

    @Override
    public List<Cluster> calculate(int numberOfCentroids, List<Node> tags) {
        Centroid[] centroids = new Centroid[numberOfCentroids];
        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = new Centroid(tags.get(i).getFeature());
        }
        return this.calculate(Arrays.asList(centroids), tags);
    }

    @Override
    public List<Cluster> calculate(int numberOfNewCentroids, Cluster cluster) {
        return this.calculate(numberOfNewCentroids, cluster.getTags());
    }
    
}
