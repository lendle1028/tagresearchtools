package elaborate.util.google.googlesearchtool;

import com.google.gson.Gson;
import elaborate.tag_analysis.Constant;
import elaborate.tag_analysis.LinkData;
import elaborate.tag_analysis.Tag;
import elaborate.tag_analysis.tag_extractor.URLTagExtractor;
import elaborate.tag_analysis.tag_extractor.delicious.DeliciousTagExtractor;
import elaborate.util.google.DefaultGoogleSearchClientFactory;
import elaborate.util.google.GoogleSearchClient;
import elaborate.util.google.GoogleSearchResult;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {
        Map<String, String> hostTable = new HashMap<String, String>();//filter existing hosts
        System.out.println("current profile: "+Constant.PROFILE);
        String keyword = Constant.PROFILE;
        GoogleSearchClient client = new DefaultGoogleSearchClientFactory().createGoogleSearchClient();
        TagStemmer stemmer = new TagStemmer();
        stemmer.init();
        try {
            //List<GoogleSearchResult> list=client.search("test", 1000, 100);
            List<GoogleSearchResult> list = client.search(keyword, 0, 800);
            URLTagExtractor extractor = new DeliciousTagExtractor();
            List<LinkData> linkDataList = new ArrayList<LinkData>();
            for (GoogleSearchResult result : list) {
                try {
                    LinkData linkData = new LinkData();
                    URL url = new URL(result.getAnchorHref());
                    String host = url.getHost();
                    if (hostTable.containsKey(host)) {
                        continue;
                    } else {
                        hostTable.put(host, "");
                    }
                    //linkData.setUrl(url);
                    linkData.setUrl(new URL(url.getProtocol(), url.getHost(), "/"));
                    List<String> tags = extractor.extractTags(new URL(result.getAnchorHref()), "utf-8");
                    System.out.println(result.getAnchorText() + ":" + result.getAnchorHref());
                    for (String tagString : tags) {
                        tagString=stemmer.getRootForm(tagString);
                        System.out.println("\t" + tagString);
                        if(tagString==null){
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
            Gson gson = new Gson();
            String json = gson.toJson(linkDataList);
            System.out.println(json);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(keyword + ".txt"), "utf-8"));
            output.print(json);
            output.close();
        } finally {
            stemmer.close();
        }
    }
}
