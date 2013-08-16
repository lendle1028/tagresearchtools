/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elaborate.util.google.googlesearchtool;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.dictionary.MorphologicalProcessor;

/**
 *
 * @author lendle
 */
public class TagStemmer {

    private Dictionary dict = null;
    private MorphologicalProcessor morph = null;
    private String [] exceptionWords={
        "web2.0",
        "mp3"
    };
    private Map<String, String> exceptionWordsMap=new HashMap<String, String>();

    public TagStemmer() {
    }

    public void init() throws Exception {
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        dict = Dictionary.getInstance();
        morph=dict.getMorphologicalProcessor();
        for(String word : this.exceptionWords){
            exceptionWordsMap.put(word, "");
        }
    }

    public String getRootForm(String word) throws Exception{
        word=word.toLowerCase();
        if(exceptionWordsMap.containsKey(word)){
            return word;
        }
        IndexWord indexWord = morph.lookupBaseForm(POS.NOUN, word);
        if (indexWord != null) {
            indexWord = dict.getIndexWord(POS.NOUN, indexWord.getLemma());
            if (indexWord != null && indexWord.getLemma().trim().length() > 1) {
                return indexWord.getLemma().trim();
            }
        }
        return null;
    }

    public void close() throws Exception {
        dict.close();
    }
}
