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
    public static List<List<URL>> loadURLGroups() throws Exception{
        List<List<URL>> ret = new ArrayList<>();
        List<URL> currentGroup=null;
        try (InputStream input = new FileInputStream("opendirectory_urls.txt")) {
            Scanner scanner = new Scanner(input);
            String line = scanner.nextLine();
            while (line != null) {
                if (line.trim().length() > 0) {
                    if (line.startsWith("\t")) {
                        try {
                            URL url = new URL(line);
                            currentGroup.add(url);
                        } catch (Exception e) {
                            System.out.println("skip: " + line);
                        }
                    } else {
                        currentGroup = new ArrayList<>();
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
}
