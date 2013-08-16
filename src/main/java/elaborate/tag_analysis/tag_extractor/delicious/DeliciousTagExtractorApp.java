/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.tag_analysis.tag_extractor.delicious;

import elaborate.tag_analysis.tag_extractor.URLTagExtractor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 * @author lendle
 */
public class DeliciousTagExtractorApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        URLTagExtractor extractor=new DeliciousTagExtractor();
        String [] targets={
            "http://www.math.com/",
            "http://en.wikipedia.org/wiki/Mathematics",
            "http://www.mathplayground.com/",
            "http://mathworld.wolfram.com/",
            "http://www.funbrain.com/math/",
            "http://www.mathsisfun.com/",
            "http://www.coolmath4kids.com/",
            "http://download.oracle.com/javase/1.5.0/docs/api/java/lang/Math.html",
            "http://www.aplusmath.com/",
            "http://www.aaamath.com/",
            "http://www.mathletics.com.au/",
            "http://www.sosmath.com/",
            "http://mathforum.org/",
            "http://www.ixl.com/",
            "http://www.coolmath.com/",
            "http://docs.python.org/library/math.html",
            "http://download.oracle.com/javase/6/docs/api/java/lang/Math.html",
            "http://www.math.ntnu.edu.tw/",
            "http://www.bbc.co.uk/schools/ks2bitesize/maths/",
            "http://www.primarygames.com/math.htm",
            "http://www.thatquiz.org/",
            "http://arxiv.org/archive/math",
            "http://www.openoffice.org/product/math.html",
            "http://episte.math.ntu.edu.tw/",
            "http://www.math.sinica.edu.tw/",
            "http://www.webmath.com/",
            "http://www.coolmath-games.com/",
            "http://www.amathsdictionaryforkids.com/",
            "http://resources.oswego.org/games/mathmagician/cathymath.html",
            "http://www.math.ncu.edu.tw/",
            "http://www.math.ncku.edu.tw/",
            "http://archives.math.utk.edu/",
            "http://www.w3.org/Math/",
            "http://www.math.nthu.edu.tw/",
            "http://www.math.nsysu.edu.tw/",
            "http://math2.org/",
            "http://mathseed.ntue.edu.tw/",
            "http://www.math.ntu.edu.tw/",
            "http://www.openoffice.org/zh/new/zh_tw/products/math.html",
            "http://www.woodlands-junior.kent.sch.uk/maths/",
            "http://www.math.nctu.edu.tw/",
            "http://www.mathgoodies.com/",
            "http://www.pearsonsuccessnet.com/",
            "http://mathcats.com/",
            "http://www.zentralblatt-math.org/zmath/en/",
            "http://www.math.ccu.edu.tw/",
            "http://www.taiwanmathsoc.org.tw/",
            "http://www.corestandards.org/the-standards/mathematics",
            "http://www.math.tku.edu.tw/"
        };
        
        File result=new File("result.txt");
        PrintWriter output=new PrintWriter(new OutputStreamWriter(new FileOutputStream(result), "utf-8" ));
        try{
            for(String target : targets){
                URL url = new URL("http://previous.delicious.com/url/" + URLEncoder.encode(new URL(target).getHost(), "utf-8"));
                List<String> tags=extractor.extractTags(url, "utf-8");
                if(tags==null || tags.isEmpty()){
                    continue;
                }
                output.println(target);
                for(String str : tags){
                    output.println("\t"+str);
                }
            }
        }finally{
            output.close();
        }
    }
}
