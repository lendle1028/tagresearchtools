/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.opendirectory;

import elaborate.tag_analysis.utils.StringAlgorithmUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author lendle
 */
public class ClusterPerformanceEvaluator {
    public double evaluate(List<URLGroup> expertMadeGroups, List<URLGroup> calculatedGroups){
//        for(int i=0; i<calculatedGroups.size(); i++){
//            calculatedGroups.get(i).setIdentifier(""+i);
//        }
        List<List<URLGroup>> allPermuatations=this.permutate(calculatedGroups);
        System.out.println("allPermuatations.size()="+allPermuatations.size());
        double maxScore=Double.MIN_VALUE;
        for(List<URLGroup> newURLGroups : allPermuatations){
            //System.out.println("perm: "+Arrays.deepToString(newURLGroups.toArray()));
            List<URL> newURLList=new ArrayList<>();
            for(URLGroup group : newURLGroups){
                for(URL url : group.toArray(new URL[0])){
                    newURLList.add(url);
                }
            }
            String result1=URLGroupUtils.getIdentifyingStringForURLs(expertMadeGroups);
            String result2=URLGroupUtils.getIdentifyingStringForURLs(expertMadeGroups, newURLList);
            double distance=StringAlgorithmUtils.editDistance(result1, result2);
            System.out.println(result1);
            System.out.println(result2);
            System.out.println("\t"+distance);
            double score=1/(distance+1);//to prevent divided by zero
            if(score>maxScore){
                maxScore=score;
            }
        }
        return maxScore;
    }
    /**
     * permutate to find all possible arranges of 
     * the groups
     * @param group
     * @return 
     */
    private List<List<URLGroup>> permutate(List<URLGroup> groups){
        List<List<URLGroup>> ret=new ArrayList<>();
        if(groups.size()==1){
            ret.add(groups);
            return ret;
        }else{
            for(int i=0; i<groups.size(); i++){
                List<URLGroup> copy=new ArrayList<>(groups);
                
                URLGroup group=copy.remove(i);
                
                List<List<URLGroup>> nextResult=this.permutate(copy);
                for(List<URLGroup> list : nextResult){
                    //System.out.println("next="+URLGroupUtils.getIdentifyingStringForURLs(list));
                    //System.out.println("next="+list.size());
                    List<URLGroup> subRet=new ArrayList<>();
                    subRet.add(group);
                    subRet.addAll(list);
                    ret.add(subRet);
                }
            }
            return ret;
        }
    }
}
