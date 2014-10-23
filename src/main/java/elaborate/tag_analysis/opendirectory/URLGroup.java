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
public class URLGroup extends ArrayList<URL>{
    private String identifier="";

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean contains(Object o) {
        for(URL url : this.toArray(new URL[0])){
            if(url.equals(o) || url.toString().contains(o.toString())){
                return true;
            }
        }
        return false;
    }
    /**
     * preserve only the listed urls
     * @param urls 
     */
    public void preserveOnly(List<URL> urls){
        List<URL> tobeDeleted=new ArrayList<>();
        for(URL url : this.toArray(new URL[0])){
            if(urls.contains(url)==false){
                tobeDeleted.add(url);
            }
        }
        for(URL url : tobeDeleted){
            this.remove(url);
        }
    }
    
    
}
