/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.opendirectory;

import com.google.gson.Gson;
import elaborate.tag_analysis.LinkData;
import elaborate.tag_analysis.Tag;
import elaborate.tag_analysis.tag_extractor.URLTagExtractor;
import elaborate.tag_analysis.tag_extractor.delicious.DeliciousTagExtractor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lendle
 */
public class OpenDirectoryTagExtractorApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        List<URLGroup> urlGroups=URLGroupsLoader.loadURLGroups();
//        
//        List<URL> urls = new ArrayList<URL>();
//        List<List<String>> tagGroups = new ArrayList<>();
//        List<String> currentTagGroup = null;
//
//        Map<URL, List<String>> url2TagGroupMap = new HashMap<>();
//        try (InputStream input = new FileInputStream("opendirectory_urls.txt")) {
//            Scanner scanner = new Scanner(input);
//            String line = scanner.nextLine();
//            while (line != null) {
//                if (line.trim().length() > 0) {
//                    if (line.startsWith("\t")) {
//                        try {
//                            URL url = new URL(line);
//                            urls.add(new URL(line));
//                            url2TagGroupMap.put(url, currentTagGroup);
//                        } catch (Exception e) {
//                            System.out.println("skip: " + line);
//                        }
//                    } else {
//                        currentTagGroup = new ArrayList<>();
//                        tagGroups.add(currentTagGroup);
//                    }
//                }
//                if (scanner.hasNextLine()) {
//                    line = scanner.nextLine();
//                } else {
//                    break;
//                }
//            }
//        }

        //System.out.println("num groups: " + tagGroups.size());
        List<LinkData> linkDataList = new ArrayList<LinkData>();
        //TagStemmer stemmer = new TagStemmer();
        //stemmer.init();
        
        Map<URL, List<String>> url2TagGroupMap = new HashMap<>();
        for (URLGroup group : urlGroups) {
            for (URL url : group.toArray(new URL[0])) {
                try {
                    LinkData linkData = new LinkData();
                    URLTagExtractor extractor = new DeliciousTagExtractor();
                    System.out.println(url);
                    linkData.setUrl(new URL(url.getProtocol(), url.getHost(), "/"));
                    List<String> tags = extractor.extractTags(url, "utf-8");
                    for (String tagString : tags) {
                        //tagString = stemmer.getRootForm(tagString);
                        System.out.println("\t" + tagString);
                        if (tagString == null) {
                            continue;
                        }
                        Tag tag = new Tag();
                        tag.setValue(tagString);
                        linkData.getTags().add(tag);
                    }
                    if (linkData.getTags().isEmpty() == false) {
                        linkDataList.add(linkData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(linkDataList);
        System.out.println(json);
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream("opendirectory_tags.txt"), "utf-8"));
        output.print(json);
        output.close();
//        System.out.println("=================================");
//        System.out.println(gson.toJson(tagGroups));
    }

}
