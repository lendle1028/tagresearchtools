/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.opendirectory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lendle
 */
public class URLGroupUtils {
    public static String getIdentifyingStringForURLs(List<URLGroup> groups, List<URL> urls){
        StringBuilder sb=new StringBuilder();
        for(URL url : urls){
            //System.out.println("\t"+url);
            try{
                sb.append(getGroupForURL(groups, url).getIdentifier());
            }catch(Exception e){
                //System.out.println(url+":"+getGroupForURL(groups, url));
            }
        }
        return sb.toString();
    }
    
    public static String getIdentifyingStringForURLs(List<URLGroup> groups){
        List<URL> urls=new ArrayList<URL>();
        for(URLGroup group : groups){
            for(URL url : group.toArray(new URL[0])){
                urls.add(url);
            }
        }
        return getIdentifyingStringForURLs(groups, urls);
    }
    
    public static URLGroup getGroupForURL(List<URLGroup> groups, URL url){
        for(URLGroup group : groups){
            if(group.contains(url)){
                return group;
            }
        }
        return null;
    }
}
