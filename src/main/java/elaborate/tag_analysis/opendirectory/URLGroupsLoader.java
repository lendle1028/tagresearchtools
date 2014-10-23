/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.opendirectory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lendle
 */
public class URLGroupsLoader {
    public static List<URLGroup> loadURLGroups() throws Exception{
        return loadURLGroups(new SimpleIdentifierGenerator());
    }
    public static List<URLGroup> loadURLGroups(IdentifierGenerator idGenerator) throws Exception{
        List<URLGroup> ret = new ArrayList<>();
        URLGroup currentGroup=null;
        try (InputStream input = new FileInputStream("opendirectory_urls.txt")) {
            Scanner scanner = new Scanner(input);
            String line = scanner.nextLine();
            while (line != null) {
                if (line.trim().length() > 0) {
                    if (line.startsWith("\t")) {
                        try {
                            URL url = new URL(line);
                            //currentGroup.add(url);
                            currentGroup.add(new URL(url.getProtocol(), url.getHost(), "/"));//preserve only the host part
                        } catch (Exception e) {
                            System.out.println("skip: " + line);
                        }
                    } else {
                        currentGroup = new URLGroup();
                        currentGroup.setIdentifier(idGenerator.getNextIdentifier());
                        ret.add(currentGroup);
                    }
                }
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                } else {
                    break;
                }
            }
        }
        return ret;
    }
    
    public static void main(String [] args) throws Exception{
        System.out.println(URLGroupsLoader.loadURLGroups().size());
    }
    
    public static interface IdentifierGenerator{
        public String getNextIdentifier() throws Exception;
    }
    
    public static class SimpleIdentifierGenerator implements IdentifierGenerator{
        private String [] identifiers=new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
        private int currentIndex=0;
        @Override
        public String getNextIdentifier()  throws Exception{
            if(currentIndex>=this.identifiers.length){
                throw new Exception("SimpleIdentifierGenerator supports no more than "+this.identifiers.length+" groups");
            }
            return this.identifiers[currentIndex++];
        }
        
    }
}
